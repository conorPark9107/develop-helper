package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

//    List<Board> findAll();
//    Board findOne(Long id);

}
