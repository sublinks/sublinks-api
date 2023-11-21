package com.sublinks.sublinksapi.api.lemmy.v3.site.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstance;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.BlockInstanceResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.CreateSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.EditSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.LemmySiteService;
import com.sublinks.sublinksapi.api.lemmy.v3.site.services.MyUserInfoService;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.instance.dto.InstanceBlock;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceBlockRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.instance.services.InstanceService;
import com.sublinks.sublinksapi.language.services.LanguageService;
import com.sublinks.sublinksapi.person.dto.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/site")
@Tag(name = "Site")
public class SiteController extends AbstractLemmyApiController {

  private final LocalInstanceContext localInstanceContext;
  private final LemmySiteService lemmySiteService;
  private final InstanceService instanceService;
  private final LanguageService languageService;
  private final InstanceRepository instanceRepository;
  private final InstanceBlockRepository instanceBlockRepository;
  private final MyUserInfoService myUserInfoService;
  private final AuthorizationService authorizationService;

  @Operation(summary = "Gets the site, and your user data.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GetSiteResponse.class))})
  })
  @GetMapping
  public GetSiteResponse getSite(final JwtPerson principal) {

    GetSiteResponse.GetSiteResponseBuilder builder = GetSiteResponse.builder()
        .version("0.19.0") // @todo grab this from config?
        .taglines(new ArrayList<>()) // @todo taglines
        .site_view(lemmySiteService.getSiteView())
        .discussion_languages(languageService.instanceLanguageIds(localInstanceContext.instance()))
        .all_languages(lemmySiteService.allLanguages(localInstanceContext.languageRepository()))
        .custom_emojis(lemmySiteService.customEmojis())
        .admins(lemmySiteService.admins());

    getOptionalPerson(principal).ifPresent(
        (person -> builder.my_user(myUserInfoService.getMyUserInfo(person))));

    return builder.build();
  }

  @Operation(summary = "Create your site.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = SiteResponse.class))})
  })
  @PostMapping
  @Transactional
  public SiteResponse createSite(@Valid @RequestBody final CreateSite createSiteForm,
      final JwtPerson principal) {

    if (!localInstanceContext.instance().getDomain().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    final Person person = getPersonOrThrowUnauthorized(principal);
    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    final Instance instance = localInstanceContext.instance();
    instance.setName(createSiteForm.name());
    instance.setDomain(localInstanceContext.settings().getBaseUrl());
    instance.setActivityPubId(localInstanceContext.settings().getBaseUrl());
    instance.setSoftware("sublinks");
    instance.setVersion("0.1.0");
    instance.setDescription(createSiteForm.description());
    instance.setSidebar(createSiteForm.sidebar());
    instance.setLanguages(
        languageService.languageIdsToEntity(createSiteForm.discussion_languages()));
    instance.setBannerUrl(createSiteForm.banner());
    instance.setIconUrl(createSiteForm.icon());
    instanceService.createInstance(instance);
    return SiteResponse.builder()
        .site_view(lemmySiteService.getSiteView())
        .tag_lines(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Edit your site.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = SiteResponse.class))})
  })
  @PutMapping
  @Transactional
  public SiteResponse updateSite(@Valid @RequestBody final EditSite editSiteForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    final Instance instance = localInstanceContext.instance();
    instance.setName(editSiteForm.name());
    instance.setDescription(editSiteForm.description());
    instance.setSidebar(editSiteForm.sidebar());
    instance.setLanguages(languageService.languageIdsToEntity(editSiteForm.discussion_languages()));
    instance.setBannerUrl(editSiteForm.banner());
    instance.setIconUrl(editSiteForm.icon());
    instanceService.updateInstance(instance);
    return SiteResponse.builder()
        .site_view(lemmySiteService.getSiteView())
        .tag_lines(new ArrayList<>())
        .build();
  }

  @Operation(summary = "Block an instance.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = BlockInstanceResponse.class))})
  })
  @PostMapping("/block")
  @Transactional
  public BlockInstanceResponse blockInstance(
      @Valid @RequestBody final BlockInstance blockInstanceForm, final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);
    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    final Optional<Instance> instance = instanceRepository.findById(
        (long) blockInstanceForm.instance_id());
    if (instance.isEmpty()) {
      return new BlockInstanceResponse(false);
    }
    final InstanceBlock instanceBlock = instanceBlockRepository.findInstanceBlockByInstance(
        instance.get());
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
