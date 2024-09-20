package org.sejong.sulgamewiki.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.constants.SourceType;
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

  public String uploadFile(MultipartFile file, SourceType source)
      throws IOException {
    // 파일 이름 생성
    String fileName = generateFileName(file, source.name());

    // Content-Type 설정을 위한 ObjectMetadata 생성
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());  // MultipartFile에서 Content-Type을 가져옴

    amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));

    return amazonS3.getUrl(bucket, fileName).toString();
  }

  // delete
  public void deleteFile(String fileUrl) {
    try {
      // URL 디코딩
      String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.name());
      String fileName = extractFileNameFromUrl(decodedUrl);
      amazonS3.deleteObject(bucket, fileName);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      // 예외 처리 로직 추가
    }
  }

  // deleteFiles List<String>
  public void deleteFiles(List<String> fileUrls){
    for (String fileUrl : fileUrls) {
      deleteFile(fileUrl);
    }
  }

  public String uploadFileByCustomName(String customFileName, MultipartFile file)
      throws IOException {
    // Content-Type 설정을 위한 ObjectMetadata 생성
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());  // MultipartFile에서 Content-Type을 가져옴

    amazonS3.putObject(new PutObjectRequest(bucket, customFileName, file.getInputStream(), metadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3.getUrl(bucket, customFileName).toString();
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