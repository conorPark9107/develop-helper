package com.albionhelper.helper.domain.battleapi;

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
    private String mainHand;
    private String offHand;
    private String head;
    private String armor;
    private String shoes;
    private String mount;
    private String potion;
    private String food;
}
