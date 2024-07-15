package org.sejong.sulgamewiki.comment.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.domain.repository.CommentRepository;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.sejong.sulgamewiki.game.popular.domain.repository.PopularGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
}
