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

    public List<CommentResponseDTO> findAllById(Long board_id) {
        List<Comment> list = commentRepository.findAllByBoardId(board_id);
        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for(Comment c : list){
            dtoList.add(c.toResponseDTO());
        }
        return dtoList;
    }

    public long showCount(Long id) {
        return commentRepository.countById(id);
    }

    public void modifyViewCount(Long id) {
        Board b = boardRepository.findById(id).get();
        b.setView_count(b.getView_count() + 1);
        boardRepository.save(b);
    }

    public int modifyUpdownCount(Long id, String status){
        Board b = boardRepository.findById(id).get();
        if(status.equals("p")){
            b.setUpdown(b.getUpdown() + 1);
        }else {
            b.setUpdown(b.getUpdown() - 1);
        }
        boardRepository.save(b);
        return b.getUpdown();
    }

    public String deleteComment(Long id) {
        Comment c = commentRepository.findById(id).get();
        c.setComment("삭제된 댓글입니다.");
        return commentRepository.save(c).getComment();
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public String checkPassword(Long id, String password) {
        Optional<Board> b = boardRepository.findByIdAndPassword(id, password);
        if(b.isEmpty()){
            return "F";
        }
        return "T";
    }

    public String registerInquire(String text) {
        Inquire inquire = new Inquire();
        inquire.setContent(text);
        return inquireRepository.save(inquire).toString();
    }
    
    public void updateInquire(UpdateInquireDTO dto){
        Inquire inquire = inquireRepository.findById(dto.getId()).get();
        inquire.setAnswer(dto.getAnswer());
        inquireRepository.save(inquire);
    }

    public List<Inquire> findAllInquire() {
        return inquireRepository.findAll();
    }

    public void deleteInquire(long id) {
        inquireRepository.deleteById(id);
    }

    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    public void registerCategory(String category) {
        Category c = new Category();
        c.setCategory(category);
        categoryRepository.save(c);
    }
}
