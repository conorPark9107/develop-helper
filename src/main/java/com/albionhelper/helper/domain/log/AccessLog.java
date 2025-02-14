package com.albionhelper.helper.domain.log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private LocalDateTime accessTime;

    public AccessLog(Long id, String ipAddress, LocalDateTime accessTime) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.accessTime = accessTime;
    }
}
