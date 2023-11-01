package com.sublinks.sublinksapi.api.lemmy.v3.post.utils;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Url {
    public String normalizeUrl(final String providedUrl) throws MalformedURLException {
        final URL url = new URL(providedUrl);

        try {
            final String normalizedQueryString = removeTrackingParameters(url.getQuery());
            // @Todo verify protocol is http, https, magnet
            final URI uri = new URI(url.getProtocol(), url.getAuthority(), url.getPath(), normalizedQueryString, url.getRef());
            return uri.toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String removeTrackingParameters(final String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return "";
        }
        Pattern pattern = Pattern.compile("(\\w+)=?([^&]+)?");
        Matcher matcher = pattern.matcher(queryString);
        Collection<String> spyList = List.of("utm_source", "utm_medium", "utm_campaign", "utm_term", "utm_content", "gclid", "gclsrc", "dclid", "fbclid");
        HashMap<String, String> parameters = new HashMap<>();
        while ((matcher.find())) {
            if (!spyList.contains(matcher.group(1))) {
                parameters.put(matcher.group(1), matcher.group(2));
            }
        }
        if (parameters.isEmpty()) {
            return "";
        }
        return parameters.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }
}
