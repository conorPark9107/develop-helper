package com.albionhelper.helper.service;


import com.albionhelper.helper.domain.board.*;
import com.albionhelper.helper.repository.BoardRepository;
import com.albionhelper.helper.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BoardService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    public void registerBoard(BoardRequestDTO dto) throws UnknownHostException {
        log.info("registerBoard : {}", dto);

        if(dto.getNickname().isEmpty()){
            dto.setNickname(InetAddress.getLocalHost().getHostAddress());
            log.info("nickname was empty so changed : {} ", dto.getNickname());
        }

        Board board = dto.toEntity();
        boardRepository.save(board);
    }

    public Page<BoardResponseDTO> findAllByIdAsc(Pageable pageable) {
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Board> list = boardRepository.findAll(pageable);
        Page<BoardResponseDTO> dtoList = list.map(
          board -> BoardResponseDTO.builder()
                  .id(board.getId())
                  .nickname(board.getNickname())
                  .title(board.getTitle())
                  .category(board.getCategory())
                  .contents(board.getContents())
                  .write_date(board.getWrite_date())
                  .view_count(board.getView_count())
                  .updown(board.getUpdown())
                  .build()
        );
        return dtoList;
    }

    public BoardResponseDTO findBoardById(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.get().toResponseDTO();
    }

    public void registerComment(CommentRequestDTO dto) throws UnknownHostException {
        if(dto.getNickname().isEmpty()){
            dto.setNickname(InetAddress.getLocalHost().getHostAddress());
            log.info("nickname was empty so changed : {} ", dto.getNickname());
        }

        commentRepository.save(dto.toEntity());

    }

    public List<CommentResponseDTO> findAllById(Long board_id) {
        List<Comment> list = commentRepository.findAllByBoardId(board_id);
        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for(Comment c : list){
            dtoList.add(c.toResponseDTO());
        }
        return dtoList;
    }

}
