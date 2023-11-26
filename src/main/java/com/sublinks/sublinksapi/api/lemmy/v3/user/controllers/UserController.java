package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReplyView;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetReplies;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.services.LemmyCommentReplyService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BannedPersonsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetails;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonMentions;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonMentionsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetRepliesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetUnreadCount;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetUnreadCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.MarkPersonMentionAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.SaveUserSettings;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonMentionService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonService;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.repositories.CommentReplyRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.person.models.PersonMentionSearchCriteria;
import com.sublinks.sublinksapi.person.repositories.PersonMentionRepository;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
@Tag(name = "User")
public class UserController {

  private final LemmyPersonService lemmyPersonService;
  private final PersonRepository personRepository;
  private final PersonMentionRepository personMentionRepository;
  private final ConversionService conversionService;
  private final PersonService personService;
  private final LocalInstanceContext localInstanceContext;
  private final LemmyPersonMentionService lemmyPersonMentionService;
  private final CommentReplyRepository commentReplyRepository;
  private final LemmyCommentReplyService lemmyCommentReplyService;
  private final PrivateMessageRepository privateMessageRepository;

  @Operation(summary = "Get the details for a person.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetPersonDetailsResponse.class))})})
  @GetMapping()
  GetPersonDetailsResponse show(@Valid final GetPersonDetails getPersonDetailsForm) {

    Long userId = null;
    Person person = null;
    if (getPersonDetailsForm.person_id() != null) {
      userId = (long) getPersonDetailsForm.person_id();
      person = personRepository.findById(userId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_id_given"));
    } else if (getPersonDetailsForm.username() != null) {
      person = personRepository.findOneByName(getPersonDetailsForm.username())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_id_given"));
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no_id_given");
    }

    return GetPersonDetailsResponse.builder().person_view(lemmyPersonService.getPersonView(person))
        .posts(lemmyPersonService.getPersonPosts(person))
        .moderates(lemmyPersonService.getPersonModerates(person))
        .comments(lemmyPersonService.getPersonComments(person)).build();
  }

  @Operation(summary = "Get mentions for your user.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetPersonMentionsResponse.class))})})
  @GetMapping("mention")
  GetPersonMentionsResponse mention(@Valid final GetPersonMentions getPersonMentionsForm) {

    final PersonMentionSearchCriteria criteria = PersonMentionSearchCriteria.builder()
        .sort(getPersonMentionsForm.sort())
        .unreadOnly(getPersonMentionsForm.unread_only().orElse(false))
        .page(getPersonMentionsForm.page()).perPage(getPersonMentionsForm.limit()).build();

    final List<PersonMention> personMentions = personMentionRepository.allPersonMentionBySearchCriteria(
        criteria);

    final List<PersonMentionView> personMentionViews = new ArrayList<>();

    personMentions.forEach(personMention -> personMentionViews.add(
        lemmyPersonMentionService.getPersonMentionView(personMention)));

    return GetPersonMentionsResponse.builder().mentions(personMentionViews).build();
  }

  @Operation(summary = "Mark a person mention as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonMentionResponse.class))})})
  @PostMapping("mention/mark_as_read")
  PersonMentionResponse mentionMarkAsRead(
      @Valid final MarkPersonMentionAsRead markPersonMentionAsReadForm) {

    final PersonMention personMention = personMentionRepository.findById(
        (long) markPersonMentionAsReadForm.person_mention_id()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_mention_not_found"));

    personMention.setRead(markPersonMentionAsReadForm.read());

    personMentionRepository.save(personMention);

    final PersonMentionView personMentionView = conversionService.convert(personMention,
        PersonMentionView.class);

    return PersonMentionResponse.builder().person_mention_view(personMentionView).build();
  }

  @Operation(summary = "Get comment replies.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetRepliesResponse.class))})})
  @GetMapping("replies")
  GetRepliesResponse replies(@Valid final GetReplies getReplies, JwtPerson principal) {

    final Person person = Optional.ofNullable((Person) principal.getPrincipal())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    final List<CommentReply> commentReplies = commentReplyRepository.allCommentReplysBySearchCriteria(
        com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria.builder()
            .sortType(getReplies.sort())
            .unreadOnly(getReplies.unread_only() != null && getReplies.unread_only())
            .page(getReplies.page()).perPage(getReplies.limit()).build());

    final List<CommentReplyView> commentReplyViews = new ArrayList<>();

    commentReplies.forEach(commentReply -> commentReplyViews.add(
        lemmyCommentReplyService.createCommentReplyView(commentReply, person)));

    return GetRepliesResponse.builder().replies(commentReplyViews).build();
  }

  @Operation(summary = "Get a list of banned users.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BannedPersonsResponse.class))})})
  @GetMapping("banned")
  BannedPersonsResponse bannedList() {

    final Collection<PersonView> bannedPersons = new LinkedHashSet<>();
    return BannedPersonsResponse.builder().banned(bannedPersons).build();
  }

  @Operation(summary = "Mark all replies as read.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetRepliesResponse.class))})})
  @PostMapping("mark_all_as_read")
  GetRepliesResponse markAllAsRead() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Save your user settings.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))})})
  @Transactional
  @PutMapping("save_user_settings")
  public LoginResponse saveUserSettings(@Valid @RequestBody SaveUserSettings saveUserSettingsForm,
      JwtPerson principal) {

    Person person = Optional.ofNullable((Person) principal.getPrincipal())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    // @todo expand form validation to check for email formatting, etc.
    person.setShowNsfw(
        saveUserSettingsForm.show_nsfw() != null && saveUserSettingsForm.show_nsfw());
    // @todo add blur nfsw content
    // @todo add auto expand media
    // @todo show bot accounts
    person.setShowScores(
        saveUserSettingsForm.show_scores() != null && saveUserSettingsForm.show_scores());
    person.setDefaultTheme(
        saveUserSettingsForm.theme() != null ? saveUserSettingsForm.theme() : "");
    person.setDefaultSortType(
        conversionService.convert(saveUserSettingsForm.default_sort_type(), SortType.class));
    person.setDefaultListingType(
        conversionService.convert(saveUserSettingsForm.default_listing_type(), ListingType.class));
    person.setInterfaceLanguage(saveUserSettingsForm.interface_language() != null
        ? saveUserSettingsForm.interface_language() : "");
    person.setAvatarImageUrl(saveUserSettingsForm.avatar());
    person.setBannerImageUrl(saveUserSettingsForm.banner());
    person.setDisplayName(
        saveUserSettingsForm.display_name() != null ? saveUserSettingsForm.display_name() : "");
    person.setEmail(saveUserSettingsForm.email()); // @todo verify email again?
    person.setBiography(saveUserSettingsForm.bio() != null ? saveUserSettingsForm.bio() : "");
    // @todo matrix user
    person.setShowAvatars(
        saveUserSettingsForm.show_avatars() != null && saveUserSettingsForm.show_avatars());
    person.setSendNotificationsToEmail(saveUserSettingsForm.send_notifications_to_email() != null
        && saveUserSettingsForm.send_notifications_to_email());
    person.setBotAccount(
        saveUserSettingsForm.bot_account() != null && saveUserSettingsForm.bot_account());
    person.setShowReadPosts(
        saveUserSettingsForm.show_read_posts() != null && saveUserSettingsForm.show_read_posts());
    person.setShowNewPostNotifications(saveUserSettingsForm.show_new_post_notifs() != null
        && saveUserSettingsForm.show_new_post_notifs());
    // @todo generate_totp_2fa
    person.setOpenLinksInNewTab(saveUserSettingsForm.open_links_in_new_tab() != null
        && saveUserSettingsForm.open_links_in_new_tab());

    final List<Language> languages = new ArrayList<>();
    for (String languageCode : saveUserSettingsForm.discussion_languages()) {
      final Optional<Language> language = localInstanceContext.languageRepository()
          .findById(Long.valueOf(languageCode));
      language.ifPresent(languages::add);
    }
    person.setLanguages(languages);

    personService.updatePerson(person);

    return LoginResponse.builder().jwt(null).registration_created(false).verify_email_sent(false)
        .build();
  }

  @Operation(summary = "Get your unread counts.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetUnreadCountResponse.class))})})
  @GetMapping("unread_count")
  GetUnreadCountResponse unreadCount(@Valid final GetUnreadCount getUnreadCountForm,
      JwtPerson principal) {

    final Person person = Optional.ofNullable((Person) principal.getPrincipal())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    GetUnreadCountResponse.GetUnreadCountResponseBuilder builder = GetUnreadCountResponse.builder();

    builder.mentions((int) personMentionRepository.countByRecipientAndIsReadFalse(person));
    builder.replies((int) commentReplyRepository.countByRecipientAndIsReadFalse(person));
    builder.private_messages((int) privateMessageRepository.countByRecipientAndIsReadFalse(person));

    return builder.build();
  }
}
