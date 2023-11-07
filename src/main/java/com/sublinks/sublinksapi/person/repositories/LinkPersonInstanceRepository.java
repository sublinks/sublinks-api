package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.person.dto.LinkPersonInstance;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LinkPersonInstanceRepository extends JpaRepository<LinkPersonInstance, Long> {
    Collection<LinkPersonInstance> getLinkPersonInstancesByInstanceAndLinkTypeIsIn(Instance instance, Collection<LinkPersonInstanceType> linkTypes);
}
