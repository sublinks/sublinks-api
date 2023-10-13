package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.AdminPurgeCommentView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.AdminPurgeCommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.AdminPurgePersonView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.AdminPurgePostView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModAddCommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModAddView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModBanFromCommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModBanView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModFeaturePostView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModHideCommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModLockPostView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModRemoveCommentView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModRemoveCommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModRemovePostView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.ModTransferCommunityView;
import lombok.Builder;

import java.util.List;

@Builder
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