package com.albionhelper.helper.domain.metaBuild;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierListCommentDTO {

    private Long id;
    private Long tierListId;
    private TierList tierList;
    private String userId;
    private String comment;
    private String password;
    private LocalDateTime writeDate;

}
