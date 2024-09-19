package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByMemberMemberId(Long memberId, Pageable pageable);
    void deleteByMember(Member member);
}