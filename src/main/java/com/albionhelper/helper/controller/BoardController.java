package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.board.*;
import com.albionhelper.helper.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/board")
public class BoardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardService boardService;

    @GetMapping("")
    public String getBoard(@PageableDefault(page = 0, size = 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){

        Page<BoardResponseDTO> boardList = boardService.findAllByIdAsc(pageable);

        log.info("pageNumber() : {}", pageable.getPageNumber() + 1);
        log.info("getTotalPages() : {}", boardList.getTotalPages());

        int range = 10; // 기본적으로 보여줄 페이지 index 개수.
        int firstPage = 0; // 첫 페이지.
        int pickPage = pageable.getPageNumber(); // 현재 선택된 페이지.
        int lastPage = boardList.getTotalPages(); // 마지막페이지.
        int startPage = (pickPage / range) * range; // 시작점.
        int endPage = Math.min((startPage + range), lastPage); // 종료점.

        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pickPage", pickPage);
        model.addAttribute("startPageNum", startPage);
        model.addAttribute("endPageNum", endPage);

        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("list", boardList);
        return "board/board";
    }

    @GetMapping("/write")
    public String boardWrite(){
        return "board/write";
    }

   /* @GetMapping("/modify")
    public String boardModify(@RequestParam("id") Long id, Model model) {
        BoardResponseDTO board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "board/write";
    }*/

    @PostMapping("/delete")
    public String boardDelete(@RequestParam("id")Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }

    @PostMapping(value = "/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestParam("id")Long id, @RequestParam("password")String password){
        return boardService.checkPassword(id, password);
    }

    @PostMapping(value = "/register")
    public String register(final BoardRequestDTO dto) throws UnknownHostException {
        log.info("register board => {}", dto.toString());
        boardService.registerBoard(dto);
        return "redirect:/board";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam("id")Long id,
                             Model model){
        log.info("detailPage id : {}", id);
        boardService.modifyViewCount(id);
        BoardResponseDTO board = boardService.findBoardById(id);
        List<CommentResponseDTO> comments = boardService.findAllById(id);


        log.info("detailPage comments.size : {}", comments.size());


        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        return "board/detail";
    }

    @PostMapping(value = "/registerComment")
    public String registerComment(final CommentRequestDTO dto, RedirectAttributes model) throws UnknownHostException {

        log.info("board_id : {}", dto.getBoard_id());
        log.info("nickname : {}", dto.getNickname());
        log.info("password : {}", dto.getPassword());
        log.info("comment : {}", dto.getComment());

        boardService.registerComment(dto);

        model.addAttribute("id", dto.getBoard_id());
        return "redirect:/board/detail";
    }

    @GetMapping(value="/updown")
    @ResponseBody
    public int getupdown(@RequestParam("id")Long id, @RequestParam("status")String status){
        return boardService.modifyUpdownCount(id, status);
    }

    @PostMapping(value="/deleteComment")
    @ResponseBody
    public String deleteComment(@RequestParam("id")Long id){
        return boardService.deleteComment(id);
    }

}
