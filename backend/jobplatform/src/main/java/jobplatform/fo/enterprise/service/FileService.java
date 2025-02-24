package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeProfileImageDto;

public interface FileService {

    public ResumeProfileImageDto uploadImage(MultipartFile files);

    public List<AttachmentDto> uploadAttachment(MultipartFile[] files);

    public Resource getImage(String fileName);

} // FileService
