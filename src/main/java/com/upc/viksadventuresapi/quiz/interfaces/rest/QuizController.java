package com.upc.viksadventuresapi.quiz.interfaces.rest;

import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuizByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuizzesQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuizByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.QuizCommandService;
import com.upc.viksadventuresapi.quiz.domain.services.QuizQueryService;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateQuizResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.QuizResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.CreateQuizCommandFromResourceAssembler;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.QuizResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Quizzes", description = "Quiz Management Endpoints")
public class QuizController
{
    private final QuizQueryService quizQueryService;
    private final QuizCommandService quizCommandService;

    public QuizController(QuizQueryService quizQueryService, QuizCommandService quizCommandService) {
        this.quizQueryService = quizQueryService;
        this.quizCommandService = quizCommandService;
    }

    @PostMapping
    public ResponseEntity<QuizResource> createQuiz(@RequestBody CreateQuizResource resource) {
        var createQuizCommand = CreateQuizCommandFromResourceAssembler.toCommandFromResource(resource);
        var quiz = quizCommandService.handle(createQuizCommand);
        if (quiz.isEmpty()) return ResponseEntity.badRequest().build();
        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return new ResponseEntity<>(quizResource, HttpStatus.CREATED);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResource> getQuizById(@PathVariable Long quizId) {
        var getQuizByIdQuery = new GetQuizByIdQuery(quizId);
        var quiz = quizQueryService.handle(getQuizByIdQuery);
        if (quiz.isEmpty()) return ResponseEntity.badRequest().build();
        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return ResponseEntity.ok(quizResource);
    }

    @GetMapping
    public ResponseEntity<List<QuizResource>> getAllQuizzes() {
        var getAllQuizzesQuery = new GetAllQuizzesQuery();
        var quizzes = quizQueryService.handle(getAllQuizzesQuery);
        var quizResources = quizzes.stream().map(QuizResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(quizResources);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable Long quizId) {
        var deleteQuizByIdCommand = new DeleteQuizByIdCommand(quizId);
        quizCommandService.handle(deleteQuizByIdCommand);
        return ResponseEntity.noContent().build();
    }
}
