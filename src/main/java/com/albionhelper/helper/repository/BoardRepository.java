package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.board.Board;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
//    Page<Board> findAll(Pageable pageable);

    Page<Board> findAll(Pageable pageable);

}
