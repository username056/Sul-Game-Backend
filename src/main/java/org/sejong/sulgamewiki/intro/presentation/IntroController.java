package org.sejong.sulgamewiki.intro.presentation;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.common.docs.IntroControllerDocs;
import org.sejong.sulgamewiki.common.utils.annotations.FilesParameter;
import org.sejong.sulgamewiki.intro.application.IntroService;
import org.sejong.sulgamewiki.intro.dto.request.CreateIntroRequest;
import org.sejong.sulgamewiki.intro.dto.response.CreateIntroResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class IntroController implements IntroControllerDocs {

    private final IntroService introService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateIntroResponse> createIntro(
            @RequestParam Long memberId,
            @FilesParameter CreateIntroRequest request
    ) {
        CreateIntroResponse introResponse = introService.createIntro(memberId, request);
        return ResponseEntity.ok(introResponse);
    }
}
