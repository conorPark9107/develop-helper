package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.metaBuild.TierList;
import com.albionhelper.helper.domain.metaBuild.TierListDTO;
import com.albionhelper.helper.repository.MetaBuildRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaBuildService {

    private final MetaBuildRepository metaBuildRepository;

    public MetaBuildService(MetaBuildRepository metaBuildRepository) {
        this.metaBuildRepository = metaBuildRepository;
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

    public List<TierList> getTierList() {
        return null;
    }
}
