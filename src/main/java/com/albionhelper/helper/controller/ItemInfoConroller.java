package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemInfoConroller {

    // 아이템 정보 요청 url
    // https://gameinfo.albiononline.com/api/gameinfo/items/아이템명/data

    private final ItemService itemService;

    public ItemInfoConroller(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public String showPage(){
        return "item/item";
    }

    




}
