package com.sublinks.sublinksapi.api.lemmy.v3.image.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/pictrs/image")
public class ImageController {
    @PostMapping
    public ResponseEntity upload() {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity show(@PathVariable String filename) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/{token}/{filename}")
    public ResponseEntity delete(@PathVariable String token, @PathVariable String filename) {

        return ResponseEntity.ok().build();
    }
}
