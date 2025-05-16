package com.albionhelper.helper.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ItemService {

    private final WebClient webClient;

    public ItemService(WebClient webClient) {
        this.webClient = webClient;
    }


}
