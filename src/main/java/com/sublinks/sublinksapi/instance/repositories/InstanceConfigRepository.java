package com.sublinks.sublinksapi.instance.repositories;

import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceConfigRepository extends JpaRepository<InstanceConfig, Long> {

  Optional<InstanceConfig> findByInstance(Instance instance);
}
