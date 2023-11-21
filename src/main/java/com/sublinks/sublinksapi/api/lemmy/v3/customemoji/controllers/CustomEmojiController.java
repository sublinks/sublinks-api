package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CreateCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.DeleteCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.EditCustomEmoji;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v3/custom_emoji")
@Tag(name = "CustomEmoji")
public class CustomEmojiController extends AbstractLemmyApiController {

  @Operation(summary = "Create a new custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CustomEmojiResponse.class))})
  })
  @PostMapping
  CustomEmojiResponse create(@Valid final CreateCustomEmoji createCustomEmojiForm) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Edit a custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CustomEmojiResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Custom Emoji Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PutMapping
  CustomEmojiResponse update(@Valid final EditCustomEmoji editCustomEmojiForm) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Delete a custom emoji.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CustomEmojiResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Custom Emoji Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("delete")
  CustomEmojiResponse delete(@Valid final DeleteCustomEmoji deleteCustomEmojiForm) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
