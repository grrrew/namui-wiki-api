package com.dnd.namuiwiki.domain.question;

import com.dnd.namuiwiki.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> setDefaultQuestions(
            @RequestParam String pwd
    ) {
        questionService.setDefaultDocuments(pwd);
        return ResponseDto.noContent();
    }

    @GetMapping
    public ResponseEntity<?> getQuestions() {
        var response = questionService.getQuestions();
        return ResponseDto.ok(response);
    }

}
