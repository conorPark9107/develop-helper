package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String addcategory(){
        return "admin/addcategory";
    }

    @GetMapping("/admin/replyinquire")
    public String replyinquire(Model model){
        model.addAttribute("list", boardService.findAllInquire());
        return "admin/replyinquire";
    }


}
