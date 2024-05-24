package dev.hireben.mycrudapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import dev.hireben.mycrudapi.dto.AgentDTO;
import dev.hireben.mycrudapi.dto.MissionDTO;
import dev.hireben.mycrudapi.model.Agent;
import dev.hireben.mycrudapi.model.Mission;
import dev.hireben.mycrudapi.repository.AgentRepository;
import dev.hireben.mycrudapi.repository.MissionRepository;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {

		ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);

		boolean init = false;

		if (init) {
			// Initialize H2 database
			AgentRepository agentRepository = appContext.getBean(AgentRepository.class);
			MissionRepository missionRepository = appContext.getBean(MissionRepository.class);

			List<AgentDTO> agents = initAgentRepository();
			List<MissionDTO> missions = initMissionRepository();

			for (AgentDTO agent : agents) {
				agentRepository.save(Agent.create(agent.getName(), agent.getAlias()));
			}
	
			for (MissionDTO mission: missions) {
				missionRepository.save(Mission.create(mission.getName()));
			}
		}

	}

	private static List<AgentDTO> initAgentRepository() {
		List<AgentDTO> agents = new ArrayList<>();
		// Populate agent list
		agents.add(new AgentDTO("James Bond", "007"));
		agents.add(new AgentDTO("Ethan Hunt", "IMF"));
		agents.add(new AgentDTO("Jason Bourne", "911"));
		agents.add(new AgentDTO("Pete Mitchell", "MVK"));
		agents.add(new AgentDTO("BourneAgain Shell", "GNU"));
		return agents;
	}

	private static List<MissionDTO> initMissionRepository() {
		List<MissionDTO> missions = new ArrayList<>();
		// Populate agent list
		missions.add(new MissionDTO("Skyfall"));
		missions.add(new MissionDTO("Spectre"));
		missions.add(new MissionDTO("Maverick"));
		missions.add(new MissionDTO("Fallout"));
		missions.add(new MissionDTO("Syndicate"));
		missions.add(new MissionDTO("Goldfinger"));
		missions.add(new MissionDTO("Moonraker"));
		missions.add(new MissionDTO("Thunderball"));
		missions.add(new MissionDTO("Legacy"));
		return missions;
	}

}
