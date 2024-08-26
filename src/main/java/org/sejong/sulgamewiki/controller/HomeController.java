package org.sejong.sulgamewiki.controller;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.service.HomeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

  private final HomeService homeService;

  @GetMapping
  public ResponseEntity<HomeDto> getHomeInfo() {
    Pageable pageable = PageRequest.of(0, 5);
    // HomeDto를 반환하는 서비스 로직 호출
    return ResponseEntity.ok(homeService.getHomeInfo(HomeCommand.builder().pageable(pageable).build()));
  }
}
