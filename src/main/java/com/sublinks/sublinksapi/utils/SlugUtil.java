package com.sublinks.sublinksapi.utils;

import org.springframework.stereotype.Service;

@Service
public class SlugUtil {
    public String stringToSlug(String title) {
        return title
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replaceAll("\\s+", "_");
    }
}
