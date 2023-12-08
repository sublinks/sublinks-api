package com.sublinks.sublinksapi.slurfilter.repositories;

import com.sublinks.sublinksapi.slurfilter.dto.SlurFilter;
import com.sublinks.sublinksapi.slurfilter.enums.SlurActionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlurFilterRepository extends JpaRepository<SlurFilter, Long> {

  public List<SlurFilter> findBySlurActionType(SlurActionType actionType);
}
