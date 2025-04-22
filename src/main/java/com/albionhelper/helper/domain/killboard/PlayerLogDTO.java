package com.albionhelper.helper.domain.killboard;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerLogDTO {
    private String userId;
    private String userName;
    private String server;
    private Long count;

    public PlayerLog toEntity(){
        return PlayerLog.builder()
                .userId(userId)
                .userName(userName)
                .server(server)
                .count(count)
                .build();
    }

}
