package org.sejong.sulgamewiki.fss;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sejong.sulgamewiki.object.Member;

@Getter
@Setter
@Builder
@ToString
public class FCMCommand {
  private String topic;
  private String token;

  private Member member;
  private String title;
  private String body;
  private String image;
  private String url;
}
