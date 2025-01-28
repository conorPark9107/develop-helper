package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.board.UpdateInquireDTO;
import com.albionhelper.helper.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final BoardService boardService;

    public AdminController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(){
        return "admin/dashboard";
    }

    @GetMapping("/admin/addcategory")
    public String addCategory(){
        return "admin/addcategory";
    }

    @GetMapping("/admin/replyinquire")
    public String replyInquire(Model model){
        model.addAttribute("list", boardService.findAllInquire());
        return "admin/replyinquire";
    }

    @PostMapping("/admin/updateinquire")
    public String updateInquire(@ModelAttribute UpdateInquireDTO dto, Model model){
        boardService.updateInquire(dto);
        model.addAttribute("list", boardService.findAllInquire());
        return "admin/replyinquire";
    }

    @PostMapping("/admin/deleteinquire")
    public String deleteInquire(@ModelAttribute UpdateInquireDTO dto, Model model){
        boardService.deleteInquire(dto.getId());
        model.addAttribute("list", boardService.findAllInquire());
        return "admin/replyinquire";
    }

}
