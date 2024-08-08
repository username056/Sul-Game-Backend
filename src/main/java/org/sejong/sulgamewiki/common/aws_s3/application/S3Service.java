package org.sejong.sulgamewiki.common.aws_s3.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.entity.constants.BasePostSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String uploadFile(MultipartFile file, BasePostSource source)  {
    // 파일 이름 생성
    String fileName = generateFileName(file, source.name());

    try {
      amazonS3.putObject(
          new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
              .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return amazonS3.getUrl(bucket, fileName).toString();
  }

  // List<MulipartFile>
  public List<String> uploadFiles(List<MultipartFile> files, BasePostSource source) {
    List<String> fileUrls = new ArrayList<>();
    for (MultipartFile file : files) {
      fileUrls.add(uploadFile(file, source));
    }
    return fileUrls;
  }

  // delete
  public void deleteFile(String fileUrl) throws IOException {
    amazonS3.deleteObject(bucket, fileUrl);
  }

  // deleteFiles List<String>
  public void deleteFiles(List<String> fileUrls) throws IOException{
    for (String fileUrl : fileUrls) {
      deleteFile(fileUrl);
    }
  }

  // 파일 이름 생성 메소드
  private String generateFileName(MultipartFile file, String category) {
    if (category == null || category.isEmpty()) {
      throw new IllegalArgumentException("Category must not be null or empty");
    }
    return category.toLowerCase() + "-" + UUID.randomUUID() + "-" + file.getOriginalFilename();
  }

  // URL에서 파일 이름 추출 메소드
  private String extractFileNameFromUrl(String fileUrl) {
    int lastSlashIndex = fileUrl.lastIndexOf('/');
    if (lastSlashIndex == -1) {
      throw new IllegalArgumentException("Invalid file URL");
    }
    return fileUrl.substring(lastSlashIndex + 1);
  }

}