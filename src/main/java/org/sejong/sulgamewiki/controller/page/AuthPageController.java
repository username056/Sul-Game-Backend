package org.sejong.sulgamewiki.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthPageController {

  @GetMapping("/oauth-login")
  public String loginPage(Model model) {
    return "login"; // This will point to login.html
  }
}
