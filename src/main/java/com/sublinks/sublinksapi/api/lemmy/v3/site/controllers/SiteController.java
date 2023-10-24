package com.sublinks.sublinksapi.api.lemmy.v3.site.controllers;

import com.sublinks.sublinksapi.announcment.Announcement;
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
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.language.LanguageRepository;
import com.sublinks.sublinksapi.person.PersonContext;
import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v3/site")
public class SiteController {
    private final LocalInstanceContext localInstanceContext;
    private final PersonContext personContext;
    private final SiteService siteService;
    private final InstanceRepository instanceRepository;
    private final InstanceBlockRepository instanceBlockRepository;
    private final LanguageRepository languageRepository;
    private final KeyService keyService;
    private final GetSiteResponseMapper getSiteResponseMapper;
    private final CreateSiteFormMapper createSiteFormMapper;
    private final EditSiteFormMapper editSiteFormMapper;
    private final SiteResponseMapper siteResponseMapper;


    public SiteController(
            LocalInstanceContext localInstanceContext,
            PersonContext personContext,
            SiteService siteService,
            InstanceRepository instanceRepository,
            InstanceBlockRepository instanceBlockRepository, LanguageRepository languageRepository, KeyService keyService,
            GetSiteResponseMapper getSiteResponseMapper, CreateSiteFormMapper createSiteFormMapper,
            EditSiteFormMapper editSiteFormMapper, SiteResponseMapper siteResponseMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.personContext = personContext;
        this.siteService = siteService;
        this.instanceRepository = instanceRepository;
        this.instanceBlockRepository = instanceBlockRepository;
        this.languageRepository = languageRepository;
        this.keyService = keyService;
        this.getSiteResponseMapper = getSiteResponseMapper;
        this.createSiteFormMapper = createSiteFormMapper;
        this.editSiteFormMapper = editSiteFormMapper;
        this.siteResponseMapper = siteResponseMapper;
    }

    @GetMapping
    public GetSiteResponse getSite() {
        Collection<Announcement> announcements = new HashSet<>();
        List<Long> discussionLanguages = new ArrayList<>();
        for (Language language : localInstanceContext.instance().getLanguages()) {
            discussionLanguages.add(language.getId());
        }
        return getSiteResponseMapper.map(
                localInstanceContext,
                personContext,
                announcements,
                siteService.admins(),
                siteService.allLanguages(localInstanceContext.languageRepository()),
                siteService.customEmojis(),
                discussionLanguages
        );
    }

    @PostMapping
    @Transactional
    public SiteResponse createSite(@Valid @RequestBody CreateSite createSiteForm) {
        KeyStore keys = keyService.generate();
        Instance instance = localInstanceContext.instance();
        createSiteFormMapper.map(
                createSiteForm,
                localInstanceContext,
                keys,
                instance
        );
        final List<Language> languages = new ArrayList<>();
        for (String languageCode : createSiteForm.discussion_languages()) {
            final Optional<Language> language = languageRepository.findById(Long.valueOf(languageCode));
            language.ifPresent(languages::add);
        }
        instance.setLanguages(languages);
        instanceRepository.save(instance);
        Collection<Announcement> announcements = new HashSet<>();
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PutMapping
    @Transactional
    public SiteResponse updateSite(@Valid @RequestBody EditSite editSiteForm) {
        Collection<Announcement> announcements = new HashSet<>();
        Instance instance = localInstanceContext.instance();
        final List<Language> languages = new ArrayList<>();
        for (String languageCode : editSiteForm.discussion_languages()) {
            final Optional<Language> language = languageRepository.findById(Long.valueOf(languageCode));
            language.ifPresent(languages::add);
        }
        instance.setLanguages(languages);
        editSiteFormMapper.map(editSiteForm, instance);
        instanceRepository.save(instance);
        return siteResponseMapper.map(localInstanceContext, announcements);
    }

    @PostMapping("/block")
    @Transactional
    public BlockInstanceResponse blockInstance(@Valid @RequestBody BlockInstance blockInstanceForm, Principal principal) {
        if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final Optional<Instance> instance = instanceRepository.findById((long) blockInstanceForm.instance_id());
        if (instance.isEmpty()) {
            return new BlockInstanceResponse(false);
        }
        InstanceBlock instanceBlock = instanceBlockRepository.findInstanceBlockByInstance(instance.get());
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
