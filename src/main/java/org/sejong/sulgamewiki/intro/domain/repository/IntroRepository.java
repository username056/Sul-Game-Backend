package org.sejong.sulgamewiki.intro.domain.repository;

import org.sejong.sulgamewiki.intro.domain.entity.Intro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroRepository extends JpaRepository<Intro, Long> {
}
