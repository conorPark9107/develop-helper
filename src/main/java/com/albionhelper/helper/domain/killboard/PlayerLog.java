package com.albionhelper.helper.domain.killboard;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "playerLog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "searchDate")
    private LocalDateTime searchDate;

    @PrePersist
    public void prePersist(){
        if(searchDate == null){
            searchDate = LocalDateTime.now();
        }
    }

}


