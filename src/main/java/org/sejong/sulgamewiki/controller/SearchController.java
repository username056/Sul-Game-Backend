package org.sejong.sulgamewiki.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sejong.sulgamewiki.object.MemberDto;
import org.sejong.sulgamewiki.object.SearchCommand;
import org.sejong.sulgamewiki.object.SearchDto;
import org.sejong.sulgamewiki.service.SearchService;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "검색 관리 API",
    description = "검색 관리 API 제공"
)
public class SearchController implements SearchControllerDocs{

  private final SearchService searchService;

  @PostMapping(value = "/all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  @LogMonitoringInvocation
  public ResponseEntity<SearchDto> searchAll(
      @ModelAttribute SearchCommand command) {
    return ResponseEntity.ok(searchService.searchAll(command));
  }
}
