package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.BoardService;
import com.albionhelper.helper.service.GoldService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


// 이미지 관련
// https://wiki.albiononline.com/wiki/API:Render_service
// https://render.albiononline.com/v1/item/T4_HEAD_LEATHER_FEY@1.png?quality=4


// 데이터 관련
// https://www.albion-online-data.com/#i-want-all-the-data-from-the-api

// api document
// https://openalbion.com/categories.html

// 참고
// https://albion-profit-calculator.com/

@Controller
public class MainController {

    private final GoldService goldService;
    private final BoardService boardService;

    public MainController(GoldService goldService, BoardService boardService) {
        this.goldService = goldService;
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String index(Model model){
        return "home";
    }

    @GetMapping("/gold")
    public String getGoldPricies(Model model) throws JsonProcessingException {
        model.addAttribute("east", goldService.getGoldPriciesOnEast());
        model.addAttribute("west", goldService.getGoldPriciesOnWest());
        model.addAttribute("europe", goldService.getGoldPriciesOnEurope());
        return "gold/gold";
    }

    @GetMapping("/toinquire")
    public String showToInquirePage(Model model){
        model.addAttribute("list", boardService.findAllInquire());
        return "toinquire";
    }

    @PostMapping("/inquire/register")
    @ResponseBody
    public String registerInquire(@RequestParam("text")String text){
        return boardService.registerInquire(text);
    }

    @GetMapping("/info")
    public String showInfoPage(){
        return "info";
    }

    /* robots.txt 처리 */
    @RequestMapping(value = "/robots.txt")
    public String robots() {
        return "robots.txt";
    }

    /* sitemap.txt 처리 */
    @RequestMapping(value = "/sitemap.txt")
    public String sitemap() {
        return "sitemap.xml";
    }

    /* sitemap.txt 처리 */
    @RequestMapping(value = "/sitemap.xml")
    public String sitemapXml() {
        return "sitemap.xml";
    }

}
