package org.sejong.sulgamewiki.common.object;

public enum MediaType {
  IMAGE_PNG("image/png"),
  IMAGE_JPEG("image/jpeg");
  // 다른 MIME 타입 추가

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
    throw new IllegalArgumentException(
        "No enum constant for MIME type " + mimeType);
  }
}



