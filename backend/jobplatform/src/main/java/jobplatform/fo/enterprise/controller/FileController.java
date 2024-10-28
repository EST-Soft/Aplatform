package jobplatform.fo.enterprise.controller;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeProfileImageDto;
import jobplatform.fo.enterprise.service.FileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload-image")
    public ResumeProfileImageDto uploadImage(@RequestParam("file") MultipartFile files) {
        ResumeProfileImageDto resumeProfileImageDto = fileService.uploadImage(files);

        return resumeProfileImageDto;
    } // uploadImage

    // @GetMapping(value = "{fileName}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
    //         MediaType.IMAGE_GIF_VALUE })
    // public Resource getImage(@PathVariable("fileName") String fileName) {
    //     Resource resource = fileService.getImage(fileName);
    //     return resource;
    // } // getImage

    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable String filename) {
        System.out.println("asdasadsds" + filename);
        File file = new File("/home/ubuntu/alpatform/file/" + filename);
        System.out.println(file);
        

        if(!file.exists()){
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = getMediaType(filename); // MIME 타입 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filebane=\"" + file.getName() + "\"");


        return ResponseEntity.ok()
            .headers(headers)
            .contentType(mediaType)
            .body(new FileSystemResource(file));
    }

    private MediaType getMediaType(String filename){
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    


    @PostMapping("/upload-attachment")
    public List<AttachmentDto> uploadAttachment(@RequestParam("file") MultipartFile[] files) {
        List<AttachmentDto> attachmentDtos = fileService.uploadAttachment(files);
        System.out.println("첨부파일" + attachmentDtos);
        return attachmentDtos;
    } // uploadImage

} // FileController
