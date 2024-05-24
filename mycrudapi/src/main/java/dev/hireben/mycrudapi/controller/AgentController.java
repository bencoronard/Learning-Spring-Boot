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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.mycrudapi.dto.AgentDTO;
import dev.hireben.mycrudapi.model.Agent;
import dev.hireben.mycrudapi.service.AgentService;


@RestController
@RequestMapping("/api")
@Validated
public class AgentController {

  private final AgentService agentService;

  @Autowired
  public AgentController(
      AgentService agentService
    ) {
    this.agentService = agentService;
  }

  @GetMapping("/agents")
  public ResponseEntity<List<Agent>> getAllAgents() {
    try {

      List<Agent> agentList = new ArrayList<>();
      agentService.getAgents().forEach(agentList::add);

      if (agentList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(agentList, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/agent/{alias}")
  public ResponseEntity<Agent> getAnAgent(@PathVariable String alias) {
    try {

      if (alias == null || alias.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Agent agent = agentService.getAgentByAlias(alias);

      if (agent == null) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(agent, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/agent")
  public ResponseEntity<String> createAnAgent(@Validated @RequestBody AgentDTO agent) {
    try {

      Agent createdAgent = agentService.createAgent(agent);
      
      return new ResponseEntity<>(createdAgent.toString(), HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/agent/{alias}")
  public ResponseEntity<String> deleteAnAgent(@PathVariable String alias) {
    try {

      if (alias == null || alias.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      agentService.deleteAgent(alias);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
