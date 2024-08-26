package org.sejong.sulgamewiki.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.constants.MediaType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.util.S3Service;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BaseMediaService {

  private final S3Service s3Service;
  private final BaseMediaRepository baseMediaRepository;

  // 새로 받은 미디어 파일 업로드 and save
  public List<BaseMedia> uploadMedias(BasePostCommand command) {
    List<BaseMedia> baseMedias = new ArrayList<>();
    List<String> uploadedFiles = new ArrayList<>();

    // TODO
    for( MultipartFile file : command.getMultipartFiles()) {
      String fileUrl = null;
      try {
        fileUrl = s3Service.uploadFile(file, command.getSourceType());
      } catch (IOException e) {
        //TODO: delete uploaded file in S3 -> uploadedFiles
        throw new RuntimeException(e); //TODO: change error
      }
      uploadedFiles.add(fileUrl);
      BaseMedia baseMedia = BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(command.getBasePost())
          .build();
      baseMedias.add(baseMediaRepository.save(baseMedia));
    }
    return baseMedias;
  }

  public List<String> compareAndUpdateMedia(List<String> existingMediaUrls, List<MultipartFile> newMediaFiles, SourceType sourceType) {
    List<String> updatedMediaUrls = new ArrayList<>();

    // 새로 받은 파일들 업로드하고 URL 리스트에 추가
    for (MultipartFile file : newMediaFiles) {
//      String fileUrl = uploadMedias(file, sourceType);
//      updatedMediaUrls.add(fileUrl);
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
