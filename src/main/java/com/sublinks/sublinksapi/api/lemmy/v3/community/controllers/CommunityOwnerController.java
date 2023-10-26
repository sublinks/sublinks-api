package com.sublinks.sublinksapi.api.lemmy.v3.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.CommunityResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.CreateCommunityFormMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.EditCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.authorization.AuthorizationService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.community.CommunityRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.LinkPersonCommunityRepository;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v3/community")
public class CommunityOwnerController {
    private final LocalInstanceContext localInstanceContext;

    private final CommunityRepository communityRepository;

    private final LinkPersonCommunityRepository linkPersonCommunityRepository;

    private final CreateCommunityFormMapper createCommunityFormMapper;

    private final KeyService keyService;

    private final AuthorizationService authorizationService;

    private final LemmyCommunityService lemmyCommunityService;

    private final CommunityResponseMapper communityResponseMapper;

    private final LemmyCommunityMapper lemmyCommunityMapper;


    public CommunityOwnerController(
            LocalInstanceContext localInstanceContext,
            CommunityRepository communityRepository,
            LinkPersonCommunityRepository linkPersonCommunityRepository,
            CreateCommunityFormMapper createCommunityFormMapper,
            KeyService keyService,
            AuthorizationService authorizationService,
            LemmyCommunityService lemmyCommunityService, CommunityResponseMapper communityResponseMapper,
            LemmyCommunityMapper lemmyCommunityMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.communityRepository = communityRepository;
        this.linkPersonCommunityRepository = linkPersonCommunityRepository;
        this.createCommunityFormMapper = createCommunityFormMapper;
        this.keyService = keyService;
        this.authorizationService = authorizationService;
        this.lemmyCommunityService = lemmyCommunityService;
        this.communityResponseMapper = communityResponseMapper;
        this.lemmyCommunityMapper = lemmyCommunityMapper;
    }

    @PostMapping
    @Transactional
    public CommunityResponse create(@Valid @RequestBody CreateCommunity createCommunityForm, JwtPerson principal) {
        Person person = (Person) principal.getPrincipal();
        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.create)
                .onEntity(AuthorizedEntityType.community)
                .defaultingToAllow() // @todo use site setting to allow community creation
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        final List<Language> languages = new ArrayList<>();
        for (String languageCode : createCommunityForm.discussion_languages()) {
            final Optional<Language> language = localInstanceContext.languageRepository().findById(Long.valueOf(languageCode));
            language.ifPresent(languages::add);
        }
        KeyStore keys = keyService.generate();
        Community community = createCommunityFormMapper.map(
                createCommunityForm,
                localInstanceContext.instance(),
                keys
        );
        community.setLanguages(languages);

        Set<LinkPersonCommunity> linkPersonCommunities = new HashSet<>();
        linkPersonCommunities.add(LinkPersonCommunity.builder()
                .community(community)
                .person(person)
                .linkType(LinkPersonCommunityType.owner)
                .build());
        linkPersonCommunities.add(LinkPersonCommunity.builder()
                .community(community)
                .person(person)
                .linkType(LinkPersonCommunityType.follower)
                .build());

        communityRepository.saveAndFlush(community);
        linkPersonCommunityRepository.saveAllAndFlush(linkPersonCommunities);

        CommunityView communityView = lemmyCommunityMapper.communityToCommunityView(
                community,
                SubscribedType.Subscribed,
                false,
                community.getCommunityAggregates()
        );

        return communityResponseMapper.map(
                communityView,
                lemmyCommunityService.communityLanguageCodes(community)
        );
    }

    @PutMapping
    CommunityResponse update(@Valid EditCommunity editCommunityForm) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
