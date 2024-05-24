package dev.hireben.mycrudapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.mycrudapi.service.AgentMissionService;


@RestController
@RequestMapping("/api")
@Validated
public class AgentMissionController {

  private final AgentMissionService agentMissionService;

  @Autowired
  public AgentMissionController(
      AgentMissionService agentMissionService
    ) {
    this.agentMissionService = agentMissionService;
  }

  @PutMapping("/agent/{alias}/mission/{name}")
  public ResponseEntity<String> assignMissionToAgent(
      @PathVariable("alias") String alias,
      @PathVariable("name") String name
    ) {
    try {

      if (
          name == null || alias == null ||
          name.isEmpty() || alias.isEmpty()
        ) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      agentMissionService.assignMission(alias, name);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
