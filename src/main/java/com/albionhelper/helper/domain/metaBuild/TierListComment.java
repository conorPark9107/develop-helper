package com.albionhelper.helper.domain.metaBuild;
import com.albionhelper.helper.domain.board.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tierListComment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierListComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tier_list_id", nullable = false)
    private TierList tierList;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "comment", nullable = false, length = 2000)
    private String comment;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "writeDate", nullable = false)
    private LocalDateTime writeDate;

    @PrePersist
    public void prePersist() {
        if (writeDate == null) {
            writeDate = LocalDateTime.now();  // 기본값을 현재 시간으로 설정
        }
    }

    public TierListComment toEntity(TierListCommentDTO dto) {
        return TierListComment.builder()
                .tierList(dto.getTierList())
                .userId(dto.getUserId())
                .comment(dto.getComment())
                .password(dto.getPassword())
                .writeDate(dto.getWriteDate())
                .build();
    }

    public TierListCommentDTO toDTO() {
        return TierListCommentDTO.builder()
                .id(this.id)
                .tierListId(this.tierList.getId())
                .userId(this.userId)
                .comment(this.comment)
                .password(this.password)
                .writeDate(this.writeDate)
                .build();
    }
}
