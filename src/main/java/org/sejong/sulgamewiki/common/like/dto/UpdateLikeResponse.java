package org.sejong.sulgamewiki.common.like.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateLikeResponse {
  private Long postId;
  private int likeCount;
}
