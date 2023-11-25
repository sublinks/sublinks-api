package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetReportCount;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BlockPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetReportCountResponse;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.post.repositories.PostReportRepository;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageReportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
@Tag(name = "User")
public class UserModActionsController {

  private final CommentReportRepository commentReportRepository;
  private final PostReportRepository postReportRepository;
  private final PrivateMessageReportRepository privateMessageReportRepository;
  private final CommunityRepository communityRepository;


  @Operation(summary = "Ban a person from your site.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BanPersonResponse.class))})})
  @PostMapping("ban")
  BanPersonResponse ban() {

    return BanPersonResponse.builder().build();
  }

  @Operation(summary = "Block a person.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BlockPersonResponse.class))})})
  @PostMapping("block")
  BlockPersonResponse block() {

    return BlockPersonResponse.builder().build();
  }

  @Operation(summary = "Get counts for your reports.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetReportCountResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Community not found", content = @Content)})
  @GetMapping("report_count")
  GetReportCountResponse reportCount(GetReportCount getReportCount) {

    final GetReportCountResponse.GetReportCountResponseBuilder builder = GetReportCountResponse.builder();

    if (getReportCount.community_id() != null) {
      final Community community = communityRepository.findById((long) getReportCount.community_id())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "coomunity_not_found"));

      builder.comment_reports(
          (int) commentReportRepository.countAllCommentReportsByResolvedFalseAndCommunity(
              community));
      builder.post_reports(
          (int) postReportRepository.countAllPostReportsByResolvedFalseAndCommunity(community));
      builder.private_message_reports(
          (int) privateMessageReportRepository.countAllPrivateMessageReportsByResolvedFalseAndCommunity(
              community));
    } else {
      builder.comment_reports(
          (int) commentReportRepository.countAllCommentReportsByResolvedFalse());
      builder.post_reports((int) postReportRepository.countAllPostReportsReportsByResolvedFalse());
      builder.private_message_reports(
          (int) privateMessageReportRepository.countAllPrivateMessageReportsByResolvedFalse());
    }

    return builder.build();
  }

  @Operation(summary = "Leave the Site admins.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetSiteResponse.class))})})
  @PostMapping("leave_admin")
  GetSiteResponse leaveAdmin() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
