package com.sublinksapp.sublinksappapi.api.lemmy.v3.controllers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.announcment.Announcement;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.site.CreateSiteFormMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.site.GetSiteResponseMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.site.SiteResponseMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.BlockInstance;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.CreateSite;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests.EditSite;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.BlockInstanceResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.GetSiteResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.SiteResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.services.SiteService;
import com.sublinksapp.sublinksappapi.instance.Instance;
import com.sublinksapp.sublinksappapi.instance.InstanceRepository;
import com.sublinksapp.sublinksappapi.instance.LocalInstanceContext;
import com.sublinksapp.sublinksappapi.person.PersonContext;
import com.sublinksapp.sublinksappapi.util.KeyService;
import com.sublinksapp.sublinksappapi.util.KeyStore;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
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
    private final PersonContext personContext;
    private final SiteService siteService;
    private final InstanceRepository instanceRepository;
    private final KeyService keyService;
    private final GetSiteResponseMapper getSiteResponseMapper;
    private final CreateSiteFormMapper createSiteFormMapper;
    private final SiteResponseMapper siteResponseMapper;


    public SiteController(
            LocalInstanceContext localInstanceContext,
            PersonContext personContext,
            SiteService siteService,
            InstanceRepository instanceRepository,
            KeyService keyService,
            GetSiteResponseMapper getSiteResponseMapper, CreateSiteFormMapper createSiteFormMapper,
            SiteResponseMapper siteResponseMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.personContext = personContext;
        this.siteService = siteService;
        this.instanceRepository = instanceRepository;
        this.keyService = keyService;
        this.getSiteResponseMapper = getSiteResponseMapper;
        this.createSiteFormMapper = createSiteFormMapper;
        this.siteResponseMapper = siteResponseMapper;
    }

    @GetMapping
    public GetSiteResponse getSite() {
        Collection<Announcement> announcements = new HashSet<>();
        return getSiteResponseMapper.map(
                localInstanceContext,
                personContext,
                announcements,
                siteService.admins(),
                siteService.allLanguages(localInstanceContext.languageRepository()),
                siteService.customEmojis(),
                siteService.discussionLanguages()
        );
    }

    @PostMapping
    @Transactional
    public SiteResponse createSite(@Valid @RequestBody CreateSite createSiteForm) {
        KeyStore keys = keyService.generate();
        Instance instance = localInstanceContext.instance();
        createSiteFormMapper.CreateSiteToInstance(
                createSiteForm,
                localInstanceContext,
                keys,
                instance
        );
        instanceRepository.save(instance);

        Collection<Announcement> announcements = new HashSet<>();
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PutMapping
    @Transactional
    public SiteResponse updateSite(@Valid @RequestBody EditSite editSiteForm) {
        Collection<Announcement> announcements = new HashSet<>();
        //@todo edit site
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PostMapping("/block")
    public BlockInstanceResponse blockInstance(@Valid BlockInstance blockInstanceForm) {
        //@todo block instance
        return new BlockInstanceResponse(false);
    }
}
