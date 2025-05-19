package com.albionhelper.helper.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ItemService {

    private final WebClient webClient;

    // 장비 아이템의 경우 E스킬(세번째 스킬의 스킬명과 스킬 설명으로 주어짐)
    // 음식의 경우 : buffOverTime 리스트 필드 아래에 value값이 존재하는데 이게 버프의 값임.

    public ItemService(WebClient webClient) {
        this.webClient = webClient;
    }




}
