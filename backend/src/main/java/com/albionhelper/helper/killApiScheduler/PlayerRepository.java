package com.albionhelper.helper.killApiScheduler;

import com.albionhelper.helper.domain.battleapi.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
