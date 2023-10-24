package com.sublinks.sublinksapi.instance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceBlockRepository extends JpaRepository<InstanceBlock, Long> {
    InstanceBlock findInstanceBlockByInstance(Instance instance);
}
