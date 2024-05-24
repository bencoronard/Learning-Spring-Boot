package dev.hireben.mycrudapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mission {

  public enum Status {
    UNASSIGNED,
    ASSIGNED,
    COMPLETED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Byte id;
  private final String name;
  @Enumerated(EnumType.STRING)
  private Status status;

  public Mission(String name) {
    this.name = name;
    this.status = Status.UNASSIGNED;
  }

  public static Mission create(String name) {
    return new Mission(name);
  }

  public Byte getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public String toString() {
      return "Mission{" +
              "id=" + this.id +
              ", name='" + this.name + '\'' +
              ", status=" + this.status.name() +
              '}';
  }

}
