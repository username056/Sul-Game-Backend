package org.sejong.sulgamewiki.common.auth.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

  private final Member member;
  private Map<String, Object> attributes;

  public CustomUserDetails(Member member) {
    this.member = member;
  }

  public CustomUserDetails(Member member, Map<String, Object> attributes) {
    this.member = member;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(() -> member.getRole().name());
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return member.getStatus() != MemberStatus.DELETED;
  }

  @Override
  public boolean isAccountNonLocked() {
    return member.getStatus() != MemberStatus.INACTIVE;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return member.getStatus() == MemberStatus.ACTIVE;
  }

  @Override
  public String getName() {
    return member.getEmail();
  }
}
