package com.upc.viksadventuresapi.quiz.interfaces.rest;

import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuestionByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuestionsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionsByQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.QuestionCommandService;
import com.upc.viksadventuresapi.quiz.domain.services.QuestionQueryService;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateQuestionResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.QuestionResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.CreateQuestionCommandFromResourceAssembler;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.QuestionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/questions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Questions", description = "Question Management Endpoints")
public class QuestionController {
    private final QuestionQueryService questionQueryService;
    private final QuestionCommandService questionCommandService;

    public QuestionController(QuestionQueryService questionQueryService, QuestionCommandService questionCommandService) {
        this.questionQueryService = questionQueryService;
        this.questionCommandService = questionCommandService;
    }

    @PostMapping
    public ResponseEntity<QuestionResource> createQuestion(@RequestBody CreateQuestionResource resource) {
        var createQuestionCommand = CreateQuestionCommandFromResourceAssembler.toCommandFromResource(resource);
        var question = questionCommandService.handle(createQuestionCommand);
        if (question.isEmpty()) return ResponseEntity.badRequest().build();
        var quizResource = QuestionResourceFromEntityAssembler.toResourceFromEntity(question.get());
        return new ResponseEntity<>(quizResource, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResource> getQuestion(@PathVariable Long questionId) {
        var getQuestionByIdQuery = new GetQuestionByIdQuery(questionId);
        var question = questionQueryService.handle(getQuestionByIdQuery);
        if (question.isEmpty()) return ResponseEntity.badRequest().build();
        var questionResource = QuestionResourceFromEntityAssembler.toResourceFromEntity(question.get());
        return ResponseEntity.ok(questionResource);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResource>> getAllQuestions(){
        var getAllQuestionsQuery = new GetAllQuestionsQuery();
        var questions = questionQueryService.handle(getAllQuestionsQuery);
        var questionResources = questions.stream().map(QuestionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(questionResources);

    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResource>> getQuestionsByQuiz(@PathVariable Long quizId) {
        var getQuestionsByQuizIdQuery = new GetQuestionsByQuizIdQuery(quizId);
        var questions = questionQueryService.handle(getQuestionsByQuizIdQuery);
        var questionResources = questions.stream().map(QuestionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(questionResources);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        var deleteQuestionByIdCommand = new DeleteQuestionByIdCommand(questionId);
        questionCommandService.handle(deleteQuestionByIdCommand);
        return ResponseEntity.noContent().build();
    }
}
