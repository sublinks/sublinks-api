package com.sublinks.sublinksapi.api.lemmy.v3.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.CommunityModeratorViewMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.CommunityResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.CreateCommunityFormMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.GetCommunityResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.AddModToCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.AddModToCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.BanFromCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.BlockCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.BlockCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.DeleteCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.HideCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.RemoveCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.TransferCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPerson;
import com.sublinks.sublinksapi.authorization.AuthorizationService;
import com.sublinks.sublinksapi.community.CommunityRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.LinkPersonCommunityRepository;
import com.sublinks.sublinksapi.util.KeyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/community")
public class CommunityModActionsController {
    private final LocalInstanceContext localInstanceContext;

    private final CommunityRepository communityRepository;

    private final LinkPersonCommunityRepository linkPersonCommunityRepository;

    private final CreateCommunityFormMapper createCommunityFormMapper;

    private final KeyService keyService;

    private final AuthorizationService authorizationService;

    private final LemmyCommunityService lemmyCommunityService;

    private final CommunityResponseMapper communityResponseMapper;

    private final LemmyCommunityMapper lemmyCommunityMapper;

    private final GetCommunityResponseMapper getCommunityResponseMapper;


    public CommunityModActionsController(LocalInstanceContext localInstanceContext, CommunityRepository communityRepository, LinkPersonCommunityRepository linkPersonCommunityRepository, CreateCommunityFormMapper createCommunityFormMapper, KeyService keyService, AuthorizationService authorizationService, LemmyCommunityService lemmyCommunityService, CommunityResponseMapper communityResponseMapper, LemmyCommunityMapper lemmyCommunityMapper, GetCommunityResponseMapper getCommunityResponseMapper, CommunityModeratorViewMapper communityModeratorViewMapper) {
        this.localInstanceContext = localInstanceContext;
        this.communityRepository = communityRepository;
        this.linkPersonCommunityRepository = linkPersonCommunityRepository;
        this.createCommunityFormMapper = createCommunityFormMapper;
        this.keyService = keyService;
        this.authorizationService = authorizationService;
        this.lemmyCommunityService = lemmyCommunityService;
        this.communityResponseMapper = communityResponseMapper;
        this.lemmyCommunityMapper = lemmyCommunityMapper;
        this.getCommunityResponseMapper = getCommunityResponseMapper;
    }

    @PutMapping("hide")
    CommunityResponse hide(@Valid HideCommunity hideCommunityForm) {
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
