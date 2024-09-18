package org.sejong.sulgamewiki.controller.page;

import org.sejong.sulgamewiki.object.HomeCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomaPageController {

  @GetMapping("/")
  public String homePage(Model model) {
    HomeCommand command = HomeCommand.builder().build();
    model.addAttribute("command", command);
    return "index"; // templates/index.html
  }
}
