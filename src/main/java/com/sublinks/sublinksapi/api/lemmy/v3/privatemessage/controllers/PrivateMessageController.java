package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.CreatePrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.DeletePrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.EditPrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.GetPrivateMessages;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.ListPrivateMessageReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.MarkPrivateMessageAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageView;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessagesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.services.LemmyPrivateMessageService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.enums.PrivateMessageSortType;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageSearchCriteria;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageRepository;
import com.sublinks.sublinksapi.privatemessages.services.PrivateMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/private_message")
@Tag(name = "PrivateMessage")
public class PrivateMessageController extends AbstractLemmyApiController {

  private final PrivateMessageService privateMessageService;
  private final PrivateMessageRepository privateMessageRepository;
  private final LemmyPrivateMessageService lemmyPrivateMessageService;
  private final PersonRepository personRepository;
  private final AuthorizationService authorizationService;

  @Operation(summary = "Get / fetch private messages.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessagesResponse.class))})
  })
  @GetMapping("list")
  PrivateMessagesResponse list(@Valid final GetPrivateMessages getPrivateMessagesForm,
      final JwtPerson principal) {

    final Person sender = getPersonOrThrowUnauthorized(principal);

    // @todo: add support for other sort types
    final PrivateMessageSortType sortType = PrivateMessageSortType.New;

    final PrivateMessageSearchCriteria privateMessageSearchCriteria
        = PrivateMessageSearchCriteria.builder()
        .page(getPrivateMessagesForm.page() == null ? 1 : getPrivateMessagesForm.page())
        .perPage(getPrivateMessagesForm.limit() == null ? 20 : getPrivateMessagesForm.limit())
        .privateMessageSortType(sortType)
        .person(sender)
        .build();

    final List<PrivateMessage> privateMessages
        = privateMessageRepository.allPrivateMessagesBySearchCriteria(
        privateMessageSearchCriteria);
    final List<PrivateMessageView> privateMessageViews = new ArrayList<>();
    privateMessages.forEach(privateMessage -> privateMessageViews.add(
        lemmyPrivateMessageService.createPrivateMessageView(privateMessage)));

    return PrivateMessagesResponse.builder().private_messages(privateMessageViews).build();
  }

  @Operation(summary = "Create a private message.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Recipient Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping
  PrivateMessageResponse create(
      @Valid @RequestBody final CreatePrivateMessage createPrivateMessageForm,
      final JwtPerson principal) {

    final Person sender = getPersonOrThrowUnauthorized(principal);

    final Person recipient = personRepository.findById(
            (long) createPrivateMessageForm.recipient_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipient_not_found"));

    final PrivateMessage privateMessage = PrivateMessage.builder()
        .content(createPrivateMessageForm.content())
        .sender(sender)
        .recipient(recipient)
        .isLocal(recipient.isLocal())
        .build();
    privateMessageService.createPrivateMessage(privateMessage);

    final PrivateMessageView privateMessageView
        = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);

    return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
  }

  @Operation(summary = "Edit a private message.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Private Message Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PutMapping
  PrivateMessageResponse update(@Valid @RequestBody final EditPrivateMessage editPrivateMessageForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    PrivateMessage privateMessage = privateMessageRepository.findById(
            (long) editPrivateMessageForm.private_message_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

    authorizationService
        .canPerson(person)
        .performTheAction(AuthorizeAction.update)
        .onEntity(privateMessage)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    privateMessage.setContent(editPrivateMessageForm.content());

    privateMessageService.updatePrivateMessage(privateMessage);

    final PrivateMessageView privateMessageView = lemmyPrivateMessageService.createPrivateMessageView(
        privateMessage);
    return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
  }

  @Operation(summary = "Delete a private message.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Private Message Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("delete")
  PrivateMessageResponse delete(
      @Valid @RequestBody final DeletePrivateMessage deletePrivateMessageForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    PrivateMessage privateMessage = privateMessageRepository.findById(
            (long) deletePrivateMessageForm.private_message_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

    authorizationService
        .canPerson(person)
        .performTheAction(AuthorizeAction.delete)
        .onEntity(privateMessage);

    privateMessageService.deletePrivateMessage(privateMessage);

    final PrivateMessageView privateMessageView
        = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);
    return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
  }

  @Operation(summary = "Mark a private message as read.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Private Message Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("mark_as_read")
  PrivateMessageResponse markAsRead(
      @Valid @RequestBody MarkPrivateMessageAsRead markPrivateMessageAsReadForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    PrivateMessage privateMessage = privateMessageRepository.findById(
            (long) markPrivateMessageAsReadForm.private_message_id())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

    authorizationService
        .canPerson(person)
        .performTheAction(AuthorizeAction.read)
        .onEntity(privateMessage)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    privateMessage.setRead(markPrivateMessageAsReadForm.read());

    privateMessageService.updatePrivateMessage(privateMessage);

    final PrivateMessageView privateMessageView
        = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);
    return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
  }

  @Operation(summary = "Create a report for a private message.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageReportResponse.class))})
  })
  @PostMapping("report")
  PrivateMessageReportResponse report() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Resolve a report for a private message.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrivateMessageReportResponse.class))})
  })
  @PutMapping("report/resolve")
  PrivateMessageReportResponse reportResolve() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "List private message reports.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ListPrivateMessageReportsResponse.class))})
  })
  @GetMapping("report/list")
  ListPrivateMessageReportsResponse reportList() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
