package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import jobplatform.fo.enterprise.domain.entity.AttachmentEntity;
import jobplatform.fo.enterprise.domain.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public void insertImageAttachment(Long mbrSq, Long rsmSq, ResumeDataDTO resumeDataDTO) {
        AttachmentEntity attachmentEntity = new AttachmentEntity(mbrSq, rsmSq, resumeDataDTO);
        attachmentRepository.save(attachmentEntity);
    } // insertAttachment

    @Override
    public void insertResumeAttachment(Long mbrSq, Long rsmSq, List<AttachmentDto> attachmentDtos) {
        for (AttachmentDto attachmentDto : attachmentDtos) {
            AttachmentEntity attachmentEntity = new AttachmentEntity(mbrSq, rsmSq, attachmentDto);
            attachmentRepository.save(attachmentEntity);
        }
    } // insertResumeAttachment

} // AttachmentServiceImpl
