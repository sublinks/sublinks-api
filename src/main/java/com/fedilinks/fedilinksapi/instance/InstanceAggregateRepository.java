package com.fedilinks.fedilinksapi.instance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceAggregateRepository extends JpaRepository<InstanceAggregate, Long> {
}
