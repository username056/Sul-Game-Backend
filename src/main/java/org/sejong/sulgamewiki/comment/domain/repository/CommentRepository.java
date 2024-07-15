package org.sejong.sulgamewiki.comment.domain.repository;

import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
