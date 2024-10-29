package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}