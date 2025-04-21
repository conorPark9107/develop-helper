package com.albionhelper.helper.repository;


import com.albionhelper.helper.domain.killboard.PlayerLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KillboardRepository extends JpaRepository<PlayerLog, Long> {

    Optional<PlayerLog> findByUserId(String userId);

    @Query(
            "SELECT p FROM PlayerLog p ORDER BY p.count DESC LIMIT 10"
    )
    List<PlayerLog> findAllTop10(Pageable pageable);
}
