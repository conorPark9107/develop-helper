package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battleapi.KillEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KillEventRepository extends JpaRepository<KillEvent, Long> {
    boolean existsByEventId(Long eventId);
}
