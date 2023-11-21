package com.sublinks.sublinksapi.instance.repositories;

import com.sublinks.sublinksapi.instance.dto.InstanceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceAggregateRepository extends JpaRepository<InstanceAggregate, Long> {

}
