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
public class BoardRequestDTO {

    @NotNull
    private String title;
    private String nickname;
    @NotNull
    private int category;
    private String contents;
    @NotNull
    private String password;

    public Board toEntity(){
        return new Board()
                .builder()
                .title(title)
                .nickname(nickname)
                .category(category)
                .contents(contents)
                .password(password)
                .build();
    }

    @Override
    public String toString() {
        return "BoardRequestDTO{" +
                "title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", category=" + category +
                ", contents='" + contents + '\'' +
                '}';
    }
}
