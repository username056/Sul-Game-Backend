package org.sejong.sulgamewiki.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BaseMediaService {

  private final S3Service s3Service;

  // 새로 받은 미디어 파일 업로드
  public String uploadMedia(MultipartFile file, SourceType sourceType) {
    String fileUrl = s3Service.uploadFile(file, sourceType);
    if (fileUrl == null || fileUrl.isEmpty()) {
      throw new CustomException(ErrorCode.INVALID_MEDIA_TYPE);
    }
    return fileUrl;
  }

  public List<String> compareAndUpdateMedia(List<String> existingMediaUrls, List<MultipartFile> newMediaFiles, SourceType sourceType) {
    List<String> updatedMediaUrls = new ArrayList<>();

    // 새로 받은 파일들 업로드하고 URL 리스트에 추가
    for (MultipartFile file : newMediaFiles) {
      String fileUrl = uploadMedia(file, sourceType);
      updatedMediaUrls.add(fileUrl);
    }

    // 기존 미디어와 비교하여 삭제할 미디어 처리
    for (String existingUrl : existingMediaUrls) {
      if (!updatedMediaUrls.contains(existingUrl)) {
        try {
          s3Service.deleteFile(existingUrl); // S3에서 미디어 파일 삭제
        } catch (IOException e) {
          throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
      } else {
        // 기존 미디어는 그대로 유지, 추가하지 않음
        updatedMediaUrls.add(existingUrl);
      }
    }

    return updatedMediaUrls;
  }




}
