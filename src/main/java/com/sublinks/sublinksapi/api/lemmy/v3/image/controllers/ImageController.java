package com.sublinks.sublinksapi.api.lemmy.v3.image.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/pictrs/image")
public class ImageController {
    @PostMapping
    String upload(@RequestParam("images[]") MultipartFile images) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("{filename}")
    String fullResImage(@PathVariable String filename) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("delete/{token}/{filename}")
    String delete(@PathVariable String token, @PathVariable String filename) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
