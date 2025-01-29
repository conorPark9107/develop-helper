package com.albionhelper.helper.util;

import com.albionhelper.helper.service.BattlesService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApiScheduler {

    private BattlesService battlesService;

    public ApiScheduler(BattlesService battlesService) {
        this.battlesService = battlesService;
    }

//    @Scheduled(fixedRate = 60000) // 60초마다 실행
//    public void scheduleGetBattles(){
//        System.out.println("test");
//    }

}
