package jobplatform.fo.enterprise.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import jobplatform.fo.enterprise.domain.dto.ResumeProfileImageDto;

public interface FileService {

    ResumeProfileImageDto uploadImage(MultipartFile files);

    Resource getImage(String fileName);
    
} // FileService
