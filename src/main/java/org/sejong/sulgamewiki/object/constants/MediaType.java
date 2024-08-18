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

}



