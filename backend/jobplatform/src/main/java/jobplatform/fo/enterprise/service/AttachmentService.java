package jobplatform.fo.enterprise.service;

import java.util.List;

import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;

public interface AttachmentService {

    public void insertImageAttachment(Long mbrSq, Long rsmSq, ResumeDataDTO resumeDataDTO);

    public void insertResumeAttachment(Long mbrSq, Long rsmSq, List<AttachmentDto> attachmentDtos);
} // AttachmentService
