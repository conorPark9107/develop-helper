package com.albionhelper.helper.domain.battle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guild {

    @JsonProperty("name")
    private String name;
    @JsonProperty("kills")
    private int kills;
    @JsonProperty("deaths")
    private int deaths;
    @JsonProperty("killFame")
    private int killFame;
    @JsonProperty("alliance")
    private String alliance;
    @JsonProperty("allianceId")
    private String allianceId;
    @JsonProperty("id")
    private String id;

    @Override
    public String toString() {
        return "Guild{" +
                "name='" + name + '\'' +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", killFame=" + killFame +
                ", alliance='" + alliance + '\'' +
                ", allianceId='" + allianceId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
