package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KillboardService {

    public ArrayList<Player> getPlayersInfo(String id, String location){
        ArrayList<Player> list = new ArrayList<>();


        //        WebClient webClient = WebClient.builder().build();
//        String responseData = webClient.get().uri(TEST_URL).header("x-test", "header").retrieve().bodyToMono(String.class).block();
//        model.addAttribute("text", responseData);

        return list;
    }




}
