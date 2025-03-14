package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.metaBuild.TierList;
import com.albionhelper.helper.domain.metaBuild.TierListDTO;
import com.albionhelper.helper.service.MetaBuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String showTierListPage(){
        return "metabuild/tierList";
    }

    @GetMapping("/tierList/write")
    public String showTierListWritePage(Model model){
        List<TierList> list = metaBuildService.getTierList();
        return "metabuild/tierListWrite";
    }

    @PostMapping("/tierList/write")
    @ResponseBody
    public String showTierListWrite(@RequestBody TierListDTO dto){
        String s = metaBuildService.register(dto);
        return s;
    }

}
