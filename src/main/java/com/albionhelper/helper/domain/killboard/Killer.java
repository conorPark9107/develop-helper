package com.albionhelper.helper.domain.killboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Killer {

    @JsonProperty("AverageItemPower")
    private int averageItemPower;

    @JsonProperty("Equipment")
    private Equipment equipment;


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

}
