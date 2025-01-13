package com.albionhelper.helper.domain.playerinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gathering {

    @JsonProperty("Fiber")
    private StatisticPlace fiber;
    @JsonProperty("Hide")
    private StatisticPlace hide;
    @JsonProperty("Ore")
    private StatisticPlace ore;
    @JsonProperty("Rock")
    private StatisticPlace rock;
    @JsonProperty("Wood")
    private StatisticPlace wood;
    @JsonProperty("All")
    private StatisticPlace all;

    @Override
    public String toString() {
        return "Gathering{" +
                "fiber=" + fiber +
                ", hide=" + hide +
                ", ore=" + ore +
                ", rock=" + rock +
                ", wood=" + wood +
                ", all=" + all +
                '}';
    }
}
