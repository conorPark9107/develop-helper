package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.board.UpdateInquireDTO;
import com.albionhelper.helper.service.AdminService;
import com.albionhelper.helper.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final BoardService boardService;
    private final AdminService adminService;

    public AdminController(BoardService boardService, AdminService adminService) {
        this.boardService = boardService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model){
        model.addAttribute("total", adminService.getVisitCount());
        model.addAttribute("today", adminService.getTodayVisitCount());
        return "admin/dashboard";
    }

    @GetMapping("/admin/dashboard/getVisitList")
    @ResponseBody
    public List<Map<String, Object>> getVisitList(@RequestParam(name = "date") LocalDate date){
        return adminService.getVisitCountList(date);
    }

    @GetMapping("/admin/dashboard/pickDate")
    @ResponseBody
    public long getPerDate(@RequestParam(name = "date") LocalDate date){
        return adminService.getVisitCountByDate(date);
    }


    @GetMapping("/admin/updatecategory")
    public String addCategory(Model model){
        model.addAttribute("categoryList", boardService.findAllCategory());
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

    @PostMapping("/admin/category/delete")
    public String addCategory(@RequestParam(name = "id")long id, Model model){
        boardService.deleteCategory(id);
        model.addAttribute("categoryList", boardService.findAllCategory());
        return "admin/addcategory";
    }

    @PostMapping("/admin/category/insert")
    public String insertCategory(@RequestParam(name = "category")String category, Model model){
        boardService.registerCategory(category);
        model.addAttribute("categoryList", boardService.findAllCategory());
        return "admin/addcategory";
    }

}
