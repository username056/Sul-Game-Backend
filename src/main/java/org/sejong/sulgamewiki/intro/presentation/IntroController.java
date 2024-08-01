package org.sejong.sulgamewiki.intro.presentation;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.docs.IntroControllerDocs;
import org.sejong.sulgamewiki.common.utils.annotations.FilesParameter;
import org.sejong.sulgamewiki.intro.application.IntroService;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class IntroController implements IntroControllerDocs {

  private final IntroService introService;

  @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CreateIntroResponse> createIntro(
      @RequestParam Long memberId,
      @ModelAttribute CreateIntroRequest request,
      @RequestParam("multipartFiles") List<MultipartFile> files
//            @RequestBody CreateIntroRequest request
//            @ModelAttribute MultipartFile multipartFile

  ) {
    CreateIntroResponse introResponse = introService.createIntro(memberId,
        request, files);
    return ResponseEntity.ok(introResponse);
  }

}
