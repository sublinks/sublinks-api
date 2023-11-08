package com.sublinks.sublinksapi.pictrs.models;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PictrsSettings {
    @Value("${sublinks.pictrs.url}")
    String pictrsUrl;
}
