package com.albionhelper.helper.service;

import com.albionhelper.helper.repository.AccessLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return accessLogRepository.countVisitorsByDate(date);
    }

    public List<Map<String, Object>> getVisitCountList(LocalDate after) {
        LocalDate before = after.minusDays(10);
        List<Object[]> results = accessLogRepository.countAccessByDate(LocalDateTime.of(before, LocalTime.of(0, 0, 0)));
        return results.stream().map(obj ->
                Map.of(
                        "date", obj[0],
                        "count", obj[1]
                ))
                .collect(Collectors.toList());
    }
}
