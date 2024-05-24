package dev.hireben.mycrudapi.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hireben.mycrudapi.dto.MissionDTO;
import dev.hireben.mycrudapi.model.Mission;
import dev.hireben.mycrudapi.model.Mission.Status;
import dev.hireben.mycrudapi.repository.MissionRepository;

@Service
public class MissionService {

  private final MissionRepository missionRepository;

  @Autowired
  public MissionService(MissionRepository missionRepository) {
    this.missionRepository  = missionRepository;
  }

  public Mission createMission(MissionDTO mission) throws IllegalArgumentException {
    // Check if a mission with the same name already exists
    Optional<Mission> existingMission = missionRepository.findByName(mission.getName());
    // Throw exception if the name is taken
    if (existingMission.isPresent()) {
      throw new IllegalArgumentException("A mission with the name " + mission.getName() + " already exists.");
    }
    // Store new agent in DB
    return missionRepository.save(Mission.create(mission.getName()));
  }

  public Mission getMissionByName(String name) {
    return missionRepository.findByName(name).orElse(null);
  }

  public void updateMission(String name, Status status) throws IllegalArgumentException {
    // Check if the mission with the name exists
    Optional<Mission> oldMission = missionRepository.findByName(name);
    // Throw exception if the mission does not exist
    if (!oldMission.isPresent()) {
      throw new IllegalArgumentException("A mission with the name " + name + " does not exist.");
    }
    // Update repository
    Mission newMission = oldMission.get();
    newMission.setStatus(status);
    missionRepository.save(newMission);
  }

  public void deleteMission(String name) {
    // Check if the mission with the name exists
    Mission mission = this.getMissionByName(name);
    // Perform deletion if the mission exists
    if (mission != null) {
      missionRepository.delete(mission);
    }
  }

  public Iterable<Mission> getMissions() {
    return missionRepository.findAll();
  }

}
