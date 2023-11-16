package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.CreatePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.DeletePost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.EditPost;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.services.LemmyPostService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.utils.SiteMetadataUtil;
import com.sublinks.sublinksapi.utils.SlugUtil;
import com.sublinks.sublinksapi.utils.UrlUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/api/v3/post")
@Tag(name = "post", description = "the post API")
public class PostOwnerController {
    private final LocalInstanceContext localInstanceContext;
    private final AuthorizationService authorizationService;
    private final PostRepository postRepository;
    private final LemmyPostService lemmyPostService;
    private final PostService postService;
    private final LanguageRepository languageRepository;
    private final CommunityRepository communityRepository;
    private final SlugUtil slugUtil;
    private final PersonService personService;
    private final SiteMetadataUtil siteMetadataUtil;
    private final UrlUtil urlUtil;

    @PostMapping
    @Transactional
    public PostResponse create(@Valid @RequestBody final CreatePost createPostForm, JwtPerson principal) {

        final Community community = communityRepository.findById((long) createPostForm.community_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Person person = null;
        if (principal != null) {
            person = (Person) principal.getPrincipal();
        }
        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.create)
                .onEntity(AuthorizedEntityType.post)
                .defaultResponse(community.isPostingRestrictedToMods() ? AuthorizationService.ResponseType.decline : AuthorizationService.ResponseType.allow)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // Language
        Optional<Language> language;
        if (createPostForm.language_id() != null) {
            language = languageRepository.findById((long) createPostForm.language_id());
        } else {
            language = personService.getPersonDefaultPostLanguage(person, community);
        }

        if (language.isEmpty()) {
            throw new RuntimeException("No language selected");
        }

        String url = createPostForm.url();
        SiteMetadataUtil.SiteMetadata metadata = null;
        if (createPostForm.url() != null) {
            url = urlUtil.normalizeUrl(createPostForm.url());
            urlUtil.checkUrlProtocol(url);
            metadata = siteMetadataUtil.fetchSiteMetadata(url);
        }

        Post.PostBuilder postBuilder = Post.builder()
                .instance(localInstanceContext.instance())
                .community(community)
                .language(language.get())
                .title(createPostForm.name())
                .titleSlug(slugUtil.uniqueSlug(createPostForm.name()))
                .postBody(createPostForm.body())
                .isNsfw((createPostForm.nsfw() != null && createPostForm.nsfw()));

        if (url != null) {
            postBuilder.linkUrl(url);
            if (metadata != null) {
                postBuilder.linkTitle(metadata.title())
                        .linkDescription(metadata.description())
                        .linkVideoUrl(metadata.videoUrl())
                        .linkThumbnailUrl(metadata.imageUrl());
            }
        }

        Post post = postBuilder.build();

        postService.createPost(post, person);

        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post, person))
                .build();
    }

    @PutMapping
    PostResponse update(@Valid @RequestBody EditPost editPostForm, JwtPerson principal) {

        Post post = postRepository.findById((long) editPostForm.post_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Person person = null;
        if (principal != null) {
            person = (Person) principal.getPrincipal();
        }
        authorizationService
                .canPerson(person)
                .defaultingToDecline()
                .performTheAction(AuthorizeAction.update)
                .onEntity(post)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        post.setTitle(editPostForm.name());
        post.setPostBody(editPostForm.body());
        post.setNsfw((editPostForm.nsfw() != null && editPostForm.nsfw()));
        post.setLanguage(languageRepository.findById((long) editPostForm.language_id()).get()); // @todo catch errors here
        post.setLinkUrl(editPostForm.url());

        postService.updatePost(post);

        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post, person))
                .build();
    }

    @PostMapping("delete")
    PostResponse delete(@Valid @RequestBody DeletePost deletePostForm, JwtPerson principal) {

        Post post = postRepository.findById((long) deletePostForm.post_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Person person = (Person) principal.getPrincipal();

        authorizationService
                .canPerson(person)
                .defaultingToDecline()
                .performTheAction(AuthorizeAction.delete)
                .onEntity(post)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        post.setDeleted(deletePostForm.deleted());

        postService.softDeletePost(post);

        return PostResponse.builder()
                .post_view(lemmyPostService.postViewFromPost(post, person))
                .build();
    }
}
