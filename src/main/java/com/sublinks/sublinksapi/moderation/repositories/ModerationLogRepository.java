package com.sublinks.sublinksapi.moderation.repositories;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.moderation.dto.ModerationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {

  /**
   * Search moderation logs and return a page of results
   *
   * @param actionType Moderation Log Action Type
   * @param communityId Community Id
   * @param moderationPersonId Moderation Person Id
   * @param otherPersonId Other Person Id
   * @param pageable Pagination information
   * @return a Page of moderation logs
   */
  @Query(value = "SELECT modLog "
      + "FROM ModerationLog modLog "
      + "WHERE ((:actionType IS NULL OR :actionType = 'All') OR modLog.actionType = :actionType) "
      + " AND (:communityId IS NULL OR modLog.communityId = :communityId) "
      + " AND (:moderationPersonId IS NULL OR (modLog.moderationPersonId = :moderationPersonId OR modLog.adminPersonId = :moderationPersonId)) "
      + " AND (:otherPersonId IS NULL OR modLog.otherPersonId = :otherPersonId)")
  Page<ModerationLog> searchAllByActionTypeAndPersonIds(@Param("actionType") ModlogActionType actionType,
      @Param("communityId") Long communityId,
      @Param("moderationPersonId") Long moderationPersonId,
      @Param("otherPersonId") Long otherPersonId,
      Pageable pageable);
}
