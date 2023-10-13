package com.fedilinks.fedilinksapi.person.metadata;

import com.fedilinks.fedilinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Instance, Long> {

}
