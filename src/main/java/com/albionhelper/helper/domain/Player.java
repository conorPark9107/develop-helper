package com.albionhelper.helper.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String id;
    private String name;
    private String guildName;
    private long killFame;
    private long deathFame;
}
