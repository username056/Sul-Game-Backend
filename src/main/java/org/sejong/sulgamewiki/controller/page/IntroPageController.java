//package org.sejong.sulgamewiki.controller.page;
//
//import lombok.RequiredArgsConstructor;
//import org.sejong.sulgamewiki.object.BasePostCommand;
//import org.sejong.sulgamewiki.service.CreationGameService;
//import org.sejong.sulgamewiki.service.IntroService;
//import org.sejong.sulgamewiki.service.OfficialGameService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class IntroPageController {
//
//  private final OfficialGameService officialGameService;
//  private final CreationGameService creationGameService;
//  private final IntroService introService;
//
//  @GetMapping("/create-official-game")
//  public String createOfficialGame(Model model) {
//
//    BasePostCommand command = BasePostCommand.builder().build();
//
//    model.addAttribute("BasePostDto", officialGameService.createOfficialGame(command));
//    return "createOfficialGame"; // templates/createOfficialGame.html 에 있습니다.
//  }
//
//  @GetMapping("/create-creation-game")
//  public String createCreationGame(Model model) {
//
//    BasePostCommand command = BasePostCommand.builder().build();
//
//    model.addAttribute("BasePostDto", creationGameService.createCreationGame(command));
//    return "createCreationGame"; // templates/createCreationGame.html 에 있습니다.
//  }
//
//  @GetMapping("/create-intro")
//  public String createIntro(Model model) {
//
//    BasePostCommand command = BasePostCommand.builder().build();
//
//    model.addAttribute("BasePostDto", introService.createIntro(command));
//    return "createIntro"; // templates/createIntro.html 에 있습니다.
//  }
//}