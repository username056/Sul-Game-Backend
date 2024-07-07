package org.sejong.sulgamewiki.game.popular.domain.repository;

import org.sejong.sulgamewiki.game.popular.domain.entity.PopularGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularGameRepository extends JpaRepository<PopularGame, Long> {

}
