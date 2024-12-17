package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.GuildDTO;
import com.albionhelper.helper.domain.battle.*;
import com.albionhelper.helper.service.BattlesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/battle")
public class BattlesController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BattlesService battlesService;

    @GetMapping("")
    public String showPage(Model model
                            , @RequestParam(name = "inputValue", defaultValue = "") String inputValue
                            ,@RequestParam(name = "server", defaultValue = "east") String url
                            ,@RequestParam(name = "offset", defaultValue = "0") int offset
                            ,@RequestParam(name = "limit", defaultValue = "10") int limit) throws JsonProcessingException {
        List<Battle> list = battlesService.getBattleList(url, offset, limit, inputValue);
        model.addAttribute("size", list.size());
        model.addAttribute("list", list);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        return "battle/battleboard";
    }

    @GetMapping("/more")
    @ResponseBody
    public List<Battle> showPageForAjax(
            @RequestParam(name = "inputValue", defaultValue = "") String inputValue
            ,@RequestParam(name = "server", defaultValue = "east") String url
            ,@RequestParam(name = "offset", defaultValue = "0") int offset
            ,@RequestParam(name = "limit", defaultValue = "10") int limit) throws JsonProcessingException {

        return battlesService.getBattleList(url, offset, limit, inputValue);
    }

    @GetMapping("/detail")
    public String getBattles(Model model,
                             @RequestParam(name = "id")String id,
                             @RequestParam(name = "server")String server,
                             @RequestParam(name = "kill")int kill) throws JsonProcessingException {
        Battle battle = battlesService.getBattleListById(id, server);
        battle = battlesService.getPlayers(battle);
        List<Event> eventList = battlesService.getEventList(id, server, kill);

        // 모스트 킬
        Player p = battlesService.getMostKillingPlayer(battle);

        // 플레이어 리스트
        Map<String, EventPlayer> playerList = battlesService.getPlayerList(eventList, battle);


        // [모스트 데미지, 모스트 힐, 빅도네이션]
        EventPlayer[] players = battlesService.getMostDpsAndHeal(playerList);

        model.addAttribute("playerList", playerList.values());
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

    @GetMapping("/getGuildId")
    @ResponseBody
    public List<GuildDTO> getGuildId(@RequestParam(name = "id", defaultValue = "") String id
                            , @RequestParam(name = "server", defaultValue = "east") String server) throws JsonProcessingException {
        return battlesService.getGuildId(id, server);
    }


}
