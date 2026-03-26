package com.albionhelper.helper.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;
    @JsonProperty("GuildId")
    private String guildId;
    @JsonProperty("GuildName")
    private String guildName;
    @JsonProperty("AllianceId")
    private String allianceId;
    @JsonProperty("AllianceName")
    private String allianceName;
    @JsonProperty("Avatar")
    private String avatar;
    @JsonProperty("AvatarRing")
    private String avatarRing;
    @JsonProperty("KillFame")
    private long killFame;
    @JsonProperty("DeathFame")
    private long deathFame;
    @JsonProperty("FameRatio")
    private String fameRatio;
    private String totalKills;
    private String gvgKills;
    private String gvgWon;

    @Override
    public String toString() {
        return this.getName() + " : " + this.getId();
    }
}
