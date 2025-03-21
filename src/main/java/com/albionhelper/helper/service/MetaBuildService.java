package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.metaBuild.TierList;
import com.albionhelper.helper.domain.metaBuild.TierListDTO;
import com.albionhelper.helper.repository.MetaBuildRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaBuildService {

    private final MetaBuildRepository metaBuildRepository;

    public MetaBuildService(MetaBuildRepository metaBuildRepository) {
        this.metaBuildRepository = metaBuildRepository;
    }

    public String register(TierListDTO dto) {
        TierList tierList = new TierList();
        TierList entity = tierList.toEntity(dto);
        System.out.println(dto.getT1Name());
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
}
