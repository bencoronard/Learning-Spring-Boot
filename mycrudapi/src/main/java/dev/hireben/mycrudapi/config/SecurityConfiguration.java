package dev.hireben.mycrudapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
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
          .requestMatchers("/h2-console/**").hasRole(Role.SUPERUSER.name())
          .requestMatchers("/api/admin/**").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name())
          .requestMatchers("/api/user/**").hasAnyRole(Role.SUPERUSER.name(), Role.ADMIN.name(), Role.USER.name())
          .anyRequest().authenticated();
      })
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults());

      // H2 console access
      http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
      http.headers(headers -> headers.frameOptions(frames -> frames.disable()));

    return http.build();
  }
  
}
