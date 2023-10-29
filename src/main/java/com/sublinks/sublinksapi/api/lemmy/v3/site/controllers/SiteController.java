package com.sublinks.sublinksapi.api.lemmy.v3.site.controllers;

import com.sublinks.sublinksapi.announcment.Announcement;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.CreateSiteFormMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.EditSiteFormMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.GetSiteResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.SiteResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstance;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstanceResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.CreateSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.EditSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.SiteService;
import com.sublinks.sublinksapi.instance.Instance;
import com.sublinks.sublinksapi.instance.InstanceBlock;
import com.sublinks.sublinksapi.instance.InstanceBlockRepository;
import com.sublinks.sublinksapi.instance.InstanceRepository;
import com.sublinks.sublinksapi.instance.InstanceService;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.language.LanguageService;
import com.sublinks.sublinksapi.person.PersonContext;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v3/site")
public class SiteController {
    private final LocalInstanceContext localInstanceContext;
    private final PersonContext personContext;
    private final SiteService siteService;
    private final InstanceService instanceService;
    private final LanguageService languageService;
    private final InstanceRepository instanceRepository;
    private final InstanceBlockRepository instanceBlockRepository;
    private final GetSiteResponseMapper getSiteResponseMapper;
    private final CreateSiteFormMapper createSiteFormMapper;
    private final EditSiteFormMapper editSiteFormMapper;
    private final SiteResponseMapper siteResponseMapper;


    public SiteController(
           final  LocalInstanceContext localInstanceContext,
            final PersonContext personContext,
            final SiteService siteService,
            final InstanceService instanceService,
            final LanguageService languageService,
            final InstanceRepository instanceRepository,
            final InstanceBlockRepository instanceBlockRepository,
            final GetSiteResponseMapper getSiteResponseMapper,
            final CreateSiteFormMapper createSiteFormMapper,
            final EditSiteFormMapper editSiteFormMapper,
            final SiteResponseMapper siteResponseMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.personContext = personContext;
        this.siteService = siteService;
        this.instanceService = instanceService;
        this.languageService = languageService;
        this.instanceRepository = instanceRepository;
        this.instanceBlockRepository = instanceBlockRepository;
        this.getSiteResponseMapper = getSiteResponseMapper;
        this.createSiteFormMapper = createSiteFormMapper;
        this.editSiteFormMapper = editSiteFormMapper;
        this.siteResponseMapper = siteResponseMapper;
    }

    @GetMapping
    public GetSiteResponse getSite() {

        // @todo announcements
        final Collection<Announcement> announcements = new HashSet<>();
        return getSiteResponseMapper.map(
                localInstanceContext,
                personContext,
                announcements,
                siteService.admins(),
                siteService.allLanguages(localInstanceContext.languageRepository()),
                siteService.customEmojis(),
                languageService.instanceLanguageIds(localInstanceContext.instance())
        );
    }

    @PostMapping
    @Transactional
    public SiteResponse createSite(@Valid @RequestBody final CreateSite createSiteForm) {

        final Instance instance = localInstanceContext.instance();
        createSiteFormMapper.map(createSiteForm, localInstanceContext, instance);
        instance.setLanguages(languageService.languageIdsToEntity(createSiteForm.discussion_languages()));
        instanceService.createInstance(instance);
        final Collection<Announcement> announcements = new HashSet<>();
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PutMapping
    @Transactional
    public SiteResponse updateSite(@Valid @RequestBody final EditSite editSiteForm) {

        final Collection<Announcement> announcements = new HashSet<>();
        final Instance instance = localInstanceContext.instance();
        instance.setLanguages(languageService.languageIdsToEntity(editSiteForm.discussion_languages()));
        editSiteFormMapper.map(editSiteForm, instance);
        instanceService.updateInstance(instance);
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PostMapping("/block")
    @Transactional
    public BlockInstanceResponse blockInstance(@Valid @RequestBody final BlockInstance blockInstanceForm, final Principal principal) {

        // @todo ensure they are an admin or authorized to block an instance
        if (!(principal instanceof JwtPerson)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final Optional<Instance> instance = instanceRepository.findById((long) blockInstanceForm.instance_id());
        if (instance.isEmpty()) {
            return new BlockInstanceResponse(false);
        }
        final InstanceBlock instanceBlock = instanceBlockRepository.findInstanceBlockByInstance(instance.get());
        if (blockInstanceForm.block() && instanceBlock == null) {
            instanceBlockRepository.save(InstanceBlock.builder().instance(instance.get()).build());
        } else if (!blockInstanceForm.block()) {
            if (instanceBlock != null) {
                instanceBlockRepository.delete(instanceBlock);
            }
            return new BlockInstanceResponse(false);
        }
        return new BlockInstanceResponse(true);
    }
}
