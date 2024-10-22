package com.albionhelper.helper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String board(){
        return "board/board";
    }

    @GetMapping("/write")
    public String boardWrite(){
        return "board/write";
    }


}
