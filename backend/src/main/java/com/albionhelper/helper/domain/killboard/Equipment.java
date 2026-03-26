package com.albionhelper.helper.domain.killboard;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {

    @JsonProperty("MainHand")
    private Item mainHand;

    @JsonProperty("OffHand")
    private Item offHand;

    @JsonProperty("Head")
    private Item head;

    @JsonProperty("Armor")
    private Item armor;

    @JsonProperty("Shoes")
    private Item shoes;

    @JsonProperty("Bag")
    private Item bag;

    @JsonProperty("Cape")
    private Item cape;

    @JsonProperty("Mount")
    private Item mount;

    @JsonProperty("Potion")
    private Item potion;

    @JsonProperty("Food")
    private Item food;

    @Override
    public String toString() {
        return "Equipment{" +
                "mainHand=" + mainHand +
                ", offHand=" + offHand +
                ", head=" + head +
                ", armor=" + armor +
                ", shoes=" + shoes +
                ", bag=" + bag +
                ", cape=" + cape +
                ", mount=" + mount +
                ", potion=" + potion +
                ", food=" + food +
                '}';
    }
}
