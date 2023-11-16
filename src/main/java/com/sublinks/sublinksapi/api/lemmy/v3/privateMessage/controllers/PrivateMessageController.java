package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.CreatePrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.DeletePrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.EditPrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.GetPrivateMessages;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.ListPrivateMessageReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.MarkPrivateMessageAsRead;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessageReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessageView;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessagesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.services.LemmyPrivateMessageService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.enums.PrivateMessageSortType;
import com.sublinks.sublinksapi.private_messages.models.PrivateMessageSearchCriteria;
import com.sublinks.sublinksapi.private_messages.repositories.PrivateMessageRepository;
import com.sublinks.sublinksapi.private_messages.services.PrivateMessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/private_message")
@Tag(name = "private_message", description = "the private message API")
public class PrivateMessageController {
    private final PrivateMessageService privateMessageService;
    private final PrivateMessageRepository privateMessageRepository;
    private final LemmyPrivateMessageService lemmyPrivateMessageService;
    private final PersonRepository personRepository;
    private final AuthorizationService authorizationService;

    @GetMapping("list")
    PrivateMessagesResponse list(@Valid final GetPrivateMessages getPrivateMessagesForm, final JwtPerson principal) {

        Person sender = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));


        // @todo: add support for other sort types
        final PrivateMessageSortType sortType = PrivateMessageSortType.New;

        final PrivateMessageSearchCriteria privateMessageSearchCriteria = PrivateMessageSearchCriteria.builder()
                .page(getPrivateMessagesForm.page() == null ? 1 : getPrivateMessagesForm.page())
                .perPage(getPrivateMessagesForm.limit() == null ? 20 : getPrivateMessagesForm.limit())
                .commentSortType(sortType)
                .person(sender)
                .build();

        final List<PrivateMessage> privateMessages = privateMessageRepository.allPrivateMessagesBySearchCriteria(privateMessageSearchCriteria);
        final List<PrivateMessageView> privateMessageViews = new ArrayList<>();
        privateMessages.forEach(privateMessage -> privateMessageViews.add(lemmyPrivateMessageService.createPrivateMessageView(privateMessage)));

        return PrivateMessagesResponse.builder().private_messages(privateMessageViews).build();
    }

    @PostMapping
    PrivateMessageResponse create(@Valid @RequestBody final CreatePrivateMessage createPrivateMessageForm, final JwtPerson principal) {

        Person sender = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));


        final Person recipient = personRepository.findById((long) createPrivateMessageForm.recipient_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipient_not_found"));

        final PrivateMessage privateMessage = PrivateMessage.builder()
                .content(createPrivateMessageForm.content())
                .sender(sender)
                .recipient(recipient)
                .isLocal(recipient.isLocal())
                .build();
        privateMessageService.createPrivateMessage(privateMessage);

        final PrivateMessageView privateMessageView = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);

        return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
    }

    @PutMapping
    PrivateMessageResponse update(@Valid @RequestBody final EditPrivateMessage editPrivateMessageForm, final JwtPerson principal) {

        Person person = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        PrivateMessage privateMessage = privateMessageRepository.findById((long) editPrivateMessageForm.private_message_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.update)
                .onEntity(privateMessage)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        privateMessage.setContent(editPrivateMessageForm.content());

        privateMessageService.updatePrivateMessage(privateMessage);

        final PrivateMessageView privateMessageView = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);
        return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
    }

    @PostMapping("delete")
    PrivateMessageResponse delete(@Valid @RequestBody final DeletePrivateMessage deletePrivateMessageForm, final JwtPerson principal) {

        Person person = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        PrivateMessage privateMessage = privateMessageRepository.findById((long) deletePrivateMessageForm.private_message_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.delete)
                .onEntity(privateMessage);

        privateMessageService.deletePrivateMessage(privateMessage);

        final PrivateMessageView privateMessageView = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);
        return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
    }

    @PostMapping("mark_as_read")
    PrivateMessageResponse markAsRead(@Valid @RequestBody MarkPrivateMessageAsRead markPrivateMessageAsReadForm, final JwtPerson principal) {

        Person person = Optional.ofNullable((Person) principal.getPrincipal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        PrivateMessage privateMessage = privateMessageRepository.findById((long) markPrivateMessageAsReadForm.private_message_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "private_message_not_found"));

        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.read)
                .onEntity(privateMessage)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        privateMessage.setRead(markPrivateMessageAsReadForm.read());

        privateMessageService.updatePrivateMessage(privateMessage);

        final PrivateMessageView privateMessageView = lemmyPrivateMessageService.createPrivateMessageView(privateMessage);
        return PrivateMessageResponse.builder().private_message_view(privateMessageView).build();
    }

    @PostMapping("report")
    PrivateMessageReportResponse report() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("report/resolve")
    PrivateMessageReportResponse reportResolve() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("report/list")
    ListPrivateMessageReportsResponse reportList() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
