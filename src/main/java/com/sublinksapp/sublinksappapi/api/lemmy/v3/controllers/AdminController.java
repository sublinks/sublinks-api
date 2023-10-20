package com.sublinksapp.sublinksappapi.api.lemmy.v3.controllers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.AddAdmin;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.ApproveRegistrationApplication;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.ListRegistrationApplications;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.PurgeComment;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.PurgeCommunity;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.PurgePerson;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.PurgePost;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.AddAdminResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.GetUnreadRegistrationApplicationCountResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.ListRegistrationApplicationsResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.PurgeItemResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.RegistrationApplicationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/admin")
public class AdminController {
    @PostMapping("add")
    AddAdminResponse create(@Valid AddAdmin addAdminForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("registration_application/count")
    GetUnreadRegistrationApplicationCountResponse registrationApplicationCount() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("registration_application/list")
    ListRegistrationApplicationsResponse registrationApplicationList(@Valid ListRegistrationApplications listRegistrationApplicationsForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("registration_application/approve")
    RegistrationApplicationResponse registrationApplicationApprove(@Valid ApproveRegistrationApplication approveRegistrationApplicationForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/person")
    PurgeItemResponse purgePerson(@Valid PurgePerson purgePersonForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/community")
    PurgeItemResponse purgeCommunity(@Valid PurgeCommunity purgeCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/post")
    PurgeItemResponse purgePost(@Valid PurgePost purgePostForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/comment")
    PurgeItemResponse purgeComment(@Valid PurgeComment purgeCommentForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
