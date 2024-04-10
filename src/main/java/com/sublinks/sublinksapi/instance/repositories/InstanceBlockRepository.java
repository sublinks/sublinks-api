package com.sublinks.sublinksapi.instance.repositories;

import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.instance.entities.InstanceBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceBlockRepository extends JpaRepository<InstanceBlock, Long> {

  InstanceBlock findInstanceBlockByInstance(Instance instance);
}
