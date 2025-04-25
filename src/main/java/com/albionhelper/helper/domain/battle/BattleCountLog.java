package com.albionhelper.helper.domain.battle;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "battleCountLog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleCountLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guildId")
    private String guildId;

    @Column(name = "guildName")
    private String guildName;

    @Column(name = "server")
    private String server;

    @Column(name = "count")
    private Long count;

    @Column(name = "searchDate")
    private LocalDateTime searchDate;

    @PrePersist
    public void prePersist(){
        if(searchDate == null){
            searchDate = LocalDateTime.now();
        }
        if(count == null){
            count = 1L;
        }
    }

    public BattleCountLogDTO toDto(){
        return BattleCountLogDTO.builder()
                .guildId(guildId)
                .guildName(guildName)
                .server(server)
                .count(count)
                .build();
    }


}
