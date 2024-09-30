package jobplatform.fo.enterprise.service;

import java.util.List;

import jobplatform.fo.enterprise.domain.dto.ResumeCertificateSDto;

public interface ResumeCertificateSService {

    public void insertResumeCertificateS(Long mbrSq, Long rsmSq, List<ResumeCertificateSDto> resumeCertificateSDto);

} // ResumeCertificateSService
