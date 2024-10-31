package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long board_id);
    Long countById(Long id);


}
