package com.albionhelper.helper.domain.metaBuild;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TierListDTO {

    private String userId;
    @NotNull
    private String title;
    private LocalDateTime writeDate;
    private String category;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String t9;
    private String t10;
    private Long up;

}
