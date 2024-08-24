package org.sejong.sulgamewiki.controller;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.service.HomeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

  // 임시로 작성한 서비스 (실제 서비스에서 구현되어야 함)
  private final HomeService homeService;

  @GetMapping
  public ResponseEntity<HomeDto> getHomeData(Pageable pageable) {
    // HomeDto를 반환하는 서비스 로직 호출
    HomeDto homeData = homeService.getHomeData(pageable);
    return ResponseEntity.ok(homeData);
  }
}
