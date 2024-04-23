package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;
import java.util.List;

@Builder
@SuppressWarnings("RecordComponentName")
public record ImportSettings(
  String avatar,
  String banner,
  String bio,
  List<String> blocked_communities,
  List<String> blocked_users,
  List<String> blocked_instances,
  List<String> followed_communities,
  Boolean bot_account,
  String display_name,
  String matrix_id,
  List<String> saved_comments,
  List<String> saved_posts,
  UserExportSettings settings
) {


}