package com.albionhelper.helper.domain.board;

import jakarta.persistence.Column;
import lombok.*;


import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long id;
    private Long board_id;
    private String nickname;
    private String password;
    private LocalDateTime write_date;
    private String comment;
    private Long comment_group;


}
