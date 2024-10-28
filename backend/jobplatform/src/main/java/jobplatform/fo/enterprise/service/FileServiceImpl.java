package jobplatform.fo.enterprise.service;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        if (files.isEmpty())
            return null;

        String originalFileName = files.getOriginalFilename();
        String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extention;
        String savePath = filePath + saveFileName;

        try {
            files.transferTo(new File(savePath));
        } catch (Exception exeption) {
            exeption.printStackTrace();
        }

        String url = fileUrl + saveFileName;

        ResumeProfileImageDto result = new ResumeProfileImageDto();
        result.setImgOrgnlFn(originalFileName);
        result.setImgFileUrl(url);
        return result;
    } // upload

    @Override
    public Resource getImage(String fileName) {
        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return resource;
    } // getImage

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
