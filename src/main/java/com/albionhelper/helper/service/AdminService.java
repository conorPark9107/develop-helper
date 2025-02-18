package com.albionhelper.helper.service;

import com.albionhelper.helper.repository.AccessLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminService {

    private final AccessLogRepository accessLogRepository;

    public AdminService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public Long getVisitCount(){
        return accessLogRepository.count();
    }

    public Long getTodayVisitCount() {
        return accessLogRepository.countTodayVisitors();
    }

    public long getVisitCountByDate(LocalDate date) {
        System.out.println(date.toString());
        return accessLogRepository.countVisitorsByDate(date);
    }
}
