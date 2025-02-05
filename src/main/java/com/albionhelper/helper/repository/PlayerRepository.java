package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battleapi.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
