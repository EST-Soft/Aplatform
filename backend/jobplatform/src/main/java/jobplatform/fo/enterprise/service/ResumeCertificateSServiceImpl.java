package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.ResumeCertificateSDto;
import jobplatform.fo.enterprise.domain.entity.ResumeCertificateSEntity;
import jobplatform.fo.enterprise.domain.repository.ResumeCertificateSRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeCertificateSServiceImpl implements ResumeCertificateSService {

    private final ResumeCertificateSRepository resumeCertificateSRepository;

    @Override
    public void insertResumeCertificateS(Long mbrSq, Long rsmSq, List<ResumeCertificateSDto> resumeCertificateSDtos) {
        for (ResumeCertificateSDto resumeCertificateSDto : resumeCertificateSDtos) {
            ResumeCertificateSEntity resumeCertificateSEntity = new ResumeCertificateSEntity(mbrSq, rsmSq,
                    resumeCertificateSDto);
            resumeCertificateSRepository.save(resumeCertificateSEntity);
        }
    } // insertResumeCertificateS

} // ResumeCertificateSServiceImpl
