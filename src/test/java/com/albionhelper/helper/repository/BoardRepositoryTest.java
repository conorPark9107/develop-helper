package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.board.Board;
import com.albionhelper.helper.domain.board.BoardRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testSelectAll(){
        List<Board> list = boardRepository.findAll();

        for(Board b : list){
            System.out.println(b.toString());
        }
    }

    @Test
    public void 테스트데이터_INSERT(){

        for (int i = 0; i < 300; i++) {
            BoardRequestDTO dto = new BoardRequestDTO();
            dto.setNickname("toy" + i);
            dto.setTitle("어디가 가장 잘할까?" + i);
            dto.setCategory("일반");
            dto.setPassword("1234" + i);
            dto.setContents("<p>그르게 말이다~~</p>" + i);
            boardRepository.save(dto.toEntity());
        }
    }

    @Test
    public void 게시물조회_카테고리(){
        String category = "0";
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        Page<Board> list = boardRepository.findAllByCategory(pageable, category);
        for(Board b : list){
            System.out.println(b.toString());
        }

    }



}
