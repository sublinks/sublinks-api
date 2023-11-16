package com.sublinks.sublinksapi.api.lemmy.v3.image.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.image.models.PictrsParams;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/pictrs/image")
@Tag(name = "pictrs/image", description = "the image API")
public class ImageController {
    @Value("${sublinks.pictrs.url}")
    private String pictrsUri;

    @PostMapping
    Mono<ResponseEntity<String>> upload(@RequestParam("images[]") MultipartFile image) throws IOException {

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

    @GetMapping("{filename}")
    Mono<ResponseEntity<ByteArrayResource>> fullResImage(@Valid PictrsParams pictrsParams, @PathVariable String filename) {

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
                .retrieve() // Retrieve the response
                .bodyToMono(byte[].class) // Convert the response body to a byte array
                .map(bytes -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Set the appropriate content type, e.g., MediaType.IMAGE_JPEG
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpeg\"") // Set the Content-Disposition header if you want the image to be downloadable
                        .body(new ByteArrayResource(bytes))); // Wrap the byte array in a ByteArrayResource

    }

    @GetMapping("delete/{token}/{filename}")
    String delete(@PathVariable String token, @PathVariable String filename) {

        // @todo delete images
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
