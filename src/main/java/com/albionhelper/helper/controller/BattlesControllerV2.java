package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.GuildDTO;
import com.albionhelper.helper.domain.battle.*;
import com.albionhelper.helper.service.BattlesServiceV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/battle/v2")
public class BattlesControllerV2 {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BattlesServiceV2 battlesServiceV2;

    public BattlesControllerV2(BattlesServiceV2 battlesServiceV2) {
        this.battlesServiceV2 = battlesServiceV2;
    }

    @GetMapping("")
    public String showPage(Model model
                            , @RequestParam(name = "inputValue", defaultValue = "") String inputValue
                            ,@RequestParam(name = "server", defaultValue = "east") String url
                            ,@RequestParam(name = "offset", defaultValue = "0") int offset
                            ,@RequestParam(name = "limit", defaultValue = "20") int limit) throws JsonProcessingException {
        model.addAttribute("count", battlesServiceV2.getCount(url));
        List<Battle> list = battlesServiceV2.getBattleList(url, offset, limit, inputValue);
        model.addAttribute("size", list.size());
        model.addAttribute("list", list);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        model.addAttribute("server", url);
        return "battle/battleboard";
    }

    @GetMapping("/count")
    @ResponseBody
    public List<BattleCountLogDTO> getCount(@RequestParam(name = "server", defaultValue = "") String server){
        return battlesServiceV2.getCount(server);
    }

    @GetMapping("/refresh")
    @ResponseBody
    public List<Battle> refreshList(
            @RequestParam(name = "inputValue", defaultValue = "") String inputValue
            ,@RequestParam(name = "server", defaultValue = "east") String url
            ,@RequestParam(name = "recentId", defaultValue = "0") Long recentId) throws JsonProcessingException {
        return battlesServiceV2.getBattleListForRefresh(url, inputValue, recentId);
    }

    @PostMapping("/more")
    @ResponseBody
    public List<Battle> showPageForAjax(
            @RequestParam(name = "inputValue", defaultValue = "") String inputValue
            ,@RequestParam(name = "server", defaultValue = "east") String url
            ,@RequestParam(name = "guildName", defaultValue = "") String guildName
            ,@RequestParam(name = "offset", defaultValue = "0") int offset
            ,@RequestParam(name = "limit", defaultValue = "10") int limit) throws JsonProcessingException {
        if(!guildName.isEmpty()){
            battlesServiceV2.addGuildCount(inputValue, url, guildName);
        }
        return battlesServiceV2.getBattleList(url, offset, limit, inputValue);
    }

    @GetMapping("/detail")
    public String getBattles(Model model,
                             @RequestParam(name = "id")String id,
                             @RequestParam(name = "server")String server) throws JsonProcessingException {

        // 특정 레코드 식별 가능한 id값을 통해서 특정 전투 정보를 가져옴.
        Battle battle = battlesServiceV2.getBattleListById(id, server);

        // 플레이어 인원을 길드와 연합에 채워넣는 메서드.
        battle = battlesServiceV2.getPlayers(battle);

        // 특정 전투의 이벤트(킬 로그)들을 전부 종합해서 가져오는 메서드.
        List<Event> eventList = battlesServiceV2.getEventList(id, server, battle.getTotalKills());

        // 모스트 킬.
        Player p = battlesServiceV2.getMostKillingPlayer(battle);

        // 플레이어 리스트.
        Map<String, EventPlayer> playerList = battlesServiceV2.getPlayerList(eventList, battle);

        // 길드별, 연합별 평균 ip구하기.
        battle = battlesServiceV2.getAverageIp(battle, playerList);

        // [모스트 데미지, 모스트 힐, 빅도네이션]
        EventPlayer[] players = battlesServiceV2.getMostDpsAndHeal(playerList);

        model.addAttribute("playerList", battlesServiceV2.sortAndConvertMapToList(playerList));
        model.addAttribute("mostKiller", p);
        model.addAttribute("mostDps", players[0]);
        model.addAttribute("mostHeal", players[1]);
        model.addAttribute("mostDonation", players[2]);
        model.addAttribute("eventList", eventList);
        model.addAttribute("battle", battle);
        model.addAttribute("guilds", battle.getGuilds());
        model.addAttribute("alliances", battle.getAlliances());
        return "battle/battleboardDetail";
    }

//    @GetMapping("/detail")
//    public String getBattles(Model model,
//                             @RequestParam(name = "id")String id,
//                             @RequestParam(name = "server")String server) throws JsonProcessingException {
//
//        // 특정 레코드 식별 가능한 id값을 통해서 특정 전투 정보를 가져옴.
//        Battle battle = battlesService.getBattleListById(id, server);
//
//        // 플레이어 인원을 길드와 연합에 채워넣는 메서드.
//        battle = battlesService.getPlayers(battle);
//
//        // 특정 전투의 이벤트(킬 로그)들을 전부 종합해서 가져오는 메서드.
//        List<Event> eventList = battlesService.getEventList(id, server, battle.getTotalKills());
//
//        // 모스트 킬
//        Player p = battlesService.getMostKillingPlayer(battle);
//
//        // 플레이어 리스트
//        Map<String, EventPlayer> playerList = battlesService.getPlayerList(eventList, battle);
//
//        // 길드별, 연합별 평균 ip구하기.
//        battle = battlesService.getAverageIp(battle, playerList);
//
//        // [모스트 데미지, 모스트 힐, 빅도네이션]
//        EventPlayer[] players = battlesService.getMostDpsAndHeal(playerList);
//
//        model.addAttribute("playerList", playerList.values());
//        model.addAttribute("mostKiller", p);
//        model.addAttribute("mostDps", players[0]);
//        model.addAttribute("mostHeal", players[1]);
//        model.addAttribute("mostDonation", players[2]);
//        model.addAttribute("eventList", eventList);
//        model.addAttribute("battle", battle);
//        model.addAttribute("guilds", battle.getGuilds());
//        model.addAttribute("alliances", battle.getAlliances());
//        return "battle/battleboardDetail";
//    }


    @GetMapping("/getGuildId")
    @ResponseBody
    public List<GuildDTO> getGuildId(@RequestParam(name = "id", defaultValue = "") String id
                            , @RequestParam(name = "server", defaultValue = "east") String server) throws JsonProcessingException {
        return battlesServiceV2.getGuildId(id, server);
    }


}
