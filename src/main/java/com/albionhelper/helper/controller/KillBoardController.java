package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.battle.Event;
import com.albionhelper.helper.domain.killboard.DeathBoard;
import com.albionhelper.helper.domain.killboard.KillBoard;
import com.albionhelper.helper.domain.playerinfo.PlayerInfoDetail;
import com.albionhelper.helper.service.BattlesService;
import com.albionhelper.helper.service.KillboardService;
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

import java.util.List;

@Controller
@RequestMapping(value="/killboard")
public class KillBoardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KillboardService killboardService;

    // 킬보드 관련 정보.
    // https://www.tools4albion.com/api_info.php
    
    // 참고할만한 페이지
    // https://albionboard.com/
    @GetMapping("")
    public String showPage(Model model,
                           @RequestParam(value="server", defaultValue = "east") String server){
        model.addAttribute("server", server);
        model.addAttribute("week", killboardService.getBoardBiggest(server, "week"));
        model.addAttribute("month", killboardService.getBoardBiggest(server, "month"));
        model.addAttribute("lastWeek", killboardService.getBoardBiggest(server, "lastWeek"));
        model.addAttribute("lastMonth", killboardService.getBoardBiggest(server, "lastMonth"));
        return "killboard/killboard";
    }



    @GetMapping("getUserInfo")
    @ResponseBody
    public List<Player> getUserInfo(@RequestParam(value="inputId")String id, @RequestParam(value="serverLocation")String location) throws JsonProcessingException {
        log.info("Request Users info : {}", id);
        return killboardService.getPlayersInfo(id, location);
    }

    @GetMapping("getKillBoard")
    public String getKillBoard(Model model,
                               @RequestParam(value="id")String id,
                               @RequestParam(value="location")String server) throws JsonProcessingException {

        log.info("Request getKillBoard List => id, location : {}, {}", id, server);
        PlayerInfoDetail playerInfoDetail = killboardService.getPlayerDetailInfo(id, server);
        List<KillBoard> killList = killboardService.getKillBoard(id, server);
        List<DeathBoard> deathList = killboardService.getDeathBoard(id, server);
        model.addAttribute("player", playerInfoDetail);
        model.addAttribute("pve", playerInfoDetail.getLifetimeStatistics().getPve());
        model.addAttribute("gather", playerInfoDetail.getLifetimeStatistics().getGathering());
        model.addAttribute("craft", playerInfoDetail.getLifetimeStatistics().getCrafting());

        double result = (double) playerInfoDetail.getKillFame() / playerInfoDetail.getDeathFame();
        model.addAttribute("kda", Math.round(result * 100) / 100.0);
        model.addAttribute("kills", killList);
        model.addAttribute("deaths", deathList);
        model.addAttribute("location", server);
        log.info("Response list size => killList, deathList : {}, {}", killList.size(), deathList.size());
        return "killboard/killboardList";
    }

    @GetMapping("detail")
    public String getDetail(Model model,
                               @RequestParam(value="killerId")String killerId,
                               @RequestParam(value="victimId")String victimId,
                               @RequestParam(value="location")String server) throws JsonProcessingException {
        log.info("Request getDetail => killerId, victimId, location : {}, {}, {}", killerId, victimId, server);
        Event event = killboardService.getDetail(server, killerId, victimId);
        model.addAttribute("event", event);
        model.addAttribute("server", server);

        return "killboard/killboardDetail";
    }

}
