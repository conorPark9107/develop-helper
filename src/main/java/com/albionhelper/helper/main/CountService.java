package com.albionhelper.helper.main;

import com.albionhelper.helper.domain.battle.BattleCountLog;
import com.albionhelper.helper.domain.battle.BattleCountLogDTO;
import com.albionhelper.helper.domain.killboard.PlayerLog;
import com.albionhelper.helper.domain.killboard.PlayerLogDTO;
import com.albionhelper.helper.battleBoard.BattleBoardRepository;
import com.albionhelper.helper.playerKillBoard.KillboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountService {

    private final KillboardRepository killboardRepository;
    private final BattleBoardRepository battleBoardRepository;

    public CountService(KillboardRepository killboardRepository, BattleBoardRepository battleBoardRepository) {
        this.killboardRepository = killboardRepository;
        this.battleBoardRepository = battleBoardRepository;
    }

    public List<BattleCountLogDTO> getBattleCount(){
        List<BattleCountLog> op = battleBoardRepository.findTop1ByServer();
        return op.stream().map(BattleCountLog::toDto).toList();
    }

    public List<PlayerLogDTO> getPlayerCount(){
        List<PlayerLog> op = killboardRepository.findTop1ByServer();
        return op.stream().map(PlayerLog::toDto).toList();
    }




}
