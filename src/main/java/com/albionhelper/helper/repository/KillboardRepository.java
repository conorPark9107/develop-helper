package com.albionhelper.helper.repository;


import com.albionhelper.helper.domain.killboard.PlayerLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KillboardRepository extends JpaRepository<PlayerLog, Long> {

    Optional<PlayerLog> findByUserId(String userId);

    @Query(
            "SELECT p FROM PlayerLog p ORDER BY p.count DESC LIMIT 10"
    )
    List<PlayerLog> findAllTop10(Pageable pageable);

    @Query(
            "SELECT p FROM PlayerLog p WHERE server = :server ORDER BY p.count DESC LIMIT 10"
    )
    List<PlayerLog> findAllTop10ByServer(Pageable pageable, @Param("server") String server);

    @Query(
            value = " SELECT * FROM " +
                    " (SELECT *, " +
                    " ROW_NUMBER() OVER (PARTITION BY server ORDER BY count desc) AS rownumber " +
                    " FROM player_log pl ) tbl " +
                    " WHERE tbl.rownumber = 1 ",
            nativeQuery = true
    )
    PlayerLog findTop1ByServer();



}
