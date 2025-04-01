package jobplatform.fo.enterprise.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.EnterMemberListDTO;
import jobplatform.fo.enterprise.domain.repository.EnterpriseMemberRepository;

@Service
public class EnterpriseMemberServiceImpl implements EnterpriseMemberService {

    @Autowired
    EnterpriseMemberRepository enterpriseMemberRepository;

    @Override
    public List<EnterMemberListDTO> getEnterpriseMemberList(Long entrprsSq) {
        return enterpriseMemberRepository.findByEntprsSq(entrprsSq).stream()
                .map(entity -> new EnterMemberListDTO(entity.getMbrSq()))
                .collect(Collectors.toList());
    }

}
