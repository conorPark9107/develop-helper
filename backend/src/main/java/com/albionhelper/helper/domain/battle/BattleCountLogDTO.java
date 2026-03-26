package com.albionhelper.helper.domain.battle;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleCountLogDTO {

    private String guildId;
    private String guildName;
    private String server;
    private Long count;

    public BattleCountLog toEntity(){
        return BattleCountLog.builder()
                .guildId(guildId)
                .guildName(guildName)
                .server(server)
                .count(count)
                .build();
    }
}
