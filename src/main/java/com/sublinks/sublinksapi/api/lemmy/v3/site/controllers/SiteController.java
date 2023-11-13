package com.sublinks.sublinksapi.api.lemmy.v3.site.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstance;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstanceResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.CreateSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.EditSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.LemmySiteService;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.MyUserInfoService;
import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.dto.InstanceBlock;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceBlockRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.instance.services.InstanceService;
import com.sublinks.sublinksapi.language.services.LanguageService;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/site")
public class SiteController {
    private final LocalInstanceContext localInstanceContext;
    private final LemmySiteService lemmySiteService;
    private final InstanceService instanceService;
    private final LanguageService languageService;
    private final InstanceRepository instanceRepository;
    private final InstanceBlockRepository instanceBlockRepository;
    private final MyUserInfoService myUserInfoService;

    @GetMapping
    public GetSiteResponse getSite(final JwtPerson jwtPerson) {
        Person person = null;
        if (jwtPerson != null) {
            person = (Person) jwtPerson.getPrincipal();
        }

        GetSiteResponse.GetSiteResponseBuilder builder = GetSiteResponse.builder()
                .version("0.19.0")
                .taglines(new ArrayList<>())
                .site_view(lemmySiteService.getSiteView())
                .discussion_languages(languageService.instanceLanguageIds(localInstanceContext.instance()))
                .all_languages(lemmySiteService.allLanguages(localInstanceContext.languageRepository()))
                .custom_emojis(lemmySiteService.customEmojis())
                .admins(lemmySiteService.admins());

        if (person != null) {
            builder.my_user(myUserInfoService.getMyUserInfo(person));
        }

        return builder.build();


    }

    @PostMapping
    @Transactional
    public SiteResponse createSite(@Valid @RequestBody final CreateSite createSiteForm) {

        final Instance instance = localInstanceContext.instance();
        instance.setName(createSiteForm.name());
        instance.setDomain(localInstanceContext.settings().getBaseUrl());
        instance.setActivityPubId(localInstanceContext.settings().getBaseUrl());
        instance.setSoftware("sublinks");
        instance.setVersion("0.1.0");
        instance.setDescription(createSiteForm.description() == null ? null : createSiteForm.description());
        instance.setSidebar(createSiteForm.sidebar() == null ? null : createSiteForm.sidebar());
        instance.setLanguages(languageService.languageIdsToEntity(createSiteForm.discussion_languages()));
        instance.setBannerUrl(createSiteForm.banner());
        instance.setIconUrl(createSiteForm.icon());
        instanceService.createInstance(instance);
        return SiteResponse.builder()
                .site_view(lemmySiteService.getSiteView())
                .tag_lines(new ArrayList<>())
                .build();
    }

    @PutMapping
    @Transactional
    public SiteResponse updateSite(@Valid @RequestBody final EditSite editSiteForm) {

        final Instance instance = localInstanceContext.instance();
        instance.setName(editSiteForm.name());
        instance.setDescription(editSiteForm.description() == null ? null : editSiteForm.description());
        instance.setSidebar(editSiteForm.sidebar() == null ? null : editSiteForm.sidebar());
        instance.setLanguages(languageService.languageIdsToEntity(editSiteForm.discussion_languages()));
        instance.setBannerUrl(editSiteForm.banner()); // @todo image
        instance.setIconUrl(editSiteForm.icon()); // @todo image
        instanceService.updateInstance(instance);
        return SiteResponse.builder()
                .site_view(lemmySiteService.getSiteView())
                .tag_lines(new ArrayList<>())
                .build();
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
