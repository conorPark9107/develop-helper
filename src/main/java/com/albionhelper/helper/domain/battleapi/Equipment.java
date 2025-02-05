package com.albionhelper.helper.domain.battleapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("MainHand")
    private String mainHand;

    @JsonProperty("Offhand")
    private String offHand;

    @JsonProperty("Head")
    private String head;

    @JsonProperty("Armor")
    private String armor;

    @JsonProperty("Shoes")
    private String shoes;

    @JsonProperty("Mount")
    private String mount;

    @JsonProperty("Potion")
    private String potion;

    @JsonProperty("Food")
    private String food;
}
