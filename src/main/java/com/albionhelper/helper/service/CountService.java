package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.battle.BattleCountLog;
import com.albionhelper.helper.domain.battle.BattleCountLogDTO;
import com.albionhelper.helper.domain.killboard.PlayerLog;
import com.albionhelper.helper.domain.killboard.PlayerLogDTO;
import com.albionhelper.helper.repository.BattlesRepository;
import com.albionhelper.helper.repository.KillboardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountService {

    private final KillboardRepository killboardRepository;
    private final BattlesRepository battlesRepository;

    public CountService(KillboardRepository killboardRepository, BattlesRepository battlesRepository) {
        this.killboardRepository = killboardRepository;
        this.battlesRepository = battlesRepository;
    }

    public BattleCountLogDTO getBattleCount(){

        return null;
    }

    public PlayerLogDTO getPlayerCount(){

        return null;
    }




}
