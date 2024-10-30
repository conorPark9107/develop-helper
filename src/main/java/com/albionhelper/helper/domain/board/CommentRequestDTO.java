package com.albionhelper.helper.domain.board;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {

    @NotNull
    private Long board_id;
    private String nickname;

    @NotNull
    private String password;
    private String comment;
    private Long comment_group;

    public Comment toEntity(){
        return new Comment().builder()
                .boardId(board_id)
                .nickname(nickname)
                .password(password)
                .comment(comment)
                .comment_group(comment_group)
                .build();
    }

}
