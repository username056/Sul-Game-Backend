//package org.sejong.sulgamewiki.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.sejong.sulgamewiki.object.BasePost;
//import org.sejong.sulgamewiki.repository.BasePostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
///*
//@SpringBootTest
//@Slf4j
//public class ScoreResetServiceTest {
//
//  @Autowired
//  private ScoreResetService scoreResetService;
//
//  @MockBean
//  private BasePostRepository basePostRepository;
//
//  @Test
//  public void testResetDailyScores() {
//    // given
//    BasePost post1 = Mockito.mock(BasePost.class);
//    BasePost post2 = Mockito.mock(BasePost.class);
//    post1.setDailyScore(10);
//    post2.setDailyScore(20);
//    List<BasePost> posts = Arrays.asList(post1, post2);
//    Mockito.when(basePostRepository.findAll()).thenReturn(posts);
//
//    // when
//    scoreResetService.resetDailyScores();
//
//    // then
//    log.info("post1: {}", post1.getDailyScore());
//    log.info("post2: {}", post2.getDailyScore());
//
//  }
//
//  @Test
//  public void testResetWeeklyScores() {
//    // Mock 데이터 준비
//    BasePost post1 = Mockito.mock(BasePost.class);
//    BasePost post2 = Mockito.mock(BasePost.class);
//    post1.setWeeklyScore(10);
//    post2.setWeeklyScore(20);
//    List<BasePost> posts = Arrays.asList(post1, post2);
//    Mockito.when(basePostRepository.findAll()).thenReturn(posts);
//
//    // 실제 메서드 호출
//    scoreResetService.resetWeeklyScores();
//
//    // verify로 메서드가 호출되었는지 확인
//    log.info("post1: {}", post1.getWeeklyScore());
//    log.info("post2: {}", post2.getWeeklyScore());
//  }
//}
// */