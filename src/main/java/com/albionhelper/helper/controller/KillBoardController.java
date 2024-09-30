package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.KillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/killboard")
public class KillBoardController {

    // 킬보드 관련 정보.
    // https://www.tools4albion.com/api_info.php
    
    // 참고할만한 페이지
    // https://albionboard.com/
    @GetMapping("")
    public String showPage(){
        return "killboard";
    }

    @Autowired
    KillboardService killboardService;

    @GetMapping("getKillBoard")
    @ResponseBody
    public String getKillBoard(@RequestParam(value="inputId")String id, @RequestParam(value="serverLocation")String location) {

        killboardService.getPlayersInfo(id, location);

        return id + location;
    }



}
