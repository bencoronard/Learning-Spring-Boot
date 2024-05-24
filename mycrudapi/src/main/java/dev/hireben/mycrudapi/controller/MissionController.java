package dev.hireben.mycrudapi.controller;

import java.util.List;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.mycrudapi.dto.MissionDTO;
import dev.hireben.mycrudapi.model.Mission;
import dev.hireben.mycrudapi.model.Mission.Status;
import dev.hireben.mycrudapi.service.MissionService;

@RestController
@RequestMapping("/api")
@Validated
public class MissionController {

  private final MissionService missionService;

  @Autowired
  public MissionController(
      MissionService missionService
    ) {
    this.missionService = missionService;
  }

  @GetMapping("/missions")
  public ResponseEntity<List<Mission>> getAllMissions() {
    try {

      List<Mission> missionList = new ArrayList<>();
      missionService.getMissions().forEach(missionList::add);

      if (missionList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(missionList, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/mission/{name}")
  public ResponseEntity<Mission> getAMission(@PathVariable String name) {
    try {

      if (name == null || name.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Mission mission = missionService.getMissionByName(name);

      if (mission == null) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(mission, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/mission")
  public ResponseEntity<String> createNewMission(@Validated @RequestBody MissionDTO mission) {
    try {

      Mission createdMission = missionService.createMission(mission);

      return new ResponseEntity<>(createdMission.toString(), HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/mission/{name}")
  public ResponseEntity<String> deleteAMission(@PathVariable String name) {
    try {

      if (name == null || name.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      missionService.deleteMission(name);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/mission/{name}/status/{status}")
  public ResponseEntity<String> updateMissionStatus(
      @PathVariable("name") String name,
      @PathVariable("status") String status
    ) {
    try {

      if (
          name == null || status == null ||
          name.isEmpty() || status.isEmpty()
        ) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      for (Status s : Status.values()) {
        if (s.name().equalsIgnoreCase(status)) {
          missionService.updateMission(name, s);
          break;
        }
      }

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
