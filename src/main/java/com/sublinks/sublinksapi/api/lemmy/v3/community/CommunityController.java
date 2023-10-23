package com.sublinks.sublinksapi.api.lemmy.v3.community;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.AddModToCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.BanPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.BlockCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.CreateCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.DeleteCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.EditCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.FollowCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.GetCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.HideCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.ListCommunities;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.RemoveCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.TransferCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.AddModToCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.BanFromCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.BlockCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.ListCommunitiesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v3/community")
public class CommunityController {
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


    public CommunityController(LocalInstanceContext localInstanceContext, CommunityRepository communityRepository, LinkPersonCommunityRepository linkPersonCommunityRepository, CreateCommunityFormMapper createCommunityFormMapper, KeyService keyService, AuthorizationService authorizationService, LemmyCommunityService lemmyCommunityService, CommunityResponseMapper communityResponseMapper, LemmyCommunityMapper lemmyCommunityMapper, GetCommunityResponseMapper getCommunityResponseMapper, CommunityModeratorViewMapper communityModeratorViewMapper) {
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

    @PostMapping
    @Transactional
    public CommunityResponse create(@Valid @RequestBody CreateCommunity createCommunityForm, UsernamePasswordAuthenticationToken principal) {
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

        List<String> languageCodes = new ArrayList<>();
        for (Language language :
                languages) {
            languageCodes.add(language.getCode());
        }

        return communityResponseMapper.map(
                communityView,
                languageCodes
        );
    }

    @GetMapping
    public GetCommunityResponse show(@Valid GetCommunity getCommunityForm, UsernamePasswordAuthenticationToken principal) {
        Community community = communityRepository.findCommunityByIdOrTitleSlug(
                getCommunityForm.id(), getCommunityForm.name()
        );
        CommunityView communityView;
        if (principal != null) {
            Person person = (Person) principal.getPrincipal();
            communityView = lemmyCommunityService.communityViewFromCommunity(community, person);
        } else {
            communityView = lemmyCommunityService.communityViewFromCommunity(community);
        }
        Set<String> languageCodes = lemmyCommunityService.communityLanguageCodes(community);
        List<CommunityModeratorView> moderatorViews = lemmyCommunityService.communityModeratorViewList(community);
        return getCommunityResponseMapper.map(
                communityView,
                languageCodes,
                moderatorViews,
                localInstanceContext
        );
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
    @Transactional
    public ListCommunitiesResponse list(@Valid ListCommunities listCommunitiesForm, UsernamePasswordAuthenticationToken principal) {
        Collection<CommunityView> communityViews = new HashSet<>();

        Collection<Community> communities = communityRepository.findAll(); // @todo apply filters
        for (Community community : communities) {
            CommunityView communityView;
            if (principal != null) {
                Person person = (Person) principal.getPrincipal();
                communityView = lemmyCommunityService.communityViewFromCommunity(community, person);
            } else {
                communityView = lemmyCommunityService.communityViewFromCommunity(community);
            }
            communityViews.add(communityView);
        }

        return ListCommunitiesResponse.builder()
                .communities(communityViews)
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
