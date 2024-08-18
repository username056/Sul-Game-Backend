package org.sejong.sulgamewiki.service;

import org.sejong.sulgamewiki.object.Intro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroRepository extends JpaRepository<Intro, Long> {
}
