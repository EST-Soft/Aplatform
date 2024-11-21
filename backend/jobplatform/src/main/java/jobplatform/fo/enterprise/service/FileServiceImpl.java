package jobplatform.fo.enterprise.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeProfileImageDto;

@Service
public class FileServiceImpl implements FileService {

    /*
     * @Value("${cloud.aws.s3.bucket}")
     * private String bucketName;
     * 
     * @Value("${file.url}")
     * private String fileUrl;
     * 
     * private final AmazonS3 amazonS3;
     * 
     * public FileServiceImpl(AmazonS3 amazonS3) {
     * this.amazonS3 = amazonS3;
     * } // FileServiceImpl
     * 
     * @Override
     * public String uploadImage(MultipartFile file) {
     * if(file.isEmpty()) return null;
     * 
     * String originalFileName = file.getOriginalFilename();
     * String extension =
     * originalFileName.substring(originalFileName.lastIndexOf("."));
     * String uuid = UUID.randomUUID().toString();
     * String saveFileName = uuid + extension;
     * 
     * try {
     * amazonS3.putObject(new PutObjectRequest(bucketName, saveFileName,
     * file.getInputStream(), null));
     * } catch (Exception exception) {
     * exception.printStackTrace();
     * return null;
     * }
     * String url = fileUrl + saveFileName;
     * return url;
     * } // uploadImage
     * 
     * @Override
     * public Resource getImage(String fileName) {
     * try {
     * S3Object s3Object = amazonS3.getObject(bucketName, fileName);
     * return new
     * UrlResource(s3Object.getObjectContent().getHttpRequest().getURI().toURL());
     * } catch (MalformedURLException exception) {
     * exception.printStackTrace();
     * return null;
     * } catch (Exception exception) {
     * exception.printStackTrace();
     * return null;
     * }
     * } // getImage
     */

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    @Override
public ResumeProfileImageDto uploadImage(MultipartFile files) {
    // 파일이 비어있는지 체크
    if (files.isEmpty()) {
        return null;
    }

    // 원본 파일명과 확장자 구하기
    String originalFileName = files.getOriginalFilename();
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    
    // UUID로 새로운 파일명 생성
    String uuid = UUID.randomUUID().toString();
    String saveFileName = uuid + extension;
    
    // 실제 파일 저장 경로
    String savePath = filePath + saveFileName;

    try {
        // 파일을 서버에 저장
        files.transferTo(new File(savePath));
    } catch (Exception exception) {
        exception.printStackTrace();
    }

    // 서버에서 접근할 수 있는 URL을 반환 (fileUrl은 파일 접근 URL의 기본 경로)
    String url = fileUrl + saveFileName;

    // 이미지 URL과 원본 파일명 반환
    ResumeProfileImageDto result = new ResumeProfileImageDto();
    result.setImgOrgnlFn(originalFileName);
    result.setImgFileUrl(url); // 서버 URL을 클라이언트로 반환
    return result;
}


  @Override
public Resource getImage(String fileName) {
    Path fileLocation = Paths.get(filePath, fileName);
    Resource resource = null;

    try {
        File file = fileLocation.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다: " + fileName);
        }
        resource = new UrlResource(file.toURI());
    } catch (FileNotFoundException e) {
        System.err.println("파일을 찾을 수 없습니다: " + fileName);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.", e);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", e);
    }

    return resource;
}




    @Override
    public List<AttachmentDto> uploadAttachment(MultipartFile[] files) {
        if (files.length == 0)
            return null;

        List<AttachmentDto> result = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String saveFileName = uuid + extention;
            String savePath = filePath + saveFileName;

            try {
                file.transferTo(new File(savePath));
            } catch (Exception exeption) {
                exeption.printStackTrace();
            }

            String url = fileUrl + saveFileName;

            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setAtchmntOrgnlFn(originalFileName);
            attachmentDto.setAtchmntUrl(url);
            result.add(attachmentDto);
        }
        return result;
    } // uploadAttachment

} // FileServiceImpl
