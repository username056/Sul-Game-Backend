package org.sejong.sulgamewiki.object.constants;

import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public enum MediaType {
  IMAGE_PNG("image/png"),
  IMAGE_JPEG("image/jpeg"),
  IMAGE_JPG("image/jpg"),
  VIDEO_MP4("video/mp4"),
  VIDEO_AVI("video/x-msvideo"),
  VIDEO_MOV("video/quicktime"),
  AUDIO_MP3("audio/mpeg"),
  AUDIO_WAV("audio/wav"),
  AUDIO_AAC("audio/aac");

  private final String mimeType;

  MediaType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getMimeType() {
    return mimeType;
  }

  public static MediaType fromMimeType(String mimeType) {
    for (MediaType type : values()) {
      if (type.getMimeType().equals(mimeType)) {
        return type;
      }
    }
    log.error("INVALID MIME TYPE: {}", mimeType);
    throw new CustomException(ErrorCode.INVALID_MEDIA_TYPE);
  }

  public static MediaType getMediaType(MultipartFile file) {
    return MediaType.fromMimeType(file.getContentType());
  }

  /**
   * 파일 이름에서 확장자를 추출하는 메서드.
   *
   * @param fileName 파일 이름 (예: "example.png")
   * @return 파일 확장자 (예: ".png"), 확장자가 없으면 빈 문자열 반환
   */
  public static String getFileExtension(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return "";
    }

    int lastDotIndex = fileName.lastIndexOf(".");
    if (lastDotIndex == -1) {
      return ""; // 확장자가 없는 경우
    }

    return fileName.substring(lastDotIndex); // 확장자 반환 (예: ".png")
  }

}



