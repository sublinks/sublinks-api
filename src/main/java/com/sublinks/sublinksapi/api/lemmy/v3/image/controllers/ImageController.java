package com.sublinks.sublinksapi.api.lemmy.v3.image.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.image.models.PictrsParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/pictrs/image")
public class ImageController extends AbstractLemmyApiController {

  @Value("${sublinks.pictrs.url}")
  private String pictrsUri;

  @Operation(summary = "Uploads an image.", hidden = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK")}
  )
  @PostMapping
  Mono<ResponseEntity<String>> upload(@RequestParam("images[]") MultipartFile image)
      throws IOException {

    // @todo log who is uploading and what they uploaded

    Resource resource = new ByteArrayResource(image.getBytes()) {
      @Override
      public String getFilename() {

        return image.getOriginalFilename();
      }
    };

    WebClient webClient = WebClient.builder().baseUrl(pictrsUri).build();

    return webClient.post().uri("/image")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData("images[]", resource))
        .retrieve()
        .toEntity(String.class);
  }

  @Operation(summary = "Gets the full resolution image.", hidden = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK")}
  )
  @GetMapping("{filename}")
  Mono<ResponseEntity<ByteArrayResource>> fullResImage(@Valid PictrsParams pictrsParams,
      @PathVariable String filename) {

    WebClient webClient = WebClient.builder().baseUrl(pictrsUri).build();

    String url;
    if (pictrsParams.format() == null && pictrsParams.thumbnail() == null) {
      url = "image/original/" + filename;
    } else {
      String format = pictrsParams.format() == null ? "jpg" : pictrsParams.format();
      url = "image/process.%s?src=%s".formatted(format, filename);
      if (pictrsParams.thumbnail() != null) {
        url += "&thumbnail=%d".formatted(pictrsParams.thumbnail());
      }
    }

    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(byte[].class)
        .map(bytes -> ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline;")
            .body(new ByteArrayResource(bytes)));

  }

  @Operation(summary = "Deletes an image.", hidden = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK")}
  )
  @GetMapping("delete/{token}/{filename}")
  String delete(@PathVariable String token, @PathVariable String filename) {

    // @todo delete images
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
