package com.sublinks.sublinksapi.instance.repositories;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.dto.InstanceConfig;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceConfigRepository extends JpaRepository<InstanceConfig, Long> {

  Optional<InstanceConfig> findByInstance(Instance instance);
}
