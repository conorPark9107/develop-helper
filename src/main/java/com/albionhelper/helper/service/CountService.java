package com.albionhelper.helper.service;

import com.albionhelper.helper.repository.BattlesRepository;
import com.albionhelper.helper.repository.KillboardRepository;
import org.springframework.stereotype.Service;

@Service
public class CountService {

    private final KillboardRepository killboardRepository;
    private final BattlesRepository battlesRepository;

    public CountService(KillboardRepository killboardRepository, BattlesRepository battlesRepository) {
        this.killboardRepository = killboardRepository;
        this.battlesRepository = battlesRepository;
    }



}
