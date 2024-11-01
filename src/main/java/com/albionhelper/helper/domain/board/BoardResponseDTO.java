package com.albionhelper.helper.domain.board;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDTO {

    private Long id;
    private String nickname;
    private String title;
    private String category;
    private String contents;
    private LocalDateTime write_date;
    private int view_count;
    private int updown;
    private int commentCount;




}
