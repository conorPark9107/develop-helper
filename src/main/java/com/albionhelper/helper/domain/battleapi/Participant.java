package com.albionhelper.helper.domain.battleapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 자동 증가하는 DB ID

    @JsonProperty("Id")
    @Column(unique = true, nullable = false)
    private String playerId;  // API에서 받은 "Id" 값

    @JsonProperty("Name")
    private String name;

    @JsonProperty("AverageItemPower")
    private double averageItemPower;

    @JsonProperty("DamageDone")
    private int damageDone;

    @JsonProperty("SupportHealingDone")
    private int supportHealingDone;

    @ManyToOne
    @JoinColumn(name = "kill_event_id")
    private KillEvent killEvent;

    @JsonProperty("Equipment")
    @OneToOne(cascade = CascadeType.ALL)
    private Equipment equipment;
}