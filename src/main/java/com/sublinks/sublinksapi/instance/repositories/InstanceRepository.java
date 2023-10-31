package com.sublinks.sublinksapi.instance.repositories;

import com.sublinks.sublinksapi.instance.dto.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceRepository extends JpaRepository<Instance, Long> {

}
