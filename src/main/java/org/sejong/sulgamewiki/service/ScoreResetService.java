package org.sejong.sulgamewiki.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.BasePost;
import org.sejong.sulgamewiki.repository.BasePostRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreResetService {

  private final BasePostRepository basePostRepository;

  // 매일자정마다 실행
  @Scheduled(cron = "0 0 0 1/1 * ?")
  @Async
  public void resetDailyScores() {
    log.info("일일 점수 초기화 작업 시작");
    List<BasePost> posts = basePostRepository.findAll();
    for (BasePost post : posts) {
      post.resetDailyScore();
      log.info("Post ID: {}, Daily Score 초기화 완료", post.getBasePostId());
    }
    basePostRepository.saveAll(posts);
    log.info("일일 점수 초기화 작업 완료");
  }

  // 일요일자정마다 실행 (매일 자정에 실행)
  @Scheduled(cron = "0 0 0 ? * SUN")
  @Async
  public void resetWeeklyScores() {
    log.info("주간 점수 초기화 작업 시작");
    List<BasePost> posts = basePostRepository.findAll();
    for (BasePost post : posts) {
      post.resetWeeklyScore();
      log.info("Post ID: {}, Weekly Score 초기화 완료", post.getBasePostId());
    }
    basePostRepository.saveAll(posts);
    log.info("주간 점수 초기화 작업 완료");
  }
}

