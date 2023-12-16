package com.sublinks.sublinksapi.api.lemmy.v3.modlog.services;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgeCommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgeCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePostView;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModAddCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModAddView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModBanFromCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModBanView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModFeaturePostView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModHideCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModLockPostView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModRemoveCommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModRemoveCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModRemovePostView;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModTransferCommunityView;
import com.sublinks.sublinksapi.moderation.dto.ModerationLog;
import com.sublinks.sublinksapi.moderation.events.ModerationLogCreatedPublisher;
import com.sublinks.sublinksapi.moderation.repositories.ModerationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Moderation Log Service
 */
@Component
@RequiredArgsConstructor
public class ModerationLogService {
  private final ModerationLogRepository moderationLogRepository;
  private final ModerationLogCreatedPublisher moderationLogCreatedPublisher;

  /**
   * Search moderation logs and return a page of results
   *
   * @param actionType Moderation Log Action Type
   * @param communityId Community Id
   * @param moderationPersonId Moderation Person Id
   * @param otherPersonId Other Person Id
   * @param page the page number
   * @param pageSize the size limit of a page
   * @param sort the sort option
   * @return a Page of moderation logs
   */
  public Page<ModerationLog> searchModerationLogs(ModlogActionType actionType,
      final Long communityId,
      final Long moderationPersonId,
      final Long otherPersonId,
      final int page,
      final int pageSize,
      final Sort sort) {
    return moderationLogRepository.searchAllByActionTypeAndPersonIds(actionType,
        communityId, moderationPersonId, otherPersonId, PageRequest.of(page, pageSize, sort));
  }

  /**
   * Saves a new Moderation Log
   *
   * @param moderationLog a new Moderation Log
   */
  @Transactional
  public void createModerationLog(final ModerationLog moderationLog) {
    moderationLogRepository.save(moderationLog);

    moderationLogCreatedPublisher.publish(moderationLog);
  }

  /**
   * Builds a ModRemovePostView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModRemovePostView buildModRemovePostView(ModerationLog moderationLog) {
    return ModRemovePostView.builder().build();
  }

  /**
   * Builds a ModLockPostView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModLockPostView buildModLockPostView(ModerationLog moderationLog) {
    return ModLockPostView.builder().build();
  }

  /**
   * Builds a ModFeaturePostView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModFeaturePostView buildModFeaturePostView(ModerationLog moderationLog) {
    return ModFeaturePostView.builder().build();
  }

  /**
   * Builds a ModRemoveCommentView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModRemoveCommentView buildModRemoveCommentView(ModerationLog moderationLog) {
    return ModRemoveCommentView.builder().build();
  }

  /**
   * Builds a ModRemoveCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModRemoveCommunityView buildModRemoveCommunityView(ModerationLog moderationLog) {
    return ModRemoveCommunityView.builder().build();
  }

  /**
   * Builds a ModBanFromCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModBanFromCommunityView buildModBanFromCommunityView(ModerationLog moderationLog) {
    return ModBanFromCommunityView.builder().build();
  }

  /**
   * Builds a ModBanView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModBanView buildModBanView(ModerationLog moderationLog) {
    return ModBanView.builder().build();
  }

  /**
   * Builds a ModAddCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModAddCommunityView buildModAddCommunityView(ModerationLog moderationLog) {
    return ModAddCommunityView.builder().build();
  }

  /**
   * Builds a ModTransferCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModTransferCommunityView buildModTransferCommunityView(ModerationLog moderationLog) {
    return ModTransferCommunityView.builder().build();
  }

  /**
   * Builds a ModAddView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModAddView buildModAddView(ModerationLog moderationLog) {
    return ModAddView.builder().build();
  }

  /**
   * Builds a AdminPurgePersonView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public AdminPurgePersonView buildAdminPurgePersonView(ModerationLog moderationLog) {
    return AdminPurgePersonView.builder().build();
  }

  /**
   * Builds a AdminPurgeCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public AdminPurgeCommunityView buildAdminPurgeCommunityView(ModerationLog moderationLog) {
    return AdminPurgeCommunityView.builder().build();
  }

  /**
   * Builds a AdminPurgePostView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public AdminPurgePostView buildAdminPurgePostView(ModerationLog moderationLog) {
    return AdminPurgePostView.builder().build();
  }

  /**
   * Builds a AdminPurgeCommentView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public AdminPurgeCommentView buildAdminPurgeCommentView(ModerationLog moderationLog) {
    return AdminPurgeCommentView.builder().build();
  }

  /**
   * Builds a ModHideCommunityView using information from the database for the moderation log
   *
   * @param moderationLog a moderation log
   * @return a moderation log view
   */
  public ModHideCommunityView buildModHideCommunityView(ModerationLog moderationLog) {
    return ModHideCommunityView.builder().build();
  }
}