package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.metaBuild.DeleteCommentDTO;
import com.albionhelper.helper.domain.metaBuild.TierListCommentDTO;
import com.albionhelper.helper.domain.metaBuild.TierListDTO;
import com.albionhelper.helper.domain.metaBuild.TierListUpRequestDTO;
import com.albionhelper.helper.service.MetaBuildService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MetaBuildController {

    private final MetaBuildService metaBuildService;

    public MetaBuildController(MetaBuildService metaBuildService) {
        this.metaBuildService = metaBuildService;
    }

    @GetMapping("/metabuild")
    public String showPage(){
        return "metabuild/metabuild";
    }

    @GetMapping("/tierList")
    public String showTierListPage(@RequestParam(name = "page", defaultValue = "0", required = false)int page,
                                   @RequestParam(name = "size", defaultValue = "10", required = false)int size,
                                   @RequestParam(name = "category", defaultValue = "전체", required = false) String category,
                                   @RequestParam(name = "options", defaultValue = "writeDate", required = false) String sortBy,
                                   Model model){
        Page<TierListDTO> pageObj = metaBuildService.getTierList(category, sortBy, page, size);

        int totalPage = pageObj.getTotalPages();
        int nowPage = pageObj.getNumber();
        int pageLimit = 5; // 페이징 처리시 페이징 개수를 지정.
        int startPage = (nowPage / pageLimit) * pageLimit;
        int endPage = Math.min(startPage + pageLimit, totalPage) - 1;
        endPage = endPage == -1? 0 : endPage;

        model.addAttribute("list", pageObj.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage - 1);
        model.addAttribute("category", category);
        model.addAttribute("options", sortBy);

        return "metabuild/tierList";
    }

    @GetMapping("/tierList/write")
    public String showTierListWritePage(){
        return "metabuild/tierListWrite";
    }

    @PostMapping("/tierList/write")
    @ResponseBody
    public String showTierListWrite(@RequestBody TierListDTO dto){
        return metaBuildService.register(dto);
    }


    @PostMapping("/tierList/detail/delete")
    @ResponseBody
    public String deleteComment(@RequestBody DeleteCommentDTO dto){
        return metaBuildService.deleteComment(dto);
    }

    @GetMapping("/tierList/detail")
    public String showTierListDetail(@RequestParam(name = "id") Long id, Model model){
        model.addAttribute("tierList", metaBuildService.getTierListById(id));
        return "metabuild/tierListDetail";
    }

    @PostMapping("/tierList/detail")
    @ResponseBody
    public String showTierListDetail(@RequestBody TierListCommentDTO dto){
        return metaBuildService.registerComment(dto);
    }

    @PostMapping("/tierList/detail/up")
    @ResponseBody
    public String showTierListDetail(@RequestBody TierListUpRequestDTO dto){
        return metaBuildService.modifyUp(dto.getId());
    }


}
