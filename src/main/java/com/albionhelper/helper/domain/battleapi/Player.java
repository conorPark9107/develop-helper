package com.albionhelper.helper.domain.battleapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double averageItemPower;
    private String guildName;
    private String allianceName;
    private int fame;

    @OneToOne(cascade = CascadeType.ALL)
    private Equipment equipment;
}
