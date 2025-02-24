package jobplatform.fo.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import jobplatform.fo.utils.S3FileUtil;

@RestController
@Slf4j
public class SampleFileController {

    @Autowired
    private S3FileUtil S3FileUtil;

    @PostMapping(path = "/s3fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String s3fileUpload(
                                        @RequestPart(value = "fileName") String fileName,
                                        @RequestPart(value = "file", required = false) MultipartFile multipartFile
                                        ) throws IOException {

        String extend = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String url = S3FileUtil.upload(fileName,multipartFile,extend);

        return url;
    }

    @PostMapping(path = "/s3fileDownload")
    public ResponseEntity<byte[]> s3fileDownload(@RequestPart(value = "fileUrl") String fileUrl) throws IOException {

        return S3FileUtil.download(fileUrl);
    }

    @PostMapping(path = "/s3fileDelete")
    public ResponseEntity<Object> s3fileDelete(@RequestPart(value = "fileUrl") String fileUrl) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(S3FileUtil.deleteFile(fileUrl));
    }
}