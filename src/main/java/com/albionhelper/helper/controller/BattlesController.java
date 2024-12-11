package com.albionhelper.helper.controller;

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

@Controller
@RequestMapping("/battle")
public class BattlesController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BattlesService battlesService;

    @GetMapping("")
    public String showPage(Model model, @RequestParam(name = "server", defaultValue = "east") String url) throws JsonProcessingException {
        model.addAttribute("list", battlesService.getBattleList(url));
        return "battle/battleboard";
    }

    @GetMapping("getBattles")
    public String getBattles(){
        return "battle/battleboardDetail";
    }


}
