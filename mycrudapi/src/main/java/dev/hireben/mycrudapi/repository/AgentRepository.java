package dev.hireben.mycrudapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.hireben.mycrudapi.model.Agent;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Byte> {
  Optional<Agent> findByAlias(String alias);
}
