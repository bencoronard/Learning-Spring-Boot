package dev.hireben.mycrudapi.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.hireben.mycrudapi.model.Mission;

@Repository
public interface MissionRepository extends CrudRepository<Mission, Byte> {
  Optional<Mission> findByName(String name);
}
