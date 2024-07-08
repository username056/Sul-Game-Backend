package org.sejong.sulgamewiki.comment.application;

import org.sejong.sulgamewiki.comment.domain.entity.Comment;
import org.sejong.sulgamewiki.comment.domain.repository.CommentRepository;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.sejong.sulgamewiki.game.popular.domain.repository.PopularGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PopularGameRepository popularGameRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PopularGameRepository popularGameRepository) {
        this.commentRepository = commentRepository;
        this.popularGameRepository = popularGameRepository;
    }

    public List<Comment> getCommentsByGameId(Long gameId) {
        PopularGame game = popularGameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        return game.getComments();
    }

    public Comment addComment(Long gameId, Comment comment) {
        PopularGame game = popularGameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        comment.setGame(game);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
