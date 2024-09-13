package org.sejong.sulgamewiki.service;

import static org.sejong.sulgamewiki.object.constants.SourceType.CREATION_GAME;
import static org.sejong.sulgamewiki.object.constants.SourceType.OFFICIAL_GAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BaseMedia;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.object.BasePostCommand;
import org.sejong.sulgamewiki.object.CreationGame;
import org.sejong.sulgamewiki.object.OfficialGame;
import org.sejong.sulgamewiki.object.constants.MediaType;
import org.sejong.sulgamewiki.object.constants.PostMediaType;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.sejong.sulgamewiki.repository.BaseMediaRepository;
import org.sejong.sulgamewiki.repository.BasePostRepository;
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
  private final BasePostRepository basePostRepository;

  /**
   * 미디어 파일 하나를 업로드하고 저장하는 메서드.
   * <p>
   * 이 메서드는 주어진 BasePostCommand 객체로부터 MultipartFile 을 받아, 파일을 S3에 업로드한 후, 업로드된
   * 파일의 URL을 기반으로 BaseMedia 객체를 생성하여 저장소에 저장합니다.
   *
   * @param command 업로드할 파일과 관련된 명령 객체
   * @return 업로드된 BaseMedia 객체
   */
  public BaseMedia uploadIntroMediaFromGame(BasePostCommand command) {

    MultipartFile file = command.getIntroMediaFileInGamePost();

    // 파일이 없으면 null 반환
    if (file == null || file.isEmpty()) {
      log.info("파일이 없습니다 BasePostId : {}", command.getBasePostId());
      return null;
    }

    // 포스트미디어타입 찾기
    PostMediaType postMediaType = getPostMediaType(command);

    String fileUrl = null;
    try {
      // 파일을 S3에 업로드하고 URL 반환
      fileUrl = s3Service.uploadFile(file,
          command.getBasePost().getSourceType());
    } catch (IOException e) {
      throw new CustomException(ErrorCode.S3_UPLOAD_FILE_ERROR);
    }

    // BaseMedia 객체 생성 및 저장
    BaseMedia savedMedia = baseMediaRepository.save(
        BaseMedia.builder()
            .mediaUrl(fileUrl)
            .fileSize(file.getSize())
            .mediaType(MediaType.getMediaType(file))
            .basePost(command.getBasePost())
            .postMediaType(postMediaType)
            .build()
    );
    // url 추가 로직 필요
    command.setIntroMediaFileInGamePostUrl(savedMedia.getMediaUrl());
    setIntroMediaFileInGamePostUrl(savedMedia, postMediaType);

    return savedMedia;
  }


  public BaseMedia updateIntroMediaFromGame(BasePostCommand command) {

    // 기존 인트로 미디어 파일 URL 가져오기
    String existingIntroMediaUrl = command.getIntroMediaFileInGamePostUrl();

    // 새로 들어온 파일 가져오기
    MultipartFile newIntroFile = command.getIntroMediaFileInGamePost();

    // 기존 파일 삭제
    if (existingIntroMediaUrl != null && !existingIntroMediaUrl.isEmpty()) {
      log.info("기존 인트로 미디어 URL: {}", existingIntroMediaUrl);  // 기존 URL 로그 출력

      BaseMedia existingIntroMedia = baseMediaRepository.findByMediaUrl(existingIntroMediaUrl);
      if (existingIntroMedia != null) {
        log.info("기존 인트로 미디어를 찾았습니다. 미디어 삭제를 시작합니다. BasePostId: {}", command.getBasePostId());
        // 기존 파일 S3에서 삭제
        s3Service.deleteFile(existingIntroMediaUrl);
        // 데이터베이스에서 기존 미디어 엔티티 삭제
        baseMediaRepository.delete(existingIntroMedia);
        log.info("기존 미디어 삭제 완료. BasePostId: {}", command.getBasePostId());

        // 미디어 삭제 후 업데이트 로직
        updateIntroMediaUrlInGame(command.getBasePost().getSourceType(), command.getBasePostId(), null);
      } else {
        log.warn("기존 인트로 미디어를 찾을 수 없습니다. URL: {}", existingIntroMediaUrl);
      }
    } else {
      log.info("기존 인트로 미디어 URL이 존재하지 않거나 비어있습니다.");
    }

    // 새 파일이 없으면 null 반환
    if (newIntroFile == null || newIntroFile.isEmpty()) {
      log.info("새로운 인트로 파일이 없습니다. BasePostId: {}", command.getBasePostId());
      return null;
    }
// 새로운 파일 업로드 및 저장
    // 이걸 같이 두면 안되지
    String newIntroFileUrl = null;
    try {
      newIntroFileUrl = s3Service.uploadFile(newIntroFile, command.getBasePost().getSourceType());
    } catch (IOException e) {
      throw new CustomException(ErrorCode.S3_UPLOAD_FILE_ERROR);
    }

    // 새로운 BaseMedia 엔티티 저장
    BaseMedia savedIntroMedia = baseMediaRepository.save(
        BaseMedia.builder()
            .mediaUrl(newIntroFileUrl)
            .fileSize(newIntroFile.getSize())
            .mediaType(MediaType.getMediaType(newIntroFile))
            .basePost(command.getBasePost())
            .build()
    );

    updateIntroMediaUrlInGame(
        command.getBasePost().getSourceType(), command.getBasePostId(), savedIntroMedia.getMediaUrl());

    return savedIntroMedia;

  }


  private void updateIntroMediaUrlInGame(SourceType sourceType, Long postId, String mediaFileUrl) {
    log.info("Task started with mediaFileUrl: {}", mediaFileUrl);
    BasePost gameEntity = basePostRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.GAME_NOT_FOUND));

    // SourceType에 따른 캐스팅 및 introMediaFileInGamePostUrl 업데이트 처리
    if (sourceType == SourceType.OFFICIAL_GAME && gameEntity instanceof OfficialGame) {
      if (mediaFileUrl == null) {
        // null이면 URL 제거 처리
        ((OfficialGame) gameEntity).setIntroMediaFileInGamePostUrl(null);
        log.info(" 삭제성공");
      } else {
        // 새로운 URL로 업데이트
        ((OfficialGame) gameEntity).setIntroMediaFileInGamePostUrl(mediaFileUrl);
      }
    } else if (sourceType == SourceType.CREATION_GAME && gameEntity instanceof CreationGame) {
      if (mediaFileUrl == null) {
        ((CreationGame) gameEntity).setIntroMediaFileInGamePostUrl(null);
      } else {
        ((CreationGame) gameEntity).setIntroMediaFileInGamePostUrl(mediaFileUrl);
      }
    } else {
      throw new CustomException(ErrorCode.INVALID_SOURCE_TYPE);
    }

    // 게임 엔티티 저장
    basePostRepository.save(gameEntity);
  }


  /**
   * 미디어 파일들을 업로드하고 저장하는 메서드.
   * <p>
   * 이 메서드는 주어진 BasePostCommand 객체로부터 MultipartFile 리스트를 받아, 각각의 파일을 S3에 업로드한 후,
   * 업로드된 파일들의 URL을 기반으로 BaseMedia 객체를 생성하여 저장소에 저장합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업로드된 BaseMedia 객체들의 리스트
   */
//  public List<BaseMedia> uploadMedias(BasePostCommand command) {
//    List<BaseMedia> baseMedias = new ArrayList<>();
//    List<String> uploadedFiles = new ArrayList<>();
//
//    if (command.getBasePost().getSourceType().equals(SourceType.INTRO)) {
//      if (command.getIntroMultipartFiles() == null) {
//        log.info("파일이 없습니다 BasePostId : {} ", command.getBasePostId());
//        return null;
//      }
//    } else {
//      if (command.getGameMultipartFiles() == null) {
//        log.info("파일이 없습니다 BasePostId : {} ", command.getBasePostId());
//        return null;
//      }
//    }
//
//    // TODO
//    if (command.getBasePost().getSourceType().equals(SourceType.INTRO)) {
//      for (MultipartFile file : command.getIntroMultipartFiles()) {
//        String fileUrl = null;
//        try {
//          fileUrl = s3Service.uploadFile(file,
//              command.getBasePost().getSourceType());
//        } catch (IOException e) {
//          throw new CustomException(
//              ErrorCode.S3_UPLOAD_FILE_ERROR); //TODO: change error
//        }
//        uploadedFiles.add(fileUrl);
//        baseMedias.add(baseMediaRepository.save(
//            BaseMedia.builder()
//                .mediaUrl(fileUrl)
//                .fileSize(file.getSize())
//                .mediaType(MediaType.getMediaType(file))
//                .basePost(command.getBasePost())
//                .build()));
//      }
//    } else {
//      for (MultipartFile file : command.getGameMultipartFiles()) {
//        String fileUrl = null;
//        try {
//          fileUrl = s3Service.uploadFile(file,
//              command.getBasePost().getSourceType());
//        } catch (IOException e) {
//          throw new CustomException(
//              ErrorCode.S3_UPLOAD_FILE_ERROR); //TODO: change error
//        }
//        uploadedFiles.add(fileUrl);
//        baseMedias.add(baseMediaRepository.save(
//            BaseMedia.builder()
//                .mediaUrl(fileUrl)
//                .fileSize(file.getSize())
//                .mediaType(MediaType.getMediaType(file))
//                .basePost(command.getBasePost())
//                .build()));
//      }
//    }
//    return baseMedias;
//  }

  /**
   * 공식게임 및 창작게임에서 미디어 파일들을 업로드하고 저장하는 메서드.
   * <p>
   * 이 메서드는 주어진 OfficialGame, CreationGame 객체로부터 MultipartFile 리스트를 받아, 각각의 파일을 S3에 업로드한 후,
   * 업로드된 파일들의 URL을 기반으로 BaseMedia 객체를 생성하여 저장소에 저장합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업로드된 BaseMedia 객체들의 리스트
   */
  public List<BaseMedia> uploadMediasFromGame(BasePostCommand command) {

    List<BaseMedia> baseMedias = new ArrayList<>();
    List<String> uploadedFiles = new ArrayList<>();

    if (command.getGameMultipartFiles() == null) {
      log.info("미디어파일이 없습니다 BasePostId : {} ", command.getBasePostId());
      return null;
    }

    for (MultipartFile file : command.getGameMultipartFiles()) {
      String fileUrl = null;
      try {
        fileUrl = s3Service.uploadFile(file,
            command.getBasePost().getSourceType());
      } catch (IOException e) {
        throw new CustomException(
            ErrorCode.S3_UPLOAD_FILE_ERROR);
      }
      uploadedFiles.add(fileUrl);
      baseMedias.add(baseMediaRepository.save(
          BaseMedia.builder()
              .mediaUrl(fileUrl)
              .fileSize(file.getSize())
              .mediaType(MediaType.getMediaType(file))
              .basePost(command.getBasePost())
              .postMediaType(PostMediaType.POST_MEDIA)
              .build()));
    }
    return baseMedias;
  }


  /**
   * 인트로에서 미디어 파일들을 업로드하고 저장하는 메서드.
   * <p>
   * 이 메서드는 주어진 Intro 객체로부터 MultipartFile 리스트를 받아, 각각의 파일을 S3에 업로드한 후,
   * 업로드된 파일들의 URL을 기반으로 BaseMedia 객체를 생성하여 저장소에 저장합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업로드된 BaseMedia 객체들의 리스트
   */
  public List<BaseMedia> uploadMediasFromIntro(BasePostCommand command) {
    List<BaseMedia> baseMedias = new ArrayList<>();
    List<String> uploadedFiles = new ArrayList<>();

    if (command.getIntroMultipartFiles() == null) {
      log.info("미디어파일이 없습니다 BasePostId : {} ", command.getBasePostId());
      return null;
    }

    for (MultipartFile file : command.getIntroMultipartFiles()) {
      String fileUrl = null;
      try {
        fileUrl = s3Service.uploadFile(file,
            command.getBasePost().getSourceType());
      } catch (IOException e) {
        throw new CustomException(
            ErrorCode.S3_UPLOAD_FILE_ERROR);
      }
      uploadedFiles.add(fileUrl);
      baseMedias.add(baseMediaRepository.save(
          BaseMedia.builder()
              .mediaUrl(fileUrl)
              .fileSize(file.getSize())
              .mediaType(MediaType.getMediaType(file))
              .basePost(command.getBasePost())
              .postMediaType(PostMediaType.POST_MEDIA)
              .build()));
    }
    return baseMedias;
  }

  /**
   * 기존 미디어 URL 리스트와 새로 받은 파일들을 비교하여 업데이트하는 메서드.
   * <p>
   * 이 메서드는 새로운 파일들을 업로드하고, 기존 미디어 파일 중 사용되지 않는 파일을 S3에서 삭제한 후, BaseMedia 엔티티들을
   * 반환합니다.
   *
   * @param command 업로드할 파일들과 관련된 명령 객체
   * @return 업데이트된 BaseMedia 엔티티 리스트
   */

  // TODO: 수정하기
  public List<BaseMedia> updateMedias(BasePostCommand command) {
    List<BaseMedia> updatedMediaEntities = new ArrayList<>();
    Long basePostId = command.getBasePostId();

    List<String> existingUrls = baseMediaRepository.findMediaUrlsByBasePostId(basePostId);
    PostMediaType postMediaType = getPostMediaType(command);

    Set<String> existingUrlSet = new HashSet<>(
        existingUrls != null ? existingUrls : Collections.emptyList());
    Set<String> updatedUrlSet = new HashSet<>(
        command.getImageUrls() != null ? command.getImageUrls() : Collections.emptyList());

    existingUrlSet.removeAll(updatedUrlSet);

    for (String url : existingUrlSet) {
      BaseMedia mediaToDelete = baseMediaRepository.findByMediaUrl(url);
      if (mediaToDelete == null) {
        continue;
      }

      s3Service.deleteFile(url);

      if (mediaToDelete.getPostMediaType().equals(postMediaType)) {
        if (postMediaType.equals(PostMediaType.INTRO_IN_OFFICIAL_GAME_MEDIA)) {
          OfficialGame officialGame = basePostRepository
              .findOfficialGameByBasePostId(mediaToDelete.getBasePost().getBasePostId())
              .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
          officialGame.setIntroMediaFileInGamePostUrl(null);
        } else if (postMediaType.equals(PostMediaType.INTRO_IN_CREATION_GAME_MEDIA)) {
          CreationGame creationGame = basePostRepository
              .findCreationGameByBasePostId(mediaToDelete.getBasePost().getBasePostId())
              .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
          creationGame.setIntroMediaFileInGamePostUrl(null);
        }
      }

      baseMediaRepository.delete(mediaToDelete);
    }

    // 추가
    BaseMedia introMediaFromGame = uploadIntroMediaFromGame(command);

    // 새로 들어온 multipart 파일 업로드 및 BaseMedia 엔티티 생성
    List<MultipartFile> newFiles =
        command.getBasePost().getSourceType().equals(SourceType.INTRO)
            ? command.getIntroMultipartFiles()
            : command.getGameMultipartFiles();

    if (newFiles != null && !newFiles.isEmpty()) {
      for (MultipartFile newFile : newFiles) {
        try {
          String fileUrl = s3Service.uploadFile(newFile, command.getBasePost().getSourceType());
          updatedMediaEntities.add(baseMediaRepository.save(BaseMedia.builder()
              .mediaUrl(fileUrl)
              .fileSize(newFile.getSize())
              .mediaType(MediaType.getMediaType(newFile))
              .basePost(command.getBasePost())
              .postMediaType(PostMediaType.POST_MEDIA)
              .build()));
        } catch (IOException e) {
          throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
      }
    }

    updatedMediaEntities.addAll(
        baseMediaRepository.findByBasePost_BasePostId(basePostId));

    return updatedMediaEntities;
  }

  private void setIntroMediaFileInGamePostUrl(BaseMedia baseMedia, PostMediaType postMediaType) {
    Long basePostId = baseMedia.getBasePost().getBasePostId();

    if (postMediaType.equals(PostMediaType.INTRO_IN_OFFICIAL_GAME_MEDIA)) {
      OfficialGame officialGame = basePostRepository
          .findOfficialGameByBasePostId(basePostId)
          .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
      officialGame.setIntroMediaFileInGamePostUrl(baseMedia.getMediaUrl());
    } else if (postMediaType.equals(PostMediaType.INTRO_IN_CREATION_GAME_MEDIA)) {
      CreationGame creationGame = basePostRepository
          .findCreationGameByBasePostId(basePostId)
          .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
      creationGame.setIntroMediaFileInGamePostUrl(baseMedia.getMediaUrl());
    } else if (postMediaType == null) {
      return;
    } else {
      throw new CustomException(ErrorCode.INVALID_GAME_INTRO_MEDIA_TYPE);
    }
  }

  private PostMediaType getPostMediaType(BasePostCommand command) {
    if (command.getBasePost().getSourceType().equals(OFFICIAL_GAME)) {
      return PostMediaType.INTRO_IN_OFFICIAL_GAME_MEDIA;
    } else if (command.getSourceType().equals(CREATION_GAME)) {
      return PostMediaType.INTRO_IN_CREATION_GAME_MEDIA;
    } else {
      return null;
    }
  }


  /**
   * 회원 프로필 이미지를 업로드하는 메서드.
   * <p>
   * 이 메서드는 MultipartFile로 받은 회원 프로필 이미지를 특정 형식의 파일 이름으로 S3에 업로드합니다. 업로드된 파일의
   * URL을 반환합니다.
   *
   * @param file     업로드할 프로필 이미지 파일
   * @param memberId 회원의 고유 ID
   * @return 업로드된 파일의 S3 URL
   * @throws CustomException S3 업로드 중 문제가 발생한 경우 예외를 발생시킵니다.
   */
  public String uploadMemberProfileImage(MultipartFile file, Long memberId) {
    String memberProfileImageUrl = null;
    String memberProfileImageFileName
        = SourceType.MEMBER.name().toLowerCase() + memberId
        + MediaType.getFileExtension(file.getOriginalFilename());
    try {
      memberProfileImageUrl = s3Service.uploadFileByCustomName(
          memberProfileImageFileName, file);
    } catch (IOException e) {
      throw new CustomException(ErrorCode.S3_UPLOAD_FILE_ERROR);
    }
    System.out.println("저장된 회원 프로필 파일 이름: " + memberProfileImageUrl);
    return memberProfileImageUrl;
  }

  /**
   * 기존 미디어 파일을 새로운 파일로 업데이트하는 메서드.
   * <p>
   * 이 메서드는 주어진 MultipartFile을 사용하여 S3에 있는 기존 파일을 동일한 파일 이름으로 덮어씁니다. 업데이트된 파일의
   * URL을 BaseMedia 객체에 반영하고, 해당 객체를 저장소에 저장합니다.
   *
   * @param multipartFile 업데이트할 새로운 파일
   * @param baseMedia     기존 미디어 파일 정보가 담긴 BaseMedia 객체
   * @return 업데이트된 BaseMedia 객체
   * @throws CustomException S3 업로드 중 문제가 발생한 경우 예외를 발생시킵니다.
   */
  public BaseMedia updateMediaByBaseMedia(MultipartFile
      multipartFile, BaseMedia baseMedia) {
    // 기존 파일 URL 가져오기
    String existingUrl = baseMedia.getMediaUrl();
    String existFileName = existingUrl.substring(
        existingUrl.lastIndexOf("/") + 1);
    // 새로운 파일을 동일한 키로 업로드하여 덮어쓰기
    try {
      // 기존과 같은 URL이여야 정상입니다
      String updatedMediaUrl = s3Service.uploadFileByCustomName(existFileName,
          multipartFile);
      baseMedia.setMediaUrl(updatedMediaUrl);
      return baseMediaRepository.save(baseMedia);
    } catch (IOException e) {
      throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  public void deleteMedia(BaseMedia baseMedia) {
    // S3에서 파일 삭제
    s3Service.deleteFile(baseMedia.getMediaUrl());

    // 데이터베이스에서 BaseMedia 삭제
    baseMediaRepository.delete(baseMedia);
  }

  public void deleteMedias(List<BaseMedia> baseMediaList) {
    for (BaseMedia baseMedia : baseMediaList) {
      deleteMedia(baseMedia); // 개별 삭제 메서드 호출
    }
  }

}
