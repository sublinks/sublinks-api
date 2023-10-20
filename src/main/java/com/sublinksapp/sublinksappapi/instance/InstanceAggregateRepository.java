package com.sublinksapp.sublinksappapi.instance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceAggregateRepository extends JpaRepository<InstanceAggregate, Long> {
}
