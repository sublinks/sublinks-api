package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.GetComments;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.*;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.services.LemmyPrivateMessageService;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.enums.PrivateMessageSortType;
import com.sublinks.sublinksapi.private_messages.models.PrivateMessageSearchCriteria;
import com.sublinks.sublinksapi.private_messages.repositories.PrivateMessageRepository;
import com.sublinks.sublinksapi.private_messages.repositories.PrivateMessageRepositorySearch;
import com.sublinks.sublinksapi.private_messages.services.PrivateMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/private_message")
public class PrivateMessageController {
    private final PrivateMessageService privateMessageService;
    private final PrivateMessageRepository privateMessageRepository;
    private final LemmyPrivateMessageService lemmyPrivateMessageService;
    private final ConversionService conversionService;

    @GetMapping
    PrivateMessagesResponse list(@RequestParam final GetPrivateMessages getPrivateMessagesForm, final JwtPerson principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Person sender = (Person) principal.getPrincipal();

        // @todo: add support for other sort types
        final PrivateMessageSortType sortType = PrivateMessageSortType.New;

        final PrivateMessageSearchCriteria privateMessageSearchCriteria = PrivateMessageSearchCriteria.builder()
                .page(getPrivateMessagesForm.page() == null ? 1 : getPrivateMessagesForm.page())
                .perPage(getPrivateMessagesForm.limit() == null ? 20 : getPrivateMessagesForm.limit())
                .commentSortType(sortType)
                .build();

        final List<PrivateMessage> privateMessages = privateMessageRepository.allPrivateMessagesBySearchCriteria(privateMessageSearchCriteria);
        final List<PrivateMessageView> privateMessageViews = new ArrayList<>();
        privateMessages.forEach(privateMessage -> {
            privateMessageViews.add(lemmyPrivateMessageService.createPrivateMessageView(privateMessage));
        });

        return PrivateMessagesResponse.builder().private_messages(privateMessageViews).build();
    }

    @PostMapping
    PrivateMessageResponse create() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    PrivateMessageResponse update() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("delete")
    PrivateMessageResponse delete() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    PrivateMessageResponse markAsRead() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
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
