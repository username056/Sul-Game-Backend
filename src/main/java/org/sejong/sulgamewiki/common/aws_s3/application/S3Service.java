package org.sejong.sulgamewiki.common.aws_s3.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
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

  public String uploadFile(MultipartFile file, String category) throws IOException {
    // 카테고리 값이 없거나 유효하지 않은 경우 예외 처리
    if (category == null || category.isEmpty()) {
      throw new IllegalArgumentException("Category must not be null or empty");
    }

    // 카테고리를 소문자로 변환하여 파일명에 추가
    String fileName = category.toLowerCase() + "-" + UUID.randomUUID() + "-" + file.getOriginalFilename();
    amazonS3.putObject(
        new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3.getUrl(bucket, fileName).toString();
  }

}