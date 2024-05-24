package dev.hireben.mycrudapi.dto;

import jakarta.validation.constraints.NotBlank;

public class AgentDTO {

  @NotBlank
  private final String name;

  @NotBlank
  private final String alias;


  public AgentDTO(String name, String alias) {
    this.name = name;
    this.alias = alias;
  }

  public String getName() {
    return this.name;
  }

  public String getAlias() {
    return this.alias;
  }

}
