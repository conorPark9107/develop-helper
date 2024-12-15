package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.GuildDTO;
import com.albionhelper.helper.domain.battle.Alliance;
import com.albionhelper.helper.domain.battle.Battle;
import com.albionhelper.helper.domain.battle.Event;
import com.albionhelper.helper.domain.battle.Guild;
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
                             @RequestParam(name = "server")String server) throws JsonProcessingException {
        Battle battle = battlesService.getBattleListById(id, server);
        battle = battlesService.getPlayers(battle);
        List<Event> eventList = battlesService.getPlayerList(id, server);


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
