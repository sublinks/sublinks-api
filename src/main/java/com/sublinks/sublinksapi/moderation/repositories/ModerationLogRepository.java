package com.sublinks.sublinksapi.moderation.repositories;

import com.sublinks.sublinksapi.moderation.dto.ModerationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {

}
