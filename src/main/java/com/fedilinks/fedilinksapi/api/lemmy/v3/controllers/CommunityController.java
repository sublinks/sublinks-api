package com.fedilinks.fedilinksapi.api.lemmy.v3.controllers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.AddModToCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.BanPerson;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.BlockCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.DeleteCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.EditCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.FollowCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.GetCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.HideCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.ListCommunities;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.RemoveCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.TransferCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.AddModToCommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.BanFromCommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.BlockCommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.CommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetCommunityResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.ListCommunitiesResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping(path = "/api/v3/community")
public class CommunityController {
    @GetMapping
    GetCommunityResponse show(@Valid GetCommunity getCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    CommunityResponse update(@Valid EditCommunity editCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("hide")
    CommunityResponse hide(@Valid HideCommunity hideCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    ListCommunitiesResponse list(@Valid ListCommunities listCommunitiesForm) {
        Collection<CommunityView> communities = new HashSet<>();
        return ListCommunitiesResponse.builder()
                .communities(communities)
                .build();
    }

    @PutMapping("follow")
    CommunityResponse follow(@Valid FollowCommunity followCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("block")
    BlockCommunityResponse block(@Valid BlockCommunity blockCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("delete")
    CommunityResponse delete(@Valid DeleteCommunity deleteCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("remove")
    CommunityResponse remove(@Valid RemoveCommunity removeCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("transfer")
    GetCommunityResponse transfer(@Valid TransferCommunity transferCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("ban_user")
    BanFromCommunityResponse banUser(@Valid BanPerson banPersonForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mod")
    AddModToCommunityResponse addMod(@Valid AddModToCommunity addModToCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
