package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.killboard.PlayerLogDTO;
import com.albionhelper.helper.domain.market.ItemPrice;
import com.albionhelper.helper.domain.market.MarketRank;
import com.albionhelper.helper.domain.market.MarketRankDTO;
import com.albionhelper.helper.service.MarketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/market")
public class MarketController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // api 정보.
    // https://www.albion-online-data.com/

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("")
    public String market(Model model){
        model.addAttribute("rank", marketService.getCounts());
        return "market/market";
    }

    @PostMapping("/getPrice")
    @ResponseBody
    public List<ItemPrice> showPricePost(@RequestParam("server")String server,
                                     @ModelAttribute MarketRankDTO dto) throws JsonProcessingException {
        marketService.addCount(dto);
        return marketService.getPrice(server, dto.getItemId());
    }

//    @GetMapping("/getPrice")
//    @ResponseBody
//    public List<ItemPrice> showPriceGet(@RequestParam("server")String server,
//                                     @ModelAttribute MarketRankDTO dto) throws JsonProcessingException {
//        marketService.addCount(dto);
//        return marketService.getPrice(server, dto.getItemId());
//    }

    @GetMapping("/getPriceOld")
    @ResponseBody
    public List<ItemPrice> showPriceOld(@RequestParam("server")String server,
                                     @RequestParam("quality")String quality,
                                     @RequestParam("tier")String tier,
                                     @RequestParam("dotTier")String dotTier,
                                     @RequestParam("itemName")String itemName) throws JsonProcessingException {
        return marketService.getPriceOld(server, quality, tier, dotTier, itemName);
    }

    @GetMapping("getRank")
    @ResponseBody
    public List<MarketRankDTO> getRank(){
        return marketService.getCounts();
    }




}
