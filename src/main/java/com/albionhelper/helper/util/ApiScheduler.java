package com.albionhelper.helper.util;

import com.albionhelper.helper.service.KillEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ApiScheduler {

    private final KillEventService killEventService;


    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void scheduleGetBattles(){
        long sec = Instant.now().getEpochSecond();
        String[] urls = {"https://gameinfo-sgp.albiononline.com/api/gameinfo/events?limit=51&offset=0&timestamp=" + sec,
//                         "https://gameinfo.albiononline.com/api/gameinfo/events?limit=51&offset=0&timestamp=" + sec
                "https://gameinfo-ams.albiononline.com/api/gameinfo/events?limit=51&offset=0&timestamp=" + sec};

        for (String url : urls) {
            killEventService.fetchAndSaveKillEvents(url);
        }


        System.out.println("호출됨.");
    }

}
