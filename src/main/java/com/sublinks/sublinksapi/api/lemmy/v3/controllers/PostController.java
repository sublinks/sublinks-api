package com.sublinks.sublinksapi.api.lemmy.v3.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.post.CreatePostMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.mappers.post.PostViewMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.CreatePost;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetPostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetPostsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetSiteMetadataResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.ListPostReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.services.LemmyCommunityService;
import com.sublinks.sublinksapi.authorization.AuthorizationService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.community.CommunityRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.language.LanguageRepository;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostRepository;
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

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping(path = "/api/v3/post")
public class PostController {
    private final LocalInstanceContext localInstanceContext;
    private final AuthorizationService authorizationService;
    private final KeyService keyService;
    private final LemmyCommunityService lemmyCommunityService;
    private final LanguageRepository languageRepository;
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final CreatePostMapper createPostMapper;
    private final PostViewMapper postViewMapper;

    public PostController(
            LocalInstanceContext localInstanceContext,
            AuthorizationService authorizationService,
            KeyService keyService,
            LemmyCommunityService lemmyCommunityService,
            LanguageRepository languageRepository,
            CommunityRepository communityRepository,
            PostRepository postRepository,
            CreatePostMapper createPostMapper,
            PostViewMapper postViewMapper
    ) {
        this.localInstanceContext = localInstanceContext;
        this.authorizationService = authorizationService;
        this.keyService = keyService;
        this.lemmyCommunityService = lemmyCommunityService;
        this.languageRepository = languageRepository;
        this.communityRepository = communityRepository;
        this.postRepository = postRepository;
        this.createPostMapper = createPostMapper;
        this.postViewMapper = postViewMapper;
    }

    @PostMapping
    @Transactional
    public PostResponse create(
            @Valid @RequestBody CreatePost createPostForm,
            UsernamePasswordAuthenticationToken principal
    ) {
        Community community = communityRepository.findById((long) createPostForm.community_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Person person = (Person) principal.getPrincipal();
        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.create)
                .onEntity(AuthorizedEntityType.post)
                .defaultResponse(community.isPostingRestrictedToMods() ? AuthorizationService.ResponseType.decline : AuthorizationService.ResponseType.allow)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        KeyStore keys = keyService.generate();
        Post post = createPostMapper.map(
                createPostForm,
                person,
                localInstanceContext.instance(),
                community,
                languageRepository.findById((long) createPostForm.language_id()).get(),
                keys
        );
        post.setCommunity(community);

        postRepository.saveAndFlush(post);

        SubscribedType subscribedType = lemmyCommunityService.getPersonCommunitySubscribeType(person, community);

        return PostResponse.builder()
                .post_view(postViewMapper.map(
                        post,
                        community,
                        subscribedType,
                        person
                ))
                .build();
    }

    @GetMapping
    GetPostResponse show() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    PostResponse update() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("delete")
    PostResponse delete() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("remove")
    PostResponse remove() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_as_read")
    PostResponse markAsRead() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("lock")
    PostResponse lock() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("feature")
    PostResponse feature() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("list")
    GetPostsResponse index() {
        Collection<PostView> posts = new HashSet<>();
        return GetPostsResponse.builder()
                .posts(posts)
                .build();
    }

    @PostMapping("like")
    PostResponse like() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("save")
    PostResponse saveForLater() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report")
    PostReportResponse report() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report/resolve")
    PostReportResponse reportResolve() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("report/list")
    ListPostReportsResponse reportList() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("site_metadata")
    GetSiteMetadataResponse siteMetadata() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
