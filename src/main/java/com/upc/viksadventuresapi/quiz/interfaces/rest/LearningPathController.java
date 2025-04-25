package com.upc.viksadventuresapi.quiz.interfaces.rest;

import com.upc.viksadventuresapi.quiz.domain.services.LearningPathCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/learning-path", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Learning Path", description = "Learning Path Endpoint")
public class LearningPathController {
    private final LearningPathCommandService learningPathCommandService;

    public LearningPathController(LearningPathCommandService learningPathCommandService) {
        this.learningPathCommandService = learningPathCommandService;
    }

    @PostMapping
    public List<Integer> getLearningPath(Long profileId, Long quizId) {
        return learningPathCommandService.getLearningPath(profileId, quizId);
    }
}
