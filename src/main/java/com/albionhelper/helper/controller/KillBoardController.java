package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.killboard.DeathBoard;
import com.albionhelper.helper.domain.killboard.KillBoard;
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

    // 킬보드 관련 정보.
    // https://www.tools4albion.com/api_info.php
    
    // 참고할만한 페이지
    // https://albionboard.com/
    @GetMapping("")
    public String showPage(){
        return "killboard/killboard";
    }

    @Autowired
    KillboardService killboardService;

    @GetMapping("getUserInfo")
    @ResponseBody
    public List<Player> getUserInfo(@RequestParam(value="inputId")String id, @RequestParam(value="serverLocation")String location) throws JsonProcessingException {
        return killboardService.getPlayersInfo(id, location);
    }

    @GetMapping("getKillBoard")
    public String getKillBoard(Model model,
                               @RequestParam(value="id")String id,
                               @RequestParam(value="location")String location) throws JsonProcessingException {

        log.info("id, location : {} {}", id, location);
        List<KillBoard> killList = killboardService.getKillBoard(id, location);
        List<DeathBoard> deathList = killboardService.getDeathBoard(id, location);
        model.addAttribute("kills", killList);
        model.addAttribute("deaths", deathList);
        log.info("killList, deathList : {} {}", killList.size(), deathList.size());
        return "killboard/killboardDetail";
    }

}
