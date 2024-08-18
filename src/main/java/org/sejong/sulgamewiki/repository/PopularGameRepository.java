package org.sejong.sulgamewiki.repository;

import org.sejong.sulgamewiki.object.OfficialGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularGameRepository extends JpaRepository<OfficialGame, Long> {

}
