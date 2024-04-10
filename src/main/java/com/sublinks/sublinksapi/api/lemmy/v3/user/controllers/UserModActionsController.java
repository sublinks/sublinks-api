package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.announcement.repositories.AnnouncementRepository;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetReportCount;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.services.ModerationLogService;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Tagline;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.LemmySiteService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BlockPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetReportCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonService;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.comment.repositories.CommentReportRepository;
import com.sublinks.sublinksapi.comment.services.CommentReportService;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.services.LanguageService;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.repositories.PostReportRepository;
import com.sublinks.sublinksapi.post.services.PostReportService;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageReportRepository;
import com.sublinks.sublinksapi.privatemessages.services.PrivateMessageReportService;
import com.sublinks.sublinksapi.privatemessages.services.PrivateMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
@Tag(name = "User")
public class UserModActionsController extends AbstractLemmyApiController {

  private final AnnouncementRepository announcementRepository;
  private final CommentReportRepository commentReportRepository;
  private final PostReportRepository postReportRepository;
  private final CommunityRepository communityRepository;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final RoleAuthorizingService roleAuthorizingService;
  private final PersonRepository personRepository;
  private final PostService postService;
  private final CommentService commentService;
  private final LemmyPersonService lemmyPersonService;
  private final ModerationLogService moderationLogService;
  private final LocalInstanceContext localInstanceContext;
  private final ConversionService conversionService;
  private final LemmySiteService lemmySiteService;
  private final LanguageService languageService;
  private final PostReportService postReportService;
  private final CommentReportService commentReportService;
  private final PrivateMessageReportRepository privateMessageReportRepository;
  private final PrivateMessageService privateMessageService;
  private final PrivateMessageReportService privateMessageReportService;

  @Operation(summary = "Ban a person from your site.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BanPersonResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class))),
      @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseStatusException.class)))})
  @PostMapping("ban")
  BanPersonResponse ban(@Valid @RequestBody final BanPerson banPersonForm, JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.INSTANCE_BAN_USER,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    final Person personToBan = personRepository.findById((long) banPersonForm.person_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    if (banPersonForm.ban()) {
      personToBan.setRole(roleAuthorizingService.getBannedRole());
      if (banPersonForm.remove_data()) {
        // Resolve all reports by content of the user
        postReportService.resolveAllReportsByPerson(personToBan, person);
        commentReportService.resolveAllReportsByCommentCreator(personToBan, person);
        privateMessageReportService.resolveAllReportsByPerson(personToBan, person);

        // Remove all posts and comments by the user
        postService.removeAllPostsFromUser(personToBan, true);
        commentService.removeAllCommentsFromUser(personToBan, true);
      }
    } else {
      // Restore all posts and comments by the user previously removed
      postService.removeAllPostsFromUser(personToBan, false);
      commentService.removeAllCommentsFromUser(personToBan, false);
      personToBan.setRole(roleAuthorizingService.getUserRole());
    }

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModBan)
        .banned(banPersonForm.ban())
        .entityId(personToBan.getId())
        .instance(personToBan.getInstance())
        .moderationPersonId(person.getId())
        .otherPersonId(personToBan.getId())
        .reason(banPersonForm.reason())
        .expires(banPersonForm.expires() == null ? null : new Date(banPersonForm.expires() * 1000L))
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return BanPersonResponse.builder()
        .banned(banPersonForm.ban())
        .person_view(lemmyPersonService.getPersonView(personToBan))
        .build();
  }

  @Operation(summary = "Block a person.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BlockPersonResponse.class))})})
  @PostMapping("block")
  BlockPersonResponse block(final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.USER_BLOCK,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    return BlockPersonResponse.builder().build();
  }

  @Operation(summary = "Get counts for your reports.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetReportCountResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Community not found", content = @Content)})
  @GetMapping("report_count")
  GetReportCountResponse reportCount(@Valid final GetReportCount getReportCount,
      JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrAnyPermissionOrThrow(person,
        Set.of(RolePermission.REPORT_INSTANCE_READ, RolePermission.REPORT_COMMUNITY_READ),
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    final GetReportCountResponse.GetReportCountResponseBuilder builder = GetReportCountResponse.builder();

    if (getReportCount.community_id() != null) {
      final Community community = communityRepository.findById((long) getReportCount.community_id())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "coomunity_not_found"));

      final boolean isModOfCommunity =
          linkPersonCommunityService.hasLink(person, community, LinkPersonCommunityType.moderator)
              || linkPersonCommunityService.hasLink(person, community,
              LinkPersonCommunityType.owner);

      if (!isModOfCommunity) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }

      builder.comment_reports(
          (int) commentReportRepository.countAllCommentReportsByResolvedFalseAndCommunity(
              List.of(community)));
      builder.post_reports(
          (int) postReportRepository.countAllPostReportsByResolvedFalseAndCommunity(
              List.of(community)));
      builder.private_message_reports(0);
    } else {

      final boolean isAdmin = RoleAuthorizingService.isAdmin(person);

      if (isAdmin) {
        builder.comment_reports(
            (int) commentReportRepository.countAllCommentReportsByResolvedFalse());
        builder.post_reports(
            (int) postReportRepository.countAllPostReportsReportsByResolvedFalse());
        builder.private_message_reports(
            (int) privateMessageReportRepository.countAllPrivateMessageReportsByResolvedFalse());
      } else {
        List<Community> communities = new ArrayList<>();

        communities.addAll(linkPersonCommunityService.getPersonLinkByType(person,
            LinkPersonCommunityType.moderator));
        communities.addAll(
            linkPersonCommunityService.getPersonLinkByType(person, LinkPersonCommunityType.owner));

        builder.comment_reports(
            (int) commentReportRepository.countAllCommentReportsByResolvedFalseAndCommunity(
                communities));
        builder.post_reports(
            (int) postReportRepository.countAllPostReportsByResolvedFalseAndCommunity(communities));

        builder.private_message_reports(0);
      }
    }

    return builder.build();
  }

  @Operation(summary = "Leave the Site admins.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetSiteResponse.class))})})
  @PostMapping("leave_admin")
  GetSiteResponse leaveAdmin(JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermissionOrThrow(person, RolePermission.INSTANCE_REMOVE_ADMIN,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    if (roleAuthorizingService.getAdmins().size() == 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot_leave_last_admin");
    }

    person.setRole(roleAuthorizingService.getUserRole());

    // Create Moderation Log
    ModerationLog moderationLog = ModerationLog.builder()
        .actionType(ModlogActionType.ModAdd)
        .entityId(person.getId())
        .removed(true)
        .instance(localInstanceContext.instance())
        .moderationPersonId(person.getId())
        .otherPersonId(person.getId())
        .build();
    moderationLogService.createModerationLog(moderationLog);

    return GetSiteResponse.builder()
        .version("0.19.0") // @todo grab this from config?
        .taglines(announcementRepository.findAll()
            .stream()
            .map(tagline -> conversionService.convert(tagline, Tagline.class))
            .toList())
        .site_view(lemmySiteService.getSiteView())
        .discussion_languages(languageService.instanceLanguageIds(localInstanceContext.instance()))
        .all_languages(lemmySiteService.allLanguages(localInstanceContext.languageRepository()))
        .custom_emojis(lemmySiteService.customEmojis())
        .admins(roleAuthorizingService.getAdmins()
            .stream()
            .map(lemmyPersonService::getPersonView)
            .toList())
        .build();
  }
}
