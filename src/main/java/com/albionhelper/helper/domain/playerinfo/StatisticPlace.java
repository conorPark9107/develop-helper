package com.albionhelper.helper.domain.playerinfo;


import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticPlace {

    @JsonProperty("Total")
    private long total;
    private String totalStr;

    @JsonProperty("Royal")
    private long royal;
    private String royalStr;

    @JsonProperty("Outlands")
    private long outlands;
    private String outlandsStr;

    @JsonProperty("Avalon")
    private long avalon;
    private String avalonStr;

    @JsonProperty("Hellgate")
    private long hellgate;
    private String hellgateStr;

    @JsonProperty("CorruptedDungeon")
    private long corruptedDungeon;
    private String corruptedDungeonStr;

    @JsonProperty("Mists")
    private long mists;
    private String mistsStr;

    public void setTotal(long total) {
        this.total = total;
        this.totalStr = Util.getUnit(total);
    }

    public void setRoyal(long royal) {
        this.royal = royal;
        this.royalStr = Util.getUnit(royal);
    }

    public void setOutlands(long outlands) {
        this.outlands = outlands;
        this.outlandsStr = Util.getUnit(outlands);
    }

    public void setAvalon(long avalon) {
        this.avalon = avalon;
        this.avalonStr = Util.getUnit(avalon);
    }

    public void setHellgate(long hellgate) {
        this.hellgate = hellgate;
        this.hellgateStr = Util.getUnit(hellgate);
    }

    public void setCorruptedDungeon(long corruptedDungeon) {
        this.corruptedDungeon = corruptedDungeon;
        this.corruptedDungeonStr = Util.getUnit(corruptedDungeon);
    }

    public void setMists(long mists) {
        this.mists = mists;
        this.mistsStr = Util.getUnit(mists);
    }

    @Override
    public String toString() {
        return "StatisticPlace{" +
                "total=" + totalStr +
                ", royal=" + royalStr +
                ", outlands=" + outlandsStr +
                ", avalon=" + avalonStr +
                ", hellgate=" + hellgateStr +
                ", corruptedDungeon=" + corruptedDungeonStr +
                ", mists=" + mistsStr +
                '}';
    }
}
