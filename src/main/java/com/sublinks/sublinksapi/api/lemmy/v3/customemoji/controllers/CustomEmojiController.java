package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CreateCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.DeleteCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.EditCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.customemoji.dto.CustomEmoji;
import com.sublinks.sublinksapi.customemoji.dto.CustomEmojiKeyword;
import com.sublinks.sublinksapi.customemoji.repository.CustomEmojiKeywordRepository;
import com.sublinks.sublinksapi.customemoji.repository.CustomEmojiRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v3/custom_emoji")
@Tag(name = "CustomEmoji")
public class CustomEmojiController extends AbstractLemmyApiController {

  private final AuthorizationService authorizationService;
  private final LocalInstanceContext localInstanceContext;
  private final CustomEmojiRepository customEmojiRepository;
  private final CustomEmojiKeywordRepository customEmojiKeywordRepository;

  @Operation(summary = "Create a new custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomEmojiResponse.class)) })
  })
  @PostMapping
  @Transactional
  CustomEmojiResponse create(@Valid @RequestBody final CreateCustomEmoji createCustomEmojiForm, JwtPerson principal) {

    final var person = getPersonOrThrowUnauthorized(principal);

    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    final var customEmoji = CustomEmoji.builder()
        .altText(createCustomEmojiForm.alt_text())
        .category(createCustomEmojiForm.category())
        .imageUrl(createCustomEmojiForm.image_url())
        .shortCode(createCustomEmojiForm.shortcode().toLowerCase().trim())
        .localSiteId(localInstanceContext.instance().getId())
        .build();
    final var emojiEntity = customEmojiRepository.save(customEmoji);

    var keywords = new ArrayList<CustomEmojiKeyword>();
    for (var keyword : createCustomEmojiForm.keywords()) {
      keywords.add(
          CustomEmojiKeyword.builder()
              .keyword(keyword.toLowerCase().trim())
              .emoji(emojiEntity)
              .build());
    }
    customEmojiKeywordRepository.saveAll(keywords);

    return CustomEmojiResponse.builder()
        .custom_emoji(CustomEmojiView
            .builder()
            .keywords(new ArrayList<String>(createCustomEmojiForm.keywords()))
            .custom_emoji(
                com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmoji.builder()
                    .id(emojiEntity.getId())
                    .local_site_id(emojiEntity.getLocalSiteId())
                    .shortcode(emojiEntity.getShortCode())
                    .image_url(emojiEntity.getImageUrl())
                    .alt_text(emojiEntity.getAltText())
                    .category(emojiEntity.getCategory())
                    .published(emojiEntity.getCreatedAt().toString())
                    .updated(emojiEntity.getUpdatedAt().toString())
                    .build())
            .build())
        .build();

  }

  @Operation(summary = "Edit a custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomEmojiResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "Custom Emoji Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)) })
  })
  @PutMapping
  CustomEmojiResponse update(@Valid final EditCustomEmoji editCustomEmojiForm, JwtPerson principal) {

    final var person = getPersonOrThrowUnauthorized(principal);

    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);

  }

  @Operation(summary = "Delete a custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomEmojiResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "Custom Emoji Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)) })
  })
  @PostMapping("delete")
  CustomEmojiResponse delete(@Valid final DeleteCustomEmoji deleteCustomEmojiForm) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
