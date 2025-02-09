package com.albionhelper.helper.domain.battleapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @JsonProperty("MainHand")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "main_hand")
    private String mainHand;

    @JsonProperty("Offhand")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "off_hand")
    private String offHand;

    @JsonProperty("Head")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "head")
    private String head;

    @JsonProperty("Armor")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "armor")
    private String armor;

    @JsonProperty("Shoes")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "shoes")
    private String shoes;

    @JsonProperty("Mount")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "mount")
    private String mount;

    @JsonProperty("Potion")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "potion")
    private String potion;

    @JsonProperty("Food")
    @JsonDeserialize(using = EquipmentTypeDeserializer.class)
    @Column(name = "food")
    private String food;
}
