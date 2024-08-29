package org.sejong.sulgamewiki.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
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
@Slf4j
public class BaseMediaService {

  private final S3Service s3Service;
  private final BaseMediaRepository baseMediaRepository;

  /**
   * 미디어 파일들을 업로드하고 저장하는 메서드.
   *
   * 이 메서드는 주어진 BasePostCommand 객체로부터 MultipartFile 리스트를 받아, 각각의 파일을 S3에 업로드한 후,
   * 업로드된 파일들의 URL을 기반으로 BaseMedia 객체를 생성하여 저장소에 저장합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업로드된 BaseMedia 객체들의 리스트
   */
  public List<BaseMedia> uploadMedias(BasePostCommand command) {
    List<BaseMedia> baseMedias = new ArrayList<>();
    List<String> uploadedFiles = new ArrayList<>();

    if(command.getMultipartFiles()==null){
      log.info("파일이 없습니다 BasePostId : {} ", command.getBasePostId());
      return null;
    }

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
      baseMedias.add(baseMediaRepository.save(
          BaseMedia.builder()
          .mediaUrl(fileUrl)
          .fileSize(file.getSize())
          .mediaType(MediaType.getMediaType(file))
          .basePost(command.getBasePost())
          .build()));
    }
    return baseMedias;
  }

  /**
   * 기존 미디어 URL 리스트와 새로 받은 파일들을 비교하여 업데이트하는 메서드.
   *
   * 이 메서드는 새로운 파일들을 업로드하고, 기존 미디어 파일 중 사용되지 않는 파일을 S3에서 삭제합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업데이트된 미디어 URL 리스트
   */
  public List<String> updateMedias(BasePostCommand command) {
    List<String> updatedMediaUrls = new ArrayList<>();

    // 기존 미디어와 비교하여 삭제할 미디어 처리
    for (String existingUrl : baseMediaRepository.findMediaUrlsByBasePost_BasePostId(
        command.getBasePostId())) {
      // 기존에 있던 이미지url이 업데이트 파라미터에 없으면? -> 삭제된거임
      if (!command.getImageUrls().contains(existingUrl)) {
        try {
          s3Service.deleteFile(existingUrl); // S3에서 미디어 파일 삭제
        } catch (IOException e) {
          throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
      }
    }

    // 새로 들어온 multipart파일 업로드
    for(MultipartFile newFile : command.getMultipartFiles()) {
      try {
        updatedMediaUrls.add(s3Service.uploadFile(newFile, command.getSourceType()));
      } catch (IOException e) {
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
      }
    }

    return updatedMediaUrls;
  }

  /**
   * 회원 프로필 이미지를 업로드하는 메서드.
   *
   * 이 메서드는 MultipartFile로 받은 회원 프로필 이미지를 특정 형식의 파일 이름으로 S3에 업로드합니다.
   * 업로드된 파일의 URL을 반환합니다.
   *
   * @param file 업로드할 프로필 이미지 파일
   * @param memberId 회원의 고유 ID
   * @return 업로드된 파일의 S3 URL
   * @throws CustomException S3 업로드 중 문제가 발생한 경우 예외를 발생시킵니다.
   */
  public String uploadMemberProfileImage(MultipartFile file, Long memberId) {
    String memberProfileImageUrl = null;
    String memberProfileImageFileName
        = SourceType.MEMBER.name().toLowerCase() + memberId + MediaType.getFileExtension(file.getOriginalFilename());
    try {
      memberProfileImageUrl = s3Service.uploadFileByCustomName(memberProfileImageFileName,file);
    } catch (IOException e) {
      throw new CustomException(ErrorCode.S3_UPLOAD_FILE_ERROR);
    }
    System.out.println("저장된 회원 프로필 파일 이름: " + memberProfileImageUrl);
    return memberProfileImageUrl;
  }

  /**
   * 기존 미디어 파일을 새로운 파일로 업데이트하는 메서드.
   *
   * 이 메서드는 주어진 MultipartFile을 사용하여 S3에 있는 기존 파일을 동일한 파일 이름으로 덮어씁니다.
   * 업데이트된 파일의 URL을 BaseMedia 객체에 반영하고, 해당 객체를 저장소에 저장합니다.
   *
   * @param multipartFile 업데이트할 새로운 파일
   * @param baseMedia 기존 미디어 파일 정보가 담긴 BaseMedia 객체
   * @return 업데이트된 BaseMedia 객체
   * @throws CustomException S3 업로드 중 문제가 발생한 경우 예외를 발생시킵니다.
   */
  public BaseMedia updateMediaByBaseMedia(MultipartFile multipartFile, BaseMedia baseMedia) {
    // 기존 파일 URL 가져오기
    String existingUrl = baseMedia.getMediaUrl();
    String existFileName = existingUrl.substring(existingUrl.lastIndexOf("/") + 1);
    // 새로운 파일을 동일한 키로 업로드하여 덮어쓰기
    try {
      // 기존과 같은 URL이여야 정상입니다
      String updatedMediaUrl = s3Service.uploadFileByCustomName(existFileName, multipartFile);
      baseMedia.setMediaUrl(updatedMediaUrl);
      return baseMediaRepository.save(baseMedia);
    } catch (IOException e) {
      throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }




}
