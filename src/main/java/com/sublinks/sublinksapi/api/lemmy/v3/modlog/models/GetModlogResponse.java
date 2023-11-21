package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgeCommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgeCommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AdminPurgePostView;
import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetModlogResponse(
    List<ModRemovePostView> removed_posts,
    List<ModLockPostView> locked_posts,
    List<ModFeaturePostView> featured_posts,
    List<ModRemoveCommentView> removed_comments,
    List<ModRemoveCommunityView> removed_communities,
    List<ModBanFromCommunityView> banned_from_community,
    List<ModBanView> banned,
    List<ModAddCommunityView> added_to_community,
    List<ModTransferCommunityView> transferred_to_community,
    List<ModAddView> added,
    List<AdminPurgePersonView> admin_purged_persons,
    List<AdminPurgeCommunityView> admin_purged_communities,
    List<AdminPurgePostView> admin_purged_posts,
    List<AdminPurgeCommentView> admin_purged_comments,
    List<ModHideCommunityView> hidden_communities
) {

}