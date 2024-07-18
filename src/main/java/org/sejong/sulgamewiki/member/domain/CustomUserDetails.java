package org.sejong.sulgamewiki.member.domain;

import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.member.domain.constants.MemberStatus;
import org.sejong.sulgamewiki.member.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final Member member;

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
}
