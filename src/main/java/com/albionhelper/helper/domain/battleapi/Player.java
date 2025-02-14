package com.albionhelper.helper.domain.battleapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 자동 증가하는 DB ID

    @JsonProperty("Id")
    private String playerId;  // API에서 받은 "Id" 값

    @JsonProperty("Name")
    private String name;

    @JsonProperty("AverageItemPower")
    private double averageItemPower;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("AllianceName")
    private String allianceName;

    @JsonProperty("KillFame")
    private int killFame;

    @JsonProperty("DeathFame")
    private int deathFame;

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
