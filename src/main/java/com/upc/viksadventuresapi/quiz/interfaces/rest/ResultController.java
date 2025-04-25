package com.upc.viksadventuresapi.quiz.interfaces.rest;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResultByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllResultsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByProfileIdAndQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultsByProfileIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.ResultQueryService;
import com.upc.viksadventuresapi.quiz.domain.services.ResultCommandService;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.ResultResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.ResultResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/results")
@Tag(name = "Results", description = "Result Management Endpoints")
public class ResultController {
    private final ResultQueryService resultQueryService;
    private final ResultCommandService resultCommandService;

    public ResultController(ResultQueryService resultQueryService, ResultCommandService resultCommandService) {
        this.resultQueryService = resultQueryService;
        this.resultCommandService = resultCommandService;
    }

    // Get Result by ID
    @GetMapping("/{resultId}")
    public ResponseEntity<ResultResource> getResultById(@PathVariable Long resultId) {
        Optional<Result> result = resultQueryService.query(new GetResultByIdQuery(resultId));
        return result.map(value -> ResponseEntity.ok(ResultResourceFromEntityAssembler.toResourceFromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all Results
    @GetMapping
    public ResponseEntity<List<ResultResource>> getAllResults() {
        List<Result> results = resultQueryService.query(new GetAllResultsQuery());
        List<ResultResource> resultResources = results.stream()
                .map(ResultResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultResources);
    }

    // Get Results by Profile ID
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<ResultResource>> getResultsByProfile(@PathVariable Long profileId) {
        Optional<Result> result = resultQueryService.handle(new GetResultsByProfileIdQuery(profileId));
        return result.map(value -> ResponseEntity.ok(List.of(ResultResourceFromEntityAssembler.toResourceFromEntity(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Result by Profile ID and Quiz ID
    @GetMapping("/profile/{profileId}/quiz/{quizId}")
    public ResponseEntity<ResultResource> getResultByProfileAndQuiz(@PathVariable Long profileId, @PathVariable Long quizId) {
        Optional<Result> result = resultQueryService.handle(new GetResultByProfileIdAndQuizIdQuery(profileId, quizId));
        return result.map(value -> ResponseEntity.ok(ResultResourceFromEntityAssembler.toResourceFromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Result by ID
    @DeleteMapping("/{resultId}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long resultId) {
        resultCommandService.handle(new DeleteResultByIdCommand(resultId));
        return ResponseEntity.noContent().build();
    }
}
