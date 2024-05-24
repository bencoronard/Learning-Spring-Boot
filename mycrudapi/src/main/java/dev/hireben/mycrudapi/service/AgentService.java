package dev.hireben.mycrudapi.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hireben.mycrudapi.dto.AgentDTO;
import dev.hireben.mycrudapi.model.Agent;
import dev.hireben.mycrudapi.repository.AgentRepository;

@Service
public class AgentService {

  private final AgentRepository agentRepository;

  @Autowired
  public AgentService(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }

  public Agent createAgent(AgentDTO agent) throws IllegalArgumentException {
    // Check if an agent with the same alias already exists
    Optional<Agent> existingAgent = agentRepository.findByAlias(agent.getAlias());
    // Throw exception if the alias is taken
    if (existingAgent.isPresent()) {
      throw new IllegalArgumentException("An agent with alias " + agent.getAlias() + " already exists.");
    }
    // Store new agent in DB
    return agentRepository.save(Agent.create(agent.getName(), agent.getAlias()));
  }

  public Agent getAgentByAlias(String alias) {
    return agentRepository.findByAlias(alias).orElse(null);
  }

  public void updateAgent(String alias, Byte missionId) throws IllegalArgumentException {
    // Check if the agent with the alias exists
    Optional<Agent> oldAgent = agentRepository.findByAlias(alias);
    // Throw exception if the agent does not exist
    if (!oldAgent.isPresent()) {
      throw new IllegalArgumentException("Update failed: An agent with " + alias + " does not exist.");
    }
    // Update repository
    Agent newAgent = oldAgent.get();
    newAgent.setMission(missionId);
    agentRepository.save(newAgent);
  }

  public void deleteAgent(String alias) {
    // Check if the agent with the alias exists
    Agent agent = this.getAgentByAlias(alias);
    // Perform deletion if the agent exists
    if (agent != null) {
      agentRepository.delete(agent);
    }
  }

  public Iterable<Agent> getAgents() {
    return agentRepository.findAll();
  }

}
