package com.sublinks.sublinksapi.api.lemmy.v3.admin.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AddAdmin;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.AddAdminResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.ApproveRegistrationApplication;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.GetUnreadRegistrationApplicationCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.ListRegistrationApplications;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.ListRegistrationApplicationsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.PurgeItemResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.RegistrationApplicationResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.PurgeComment;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.PurgeCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PurgePost;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PurgePerson;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "admin", description = "the admin API")
public class AdminController extends AbstractLemmyApiController {
    @PostMapping("add")
    AddAdminResponse create(@Valid final AddAdmin addAdminForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("registration_application/count")
    GetUnreadRegistrationApplicationCountResponse registrationApplicationCount() {

        return GetUnreadRegistrationApplicationCountResponse.builder().registration_applications(0).build();
    }

    @GetMapping("registration_application/list")
    ListRegistrationApplicationsResponse registrationApplicationList(@Valid final ListRegistrationApplications listRegistrationApplicationsForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("registration_application/approve")
    RegistrationApplicationResponse registrationApplicationApprove(@Valid final ApproveRegistrationApplication approveRegistrationApplicationForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/person")
    PurgeItemResponse purgePerson(@Valid final PurgePerson purgePersonForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/community")
    PurgeItemResponse purgeCommunity(@Valid final PurgeCommunity purgeCommunityForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/post")
    PurgeItemResponse purgePost(@Valid final PurgePost purgePostForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("purge/comment")
    PurgeItemResponse purgeComment(@Valid final PurgeComment purgeCommentForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
