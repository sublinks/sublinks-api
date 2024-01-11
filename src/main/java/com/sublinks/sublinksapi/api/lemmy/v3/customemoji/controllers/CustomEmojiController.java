package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CreateCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.DeleteCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.DeleteCustomEmojiResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.EditCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.customemoji.dto.CustomEmoji;
import com.sublinks.sublinksapi.customemoji.repositories.CustomEmojiRepository;
import com.sublinks.sublinksapi.customemoji.services.CustomEmojiService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v3/custom_emoji")
@Tag(name = "CustomEmoji")
public class CustomEmojiController extends AbstractLemmyApiController {

  private final AuthorizationService authorizationService;
  private final LocalInstanceContext localInstanceContext;
  private final CustomEmojiRepository customEmojiRepository;
  public final CustomEmojiService customEmojiService;
  private final ConversionService conversionService;

  @Operation(summary = "Create a new custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomEmojiResponse.class)) })
  })
  @PostMapping
  CustomEmojiResponse create(@Valid @RequestBody final CreateCustomEmoji createCustomEmojiForm, JwtPerson principal) {

    final var person = getPersonNotBannedOrThrowUnauthorized(principal);

    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    final var customEmoji = CustomEmoji.builder()
        .altText(createCustomEmojiForm.alt_text())
        .category(createCustomEmojiForm.category())
        .imageUrl(createCustomEmojiForm.image_url())
        .shortCode(createCustomEmojiForm.shortcode().toLowerCase().trim())
        .localSiteId(localInstanceContext.instance().getId())
        .build();

    var emojiEntity = customEmojiService.createCustomEmoji(customEmoji, createCustomEmojiForm.keywords());

    return CustomEmojiResponse.builder()
        .custom_emoji(conversionService.convert(emojiEntity,
            CustomEmojiView.class))
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
  CustomEmojiResponse update(@Valid @RequestBody final EditCustomEmoji editCustomEmojiForm, JwtPerson principal) {

    final var person = getPersonNotBannedOrThrowUnauthorized(principal);

    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    var emojiId = (long) editCustomEmojiForm.id();
    var customEmojiOptional = customEmojiRepository.findById(emojiId);
    if (!customEmojiOptional.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "custom_emoji_not_found");
    }
    var customEmoji = customEmojiOptional.get();

    customEmoji.setAltText(editCustomEmojiForm.alt_text());
    customEmoji.setCategory(editCustomEmojiForm.category());
    customEmoji.setImageUrl(editCustomEmojiForm.image_url());

    var emojiEntity = customEmojiService.updateCustomEmoji(customEmoji, editCustomEmojiForm.keywords());

    return CustomEmojiResponse.builder()
        .custom_emoji(conversionService.convert(emojiEntity,
            CustomEmojiView.class))
        .build();
  }

  @Operation(summary = "Delete a custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DeleteCustomEmojiResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "Custom Emoji Not Found", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)) })
  })
  @PostMapping("delete")
  DeleteCustomEmojiResponse delete(@Valid @RequestBody final DeleteCustomEmoji deleteCustomEmojiForm,
      JwtPerson principal) {

    final var person = getPersonNotBannedOrThrowUnauthorized(principal);

    authorizationService.isAdminElseThrow(person,
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not_an_admin"));

    var emojiId = (long) deleteCustomEmojiForm.id();
    var exists = customEmojiRepository.existsById(emojiId);
    if (!exists) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "custom_emoji_not_found");
    }

    customEmojiRepository.deleteById(emojiId);

    return DeleteCustomEmojiResponse.builder().success(true).build();
  }
}
