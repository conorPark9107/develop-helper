package com.albionhelper.helper.repository;


import com.albionhelper.helper.domain.killboard.PlayerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KillboardRepository extends JpaRepository<PlayerLog, Long> {



}
