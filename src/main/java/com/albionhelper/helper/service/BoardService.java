package com.albionhelper.helper.service;


import com.albionhelper.helper.domain.board.*;
import com.albionhelper.helper.repository.BoardRepository;
import com.albionhelper.helper.repository.CategoryRepository;
import com.albionhelper.helper.repository.CommentRepository;
import com.albionhelper.helper.repository.InquireRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class BoardService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final InquireRepository inquireRepository;
    private final CategoryRepository categoryRepository;

    public BoardService(BoardRepository boardRepository,
                        CommentRepository commentRepository,
                        InquireRepository inquireRepository,
                        CategoryRepository categoryRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.inquireRepository = inquireRepository;
        this.categoryRepository = categoryRepository;
    }

    // 게시글 등록 메서드.
    public void registerBoard(BoardRequestDTO dto) throws UnknownHostException {
        log.info("registerBoard : {}", dto);

        if(dto.getNickname().isEmpty()){
            dto.setNickname("익명");
            log.info("nickname was empty so changed : {} ", dto.getNickname());
        }

        Board board = dto.toEntity();
        boardRepository.save(board);
    }

    // 게시글 카테고리별 조회 메서드.
    public Page<BoardResponseDTO> findAllById(Pageable pageable, String category) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Board> boardPage = category.equals("전체")
                ? boardRepository.findAll(pageable)
                : boardRepository.findAllByCategory(pageable, category);

        return boardPage.map(Board::toResponseDTO);
    }

    // 특정 한 게시글 조회.
    public BoardResponseDTO findBoardById(Long id) {
        Optional<Board> optional = boardRepository.findById(id);
        return optional.map(Board::toResponseDTO)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다. id=" + id));
    }

    // 댓글 등록.
    public void registerComment(CommentRequestDTO dto) throws UnknownHostException {
        String nickname = Optional.ofNullable(dto.getNickname())
                        .filter(n -> !n.isEmpty())
                        .orElse("익명");

        dto.setNickname(nickname);
        commentRepository.save(dto.toEntity());
    }

    // 댓글 조회.
    public List<CommentResponseDTO> findAllCommentsByBoardId(Long board_id) {
        List<Comment> list = commentRepository.findAllByBoardId(board_id);
        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for(Comment c : list){
            dtoList.add(c.toResponseDTO());
        }
        return dtoList;
    }

    // 댓글 수 조회.
    public long countCommentsByBoardId(Long id) {
        return commentRepository.countById(id);
    }

    // 조회수 증가.
    public void incrementViewCount(Long id) {
        Board b = boardRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다. id : " + id));
        b.setView_count(b.getView_count() + 1);
        boardRepository.save(b);
    }

    // 추천 / 비추천 처리.
    public int updateUpdownCount(Long boardId, String status){
        Board b = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다: id=" + boardId));
        int upOrDown = status.equals("p") ? 1 : -1;
        b.setUpdown(b.getUpdown() + upOrDown);
        boardRepository.save(b);
        return b.getUpdown();
    }

    // 댓글 삭제 (내용만 변경)
    public String deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다: id=" + commentId));
        comment.setComment("삭제된 댓글입니다.");
        return commentRepository.save(comment).getComment();
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 비밀번호 확인
    public String checkPassword(Long id, String password) {
        Optional<Board> b = boardRepository.findByIdAndPassword(id, password);
        if(b.isEmpty()){
            return "F";
        }
        return "T";
    }
    
    // 문의 등록
    public String registerInquire(String text) {
        Inquire inquire = new Inquire();
        inquire.setContent(text);
        return inquireRepository.save(inquire).toString();
    }

    // 문의 답변 등록
    public void updateInquireAnswer(UpdateInquireDTO dto){
        Inquire inquire = inquireRepository.findById(dto.getId()).get();
        inquire.setAnswer(dto.getAnswer());
        inquireRepository.save(inquire);
    }
    
    // 문의 전체 조회
    public List<Inquire> findAllInquire() {
        return inquireRepository.findAll();
    }

    // 문의 삭제
    public void deleteInquire(long id) {
        inquireRepository.deleteById(id);
    }

    // 카테고리 전체 조회
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    // 카테고리 삭제.
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    // 카테고리 등록
    public void registerCategory(String category) {
        Category c = new Category();
        c.setCategory(category);
        categoryRepository.save(c);
    }
}
