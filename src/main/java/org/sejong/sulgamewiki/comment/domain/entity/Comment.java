package org.sejong.sulgamewiki.comment.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sejong.sulgamewiki.common.entity.BaseComment;
import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment extends BaseComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private PopularGame game;
}
