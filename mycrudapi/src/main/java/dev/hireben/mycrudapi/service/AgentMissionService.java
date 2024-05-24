package dev.hireben.mycrudapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.hireben.mycrudapi.model.Agent;
import dev.hireben.mycrudapi.model.Mission;
import dev.hireben.mycrudapi.model.Mission.Status;

@Service
public class AgentMissionService {

  private final AgentService agentService;
  private final MissionService missionService;

  @Autowired
  public AgentMissionService(AgentService agentService, MissionService missionService) {
    this.agentService = agentService;
    this.missionService = missionService;
  }

  public void assignMission(String alias, String missionName) throws IllegalArgumentException {
    Agent agent = agentService.getAgentByAlias(alias);
    Mission mission = missionService.getMissionByName(missionName);
    // Check if the agent or the mission does not exist
    if (agent == null || mission == null) {
      throw new IllegalArgumentException("Unable to perform mission assignment");
    }
    // Update repositories
    agentService.updateAgent(alias, mission.getId());
    missionService.updateMission(missionName, Status.ASSIGNED);
  }

}
