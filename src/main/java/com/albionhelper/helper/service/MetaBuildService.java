package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.metaBuild.*;
import com.albionhelper.helper.repository.MetaBuildRepository;
import com.albionhelper.helper.repository.TierListCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MetaBuildService {

    private final MetaBuildRepository metaBuildRepository;
    private final TierListCommentRepository tierListCommentRepository;

    public MetaBuildService(MetaBuildRepository metaBuildRepository, TierListCommentRepository tierListCommentRepository) {
        this.metaBuildRepository = metaBuildRepository;
        this.tierListCommentRepository = tierListCommentRepository;
    }

    public String register(TierListDTO dto) {
        TierList tierList = new TierList();
        TierList entity = tierList.toEntity(dto);
        try{
            metaBuildRepository.save(entity);
            return "저장이 완료되었습니다.";
        }catch (Exception e){
            return "알수없는 이유로 저장에 실패하였습니다.";
        }
    }

    public Page<TierListDTO> getTierList(String category, String sortBy, int page, int size) {
        List<String> canSort = List.of("writeDate", "up", "tierListComments"); // 컬럼

        if(!canSort.contains(sortBy)){
            sortBy = "writeDate";
        }
        if(category.equals("전체")){
            category = null;
        }

        if (sortBy.equals("tierListComments")) {
            Pageable pageable = PageRequest.of(page, size);
            Page<TierList> entityList = metaBuildRepository.findAllByCategoryOrderByCommentCountDesc(category, pageable);
            return entityList.map(TierList::toDTO);
        }else{
            Sort sort = Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<TierList> entityList = metaBuildRepository.findAllByCategory(category, pageable);
            return entityList.map(TierList::toDTO);
        }
    }

    public TierListDTO getTierListById(Long id) {
        Optional<TierList> optional = metaBuildRepository.findById(id);
        return optional.get().toDTO();
    }

    public String registerComment(TierListCommentDTO dto) {
        TierList tierList = metaBuildRepository.findById(dto.getTierListId()).get();
        dto.setTierList(tierList);
        TierListComment tlc = new TierListComment();
        TierListComment entity = tlc.toEntity(dto);
        try{
            tierListCommentRepository.save(entity);
            return "O" + entity.getId();
        }catch (Exception e){
            return "X";
        }

    }

    @Transactional
    public String modifyUp(Long id) {
        Optional<TierList> byId = metaBuildRepository.findById(id);
        if(byId.isPresent()){
            TierList tierList = byId.get();
            tierList.setUp(tierList.getUp() + 1);
            return "O";
        }else{
            return "X";
        }
    }

    @Transactional
    public String deleteComment(DeleteCommentDTO dto) {
        Optional<TierListComment> optional = tierListCommentRepository.findByIdAndPassword(dto.getId(), dto.getPw());
        if(optional.isEmpty()){
            return "X";
        }
        TierListComment tierListComment = optional.get();
        tierListComment.setComment("삭제된 댓글입니다.");
        return "O" + tierListComment.getId();
    }

//    @Service
//    @RequiredArgsConstructor
//    public class TierListCommentService {
//
//        private final TierListCommentRepository commentRepository;
//        private final TierListRepository tierListRepository;
//
//        public TierListComment createComment(TierListCommentDTO commentDTO) {
//            // DTO의 tierListId를 이용하여 TierList 엔티티 조회
//            TierList tierList = tierListRepository.findById(commentDTO.getTierListId())
//                    .orElseThrow(() -> new RuntimeException("해당 TierList가 존재하지 않습니다."));
//
//            // 조회한 TierList 엔티티를 사용하여 Comment 엔티티 생성
//            TierListComment comment = commentDTO.toEntity(tierList);
//            return commentRepository.save(comment);
//        }
//    }




}
