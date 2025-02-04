package com.albionhelper.helper.domain.battleapi;

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
    private Long id;
    private String name;
    private double averageItemPower;
    private int damageDone;

    @ManyToOne
    @JoinColumn(name = "kill_event_id")
    private KillEvent killEvent;
}