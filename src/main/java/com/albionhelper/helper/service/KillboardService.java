package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class KillboardService {

    private final String EAST = "https://gameinfo-sgp.albiononline.com/api/gameinfo/";
    private final String WEST = "https://gameinfo.albiononline.com/api/gameinfo/";
    private final String EUROPE = "https://gameinfo-ams.albiononline.com/api/gameinfo/";

    // search?q=아이디
    private final String GET_ID_URL = "/search?q=";

    public List<Player> getPlayersInfo(String id, String location) throws JsonProcessingException {
        ArrayList<Player> list = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        switch (location) {
            case "east" :
                stringBuilder.append(EAST);
                break;
            case "west" :
                stringBuilder.append(WEST);
                break;
            case "europe" :
                stringBuilder.append(EUROPE);
                break;
        }

        stringBuilder.append(GET_ID_URL).append(id);

        WebClient webClient = WebClient.builder().build();
        String response = webClient.get()
                .uri(stringBuilder.toString())
                .header("x-test", "header")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode playersNode = rootNode.get("players");

        for (JsonNode node : playersNode) {
            Player p = objectMapper.treeToValue(node, Player.class);
            list.add(p);
        }

        return list;
    }




}
