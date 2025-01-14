package com.albionhelper.helper.domain.playerinfo;


import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerInfoDetail {

    // 인게임 id
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;

    // 길드
    @JsonProperty("GuildId")
    private String guildId;
    @JsonProperty("GuildName")
    private String guildName;

    // 동맹
    @JsonProperty("AllianceName")
    private String allianceName;

    // 킬페임
    @JsonProperty("KillFame")
    private long killFame;

    // 데스 페임
    @JsonProperty("DeathFame")
    private long deathFame;

    @JsonProperty("LifetimeStatistics")
    private LifetimeStatistics lifetimeStatistics;

    private String killFameStr;
    private String deathFameStr;

    public void setKillFame(long killFame) {
        this.killFame = killFame;
        this.killFameStr = Util.getUnit(this.killFame);
    }
    public void setDeathFame(long deathFame) {
        this.deathFame = deathFame;
        this.deathFameStr = Util.getUnit(this.deathFame);
    }

    @Override
    public String toString() {
        return "PlayerInfoDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", guildId='" + guildId + '\'' +
                ", guildName='" + guildName + '\'' +
                ", allianceName='" + allianceName + '\'' +
                ", killFame=" + killFame +
                ", deathFame=" + deathFame +
                ", lifetimeStatistics=" + lifetimeStatistics +
                ", killFameStr='" + killFameStr + '\'' +
                ", deathFameStr='" + deathFameStr + '\'' +
                '}';
    }
}
