package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardService boardService;

    @GetMapping("")
    public String board(){
        return "board/board";
    }

    @GetMapping("/write")
    public String boardWrite(){
        return "board/write";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam("title")String title,
                           @RequestParam("password")String password,
                           @RequestParam("category")String category,
                           @RequestParam("contents")String contents){



        log.info("title : {}", title);
        log.info("PW : {}", password);
        log.info("category : {}", category);
        log.info("contents : {}", contents);

        return "redirect:/board";
    }


}
