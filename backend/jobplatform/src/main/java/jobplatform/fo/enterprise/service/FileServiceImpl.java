package jobplatform.fo.enterprise.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
     private String filePath; // 절대 경로
     
    @Value("${file.url}")
    private String fileUrl;

    @Override
public ResumeProfileImageDto uploadImage(MultipartFile file) {
    // 파일이 비어있는지 체크
    if (file.isEmpty()) {
        throw new IllegalArgumentException("파일이 비어 있습니다.");
    }

    String originalFileName = file.getOriginalFilename();
    if (originalFileName == null || !originalFileName.contains(".")) {
        throw new IllegalArgumentException("잘못된 파일명입니다.");
    }

    // 원본 파일명과 확장자 구하기
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

    // 허용된 파일 확장자 체크
    if (!Arrays.asList(".jpg", ".jpeg", ".png", ".gif").contains(extension.toLowerCase())) {
        throw new IllegalArgumentException("허용되지 않는 파일 형식입니다.");
    }

    // UUID로 새로운 파일명 생성
    String uuid = UUID.randomUUID().toString();
    String saveFileName = uuid + extension;

    // 실제 파일 저장 경로
    String savePath = filePath + saveFileName;
    System.out.println("저장할 파일 이름: " + saveFileName);

    // 디렉토리 존재 여부 체크
    File directory = new File(filePath);
    if (!directory.exists() && !directory.mkdirs()) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리 생성 실패");
    }

    try {
        file.transferTo(new File(savePath));
        System.out.println("파일이 성공적으로 저장되었습니다: " + savePath);
    } catch (IOException e) {
        System.err.println("파일 저장 실패: " + e.getMessage());
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 중 오류 발생");
    }

    // 서버에서 접근할 수 있는 URL을 반환
    String url = fileUrl + saveFileName;

    // 이미지 URL과 원본 파일명 반환
    ResumeProfileImageDto result = new ResumeProfileImageDto();
    result.setImgOrgnlFn(originalFileName);
    result.setImgFileUrl(url); // 서버 URL을 클라이언트로 반환
    return result;
}

    
    

    @Override
    public Resource getImage(String fileName) {
        Path fileLocation = Paths.get(filePath, fileName); // 파일 저장 경로와 파일명 합치기
        Resource resource = null;
    
        try {
            File file = fileLocation.toFile();
            if (!file.exists()) {
                throw new FileNotFoundException("파일을 찾을 수 없습니다: " + fileName);
            }
            resource = new UrlResource(file.toURI());  // 파일을 URLResource로 변환
           
    
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("파일을 읽을 수 없습니다.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("파일을 찾을 수 없습니다: " + fileName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", e);
        }
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
