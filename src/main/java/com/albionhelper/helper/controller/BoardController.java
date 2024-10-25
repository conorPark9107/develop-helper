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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String register(@RequestPart(value = "images", required = false)List<MultipartFile> images) throws IOException {




        // 이 파일들을 스토리지로 보낸 후 유니크한 파일명을 통해서 다시 바디에 써준다.
        // https://supabase.com/docs/reference/javascript/storage-from-upload
        return "nice";
    }


}
