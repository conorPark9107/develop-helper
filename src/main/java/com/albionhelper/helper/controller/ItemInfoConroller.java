package com.albionhelper.helper.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemInfoConroller {

    // 아이템 정보 요청 url
    // https://gameinfo.albiononline.com/api/gameinfo/items/아이템명/data

    @GetMapping("")
    public String showPage(){
        return "item/item";
    }

    @GetMapping("${itemName}")
    @ResponseBody
    public String getItemInfo(@PathVariable String itemName){
        return "";
    }
}
