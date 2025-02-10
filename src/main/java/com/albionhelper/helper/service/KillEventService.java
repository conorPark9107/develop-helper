package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.battleapi.Equipment;
import com.albionhelper.helper.domain.battleapi.KillEvent;

import com.albionhelper.helper.domain.battleapi.Player;
import com.albionhelper.helper.repository.EquipmentRepository;
import com.albionhelper.helper.repository.KillEventRepository;

import com.albionhelper.helper.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KillEventService {

    private final KillEventRepository killEventRepository;
    private final PlayerRepository playerRepository;
    private final EquipmentRepository equipmentRepository;
    private final WebClient webClient;

    @Transactional
    public void fetchAndSaveKillEvents(String urls){
        webClient.get()
                .uri(urls)
                .retrieve()
                .bodyToMono(KillEvent[].class)
                .flatMapMany(Flux::fromArray) // 배열을 개별 요소로 변환
                .doOnNext(this::saveKillEvent)
                .subscribe();
    }

    @Transactional
    public void saveKillEvent(KillEvent event){
        // API에서 받은 eventId로 기존 데이터 존재 여부 확인
        if (!killEventRepository.existsByEventId(event.getEventId())) {
            KillEvent save = killEventRepository.save(event);
            KillEvent e = killEventRepository.findById(save.getId()).get();
            Player killer = event.getKiller();
            Player victim = event.getVictim();
            killer.setKillEvent(e);
            victim.setKillEvent(e);
            event.setKiller(killer);
            event.setVictim(victim);
            playerRepository.save(event.getKiller());
            playerRepository.save(event.getVictim());

            List<Player> list = event.getParticipants()
                    .stream()
                    .map(player -> {
                    player.setKillEvent(e);
                    player.getEquipment().setPlayer(player);
                    return player;
            }).toList();
            event.setParticipants(list);

            playerRepository.saveAll(event.getParticipants());
            Equipment killerEquip = event.getKiller().getEquipment();
            killerEquip.setPlayer(event.getKiller());
            event.getKiller().setEquipment(killerEquip);

            Equipment victimEquip = event.getVictim().getEquipment();
            victimEquip.setPlayer(event.getVictim());
            event.getVictim().setEquipment(victimEquip);

            equipmentRepository.save(event.getKiller().getEquipment());
            equipmentRepository.save(event.getVictim().getEquipment());
        }

    }




}
