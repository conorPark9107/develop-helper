package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battle.BattleCountLog;
import com.albionhelper.helper.domain.battle.BattleCountLogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BattlesRepository extends JpaRepository<BattleCountLog, Long> {

    Optional<BattleCountLog> findByServerAndGuildId(String server, String guildId);

    @Query(
        "SELECT b FROM BattleCountLog b WHERE b.server = :server ORDER BY b.count DESC LIMIT 10"
    )
    List<BattleCountLog> findAllByTop10AndServer(@Param("server") String server);
}
