package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.log.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    Optional<AccessLog> findByIpAddress(String ipAddress);

    // 오늘 방문한 방문자 수 조회
    @Query("SELECT COUNT(v) FROM AccessLog v WHERE DATE(v.accessTime) = CURRENT_DATE")
    long countTodayVisitors();

    // 특정 날짜의 방문자 수 조회
    @Query("SELECT COUNT(v) FROM AccessLog v WHERE DATE(v.accessTime) = :date")
    long countVisitorsByDate(@Param("date") LocalDate date);

}
