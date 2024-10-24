package com.albionhelper.helper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String boardWriteRegister(@RequestParam("title")String title,
                                     @RequestParam("password")String pw,
                                     @RequestPart(value = "images", required = false)List<MultipartFile> images){
        log.info("title : {}", title);
        log.info("pw : {} ", pw);
        log.info(images.get(0).getOriginalFilename());
        log.info(images.get(1).getOriginalFilename());

        // 이 파일들을 스토리지로 보낸 후 유니크한 파일명을 통해서 다시 바디에 써준다.

        return "nice";
    }


}
