package dev.hireben.mycrudapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.hireben.mycrudapi.model.Mission;

@Repository
public interface AgentRepository extends CrudRepository<Mission, Byte> {}
