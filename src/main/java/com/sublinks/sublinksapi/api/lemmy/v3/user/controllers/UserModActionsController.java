package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BlockPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetReportCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
public class UserModActionsController {
    @PostMapping("ban")
    BanPersonResponse ban() {

        return BanPersonResponse.builder().build();
    }

    @PostMapping("block")
    BlockPersonResponse block() {

        return BlockPersonResponse.builder().build();
    }

    @GetMapping("report_count")
    GetReportCountResponse reportCount() {

        return GetReportCountResponse.builder().build();
    }

    @PostMapping("leave_admin")
    GetSiteResponse leaveAdmin() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
