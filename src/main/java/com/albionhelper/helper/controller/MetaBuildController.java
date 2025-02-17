package com.albionhelper.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MetaBuildController {

    @GetMapping("/metabuild")
    public String showPage(){
        return "metabuild/metabuild";
    }

}
