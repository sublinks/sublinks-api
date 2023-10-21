package com.sublinks.sublinksapi.api.lemmy.v3.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.ListPrivateMessageReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.PrivateMessageReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.PrivateMessagesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/private_message")
public class PrivateMessageController {
    @GetMapping
    PrivateMessagesResponse list() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
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
