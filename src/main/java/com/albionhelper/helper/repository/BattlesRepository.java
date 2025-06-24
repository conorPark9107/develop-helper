package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battle.BattleCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BattlesRepository extends JpaRepository<BattleCountLog, Long> {

    Optional<BattleCountLog> findByServerAndGuildId(String server, String guildId);

    @Query(
        "SELECT b FROM BattleCountLog b WHERE b.server = :server ORDER BY b.count DESC LIMIT 5"
    )
    List<BattleCountLog> findAllByTop5AndServer(@Param("server") String server);

    @Query(
            value = " SELECT * FROM " +
                    " (SELECT *, " +
                    " ROW_NUMBER() OVER (PARTITION BY server ORDER BY count desc) AS rownumber " +
                    " FROM battle_count_log ) tbl " +
                    " WHERE tbl.rownumber = 1 ",
            nativeQuery = true
    )
    List<BattleCountLog> findTop1ByServer();

}
