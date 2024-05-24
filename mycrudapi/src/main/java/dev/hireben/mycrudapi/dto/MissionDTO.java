package dev.hireben.mycrudapi.dto;

import jakarta.validation.constraints.NotBlank;

public class MissionDTO {

  @NotBlank
  private final String name;

  public MissionDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
