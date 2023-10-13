package com.fedilinks.fedilinksapi.api.lemmy.v3.controllers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.announcment.Announcement;
import com.fedilinks.fedilinksapi.api.lemmy.v3.builders.SiteBuilder;
import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.CreateSiteRequestMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.SiteMapper;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.BlockInstance;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.CreateSite;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.EditSite;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.BlockInstanceResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetSiteResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.SiteResponse;
import com.fedilinks.fedilinksapi.instance.Instance;
import com.fedilinks.fedilinksapi.instance.InstanceRepository;
import com.fedilinks.fedilinksapi.instance.LocalInstanceContext;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.util.KeyService;
import com.fedilinks.fedilinksapi.util.KeyStore;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping(path = "/api/v3/site")
public class SiteController {
    private final LocalInstanceContext localInstanceContext;

    private final SiteBuilder siteBuilder;

    private final InstanceRepository instanceRepository;

    private final KeyService keyService;

    private final CreateSiteRequestMapper createSiteRequestMapper;

    private final SiteMapper siteMapper;

    public SiteController(
            LocalInstanceContext localInstanceContext,
            SiteBuilder siteBuilder,
            InstanceRepository instanceRepository,
            KeyService keyService,
            CreateSiteRequestMapper createSiteRequestMapper,
            SiteMapper siteMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.siteBuilder = siteBuilder;
        this.instanceRepository = instanceRepository;
        this.keyService = keyService;
        this.createSiteRequestMapper = createSiteRequestMapper;
        this.siteMapper = siteMapper;
    }

    @GetMapping
    GetSiteResponse getSite(@AuthenticationPrincipal Person loggedInPerson) {
        Collection<Announcement> announcements = new HashSet<>();
        return siteMapper.toGetSiteResponse(
                localInstanceContext,
                loggedInPerson,
                announcements,
                siteBuilder.admins(),
                siteBuilder.allLanguages(localInstanceContext.languageRepository()),
                siteBuilder.customEmojis(),
                siteBuilder.discussionLanguages()
        );
    }

    @PostMapping
    SiteResponse createSite(@Valid @RequestBody CreateSite createSiteForm) {
        KeyStore keys = keyService.generate();
        Instance instance = localInstanceContext.instance();
        createSiteRequestMapper.CreateSiteToInstance(
                createSiteForm,
                localInstanceContext,
                keys,
                instance
        );
        instanceRepository.save(instance);

        Collection<Announcement> announcements = new HashSet<>();
        return siteMapper.toSiteResponse(localInstanceContext, announcements);
    }

    @PutMapping
    SiteResponse updateSite(@Valid @RequestBody EditSite editSiteForm) {
        Collection<Announcement> announcements = new HashSet<>();
        return siteMapper.toSiteResponse(localInstanceContext, announcements);
    }

    @PostMapping("/block")
    BlockInstanceResponse blockInstance(@Valid BlockInstance blockInstanceForm) {
        return new BlockInstanceResponse(true);
    }
}
