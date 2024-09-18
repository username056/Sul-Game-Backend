package org.sejong.sulgamewiki.controller.page;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.object.HomeCommand;
import org.sejong.sulgamewiki.object.HomeDto;
import org.sejong.sulgamewiki.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomaPageController {

  private final HomeService homeService;

  @GetMapping("/")
  public String homePage(Model model) {

    HomeCommand command = HomeCommand.builder()
        .pageNumber(0)
        .pageSize(5) // 기본 5개씩
        .build();

    model.addAttribute("homeDto", homeService.getHomepage(command));
    return "index"; // templates/index.html 에 있습니다.
  }
}
