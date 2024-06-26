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
  private Byte missionId;

  public Agent() {
    this.name = "";
    this.alias = "";
    this.missionId = null;
  }

  public Agent(String name, String alias) {
    this.name = name;
    this.alias = alias;
    this.missionId = null;
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

  public Byte getMissionId() {
    return this.missionId;
  }

  public void setMission(Byte missionId) {
    this.missionId = missionId;
  }

  @Override
  public String toString() {
      return "Agent{" +
              "id=" + this.id +
              ", name='" + this.name + '\'' +
              ", alias='" + this.alias + '\'' +
              ", mission=" + this.missionId +
              '}';
  }

}
