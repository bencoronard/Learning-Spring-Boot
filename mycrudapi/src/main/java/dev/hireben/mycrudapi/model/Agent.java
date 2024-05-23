package dev.hireben.mycrudapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Agent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Byte id;
  private final String name;
  private final String alias;
  private String mission;

  public Agent(String name, String alias) {
    this.name = name;
    this.alias = alias;
    this.mission = "none";
  }

  public static Agent create(String name, String alias) {
    return new Agent(name, alias);
  }

  public Byte getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getMission() {
    return this.mission;
  }

  public void setMission(String mission) {
    this.mission = mission;
  }

}
