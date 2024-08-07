package dev.hireben.mycrudapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private enum Role {
    USER,
    ADMIN,
    SUPERUSER
  }

  @Bean
  public UserDetailsService userDetailService() {

    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    manager.createUser(User.withUsername("user")
                           .password(passwordEncoder().encode("1234"))
                           .roles(Role.USER.name())
                           .build());

    manager.createUser(User.withUsername("admin")
                           .password(passwordEncoder().encode("1234"))
                           .roles(Role.ADMIN.name())
                           .build());
                           
    manager.createUser(User.withUsername("superuser")
                           .password(passwordEncoder().encode("1234"))
                           .roles(Role.SUPERUSER.name())
                           .build());
    
    return manager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
      .authorizeHttpRequests((authorize) -> {

        authorize
          .requestMatchers("/").authenticated()
          .requestMatchers("/h2-console/**").hasRole(Role.SUPERUSER.name());

        authorize
          .requestMatchers(HttpMethod.GET,"/api/agents").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          .requestMatchers(HttpMethod.GET,"/api/missions").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          
          .requestMatchers(HttpMethod.GET,"/api/agent/*").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          .requestMatchers(HttpMethod.GET,"/api/mission/*").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name(), Role.USER.name())

          .requestMatchers(HttpMethod.POST,"/api/agent").hasAnyRole(Role.SUPERUSER.name())
          .requestMatchers(HttpMethod.POST,"/api/mission").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())

          .requestMatchers(HttpMethod.PUT,"/api/agent/*/mission/*").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          .requestMatchers(HttpMethod.PUT,"/api/mission/*/status/*").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name(), Role.USER.name())

          .requestMatchers(HttpMethod.DELETE,"/api/agent/*").hasAnyRole(Role.SUPERUSER.name())
          .requestMatchers(HttpMethod.DELETE,"/api/mission/*").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          ;
          
      })
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults());

      // For H2 console access
      http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
      http.headers(headers -> headers.frameOptions(frames -> frames.disable()));

    return http.build();
  }

  @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("RESSION");
        cookieSerializer.setCookiePath("/");
        cookieSerializer.setSameSite("None");
        cookieSerializer.setUseSecureCookie(true);
        cookieSerializer.setUseHttpOnlyCookie(true);
        return cookieSerializer;
    }

}
