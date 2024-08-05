package org.sejong.sulgamewiki.member.dto.request;

import java.time.LocalDate;
import org.sejong.sulgamewiki.common.entity.constants.MemberRole;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;

public class UpdateMemberResponse {
  private String memberId;
  private String nickname;
  private String email;
  private LocalDate birthday;
  private String profileImageUrl;
  private MemberRole role;
  private String university;
  private Boolean isUniversityVisible;
  private MemberStatus memberStatus;
  private Boolean isNotiEnabled;
  private Boolean infoPopupVisible;
}
