package com.upc.viksadventuresapi.quiz.interfaces.rest;


import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResponseByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllResponsesQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResponseByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResponsesByProfileIdAndQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResponsesByProfileIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.ResponseCommandService;
import com.upc.viksadventuresapi.quiz.domain.services.ResponseQueryService;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateResponseResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.ResponseResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.CreateResponseCommandFromResourceAssembler;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.ResponseResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/responses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Responses", description = "Response Management Endpoints")
public class ResponseController {
    private final ResponseCommandService responseCommandService;
    private final ResponseQueryService responseQueryService;

    public ResponseController(ResponseCommandService responseCommandService, ResponseQueryService responseQueryService) {
        this.responseCommandService = responseCommandService;
        this.responseQueryService = responseQueryService;
    }

    @PostMapping
    public ResponseEntity<ResponseResource> createResponse(@RequestBody CreateResponseResource resource) {
        var createResponseCommand = CreateResponseCommandFromResourceAssembler.toCommandFromResource(resource);
        var response = responseCommandService.handle(createResponseCommand);
        if (response.isEmpty()) return ResponseEntity.badRequest().build();
        var responseResource = ResponseResourceFromEntityAssembler.toResourceFromEntity(response.get());
        return new ResponseEntity<>(responseResource, HttpStatus.CREATED);
    }

    @GetMapping("/{responseId}")
    public ResponseEntity<ResponseResource> getResponse(@PathVariable Long responseId) {
        var getResponseByIdQuery = new GetResponseByIdQuery(responseId);
        var response = responseQueryService.handle(getResponseByIdQuery);
        if (response.isEmpty()) return ResponseEntity.badRequest().build();
        var responseResource = ResponseResourceFromEntityAssembler.toResourceFromEntity(response.get());
        return ResponseEntity.ok(responseResource);
    }

    @GetMapping
    public ResponseEntity<List<ResponseResource>> getAllResponses() {
        var getAllResponsesQuery = new GetAllResponsesQuery();
        var responses = responseQueryService.handle(getAllResponsesQuery);
        var responseResources = responses.stream().map(ResponseResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(responseResources);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<ResponseResource>> getResponsesByProfile(@PathVariable Long profileId) {
        var getResponsesByProfileIdQuery = new GetResponsesByProfileIdQuery(profileId);
        var responses = responseQueryService.handle(getResponsesByProfileIdQuery);
        var responseResources = responses.stream().map(ResponseResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(responseResources);
    }

    @GetMapping("/profile/{profileId}/quiz/{quizId}")
    public ResponseEntity<List<ResponseResource>> getResponsesByProfileAndQuiz(@PathVariable Long profileId, @PathVariable Long quizId) {
        var getResponsesByProfileIdAndQuizIdQuery = new GetResponsesByProfileIdAndQuizIdQuery(profileId, quizId);
        var responses = responseQueryService.handle(getResponsesByProfileIdAndQuizIdQuery);
        var responseResources = responses.stream().map(ResponseResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(responseResources);
    }

    @DeleteMapping("/{responseId}")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long responseId) {
        var deleteResponseByIdCommand = new DeleteResponseByIdCommand(responseId);
        responseCommandService.handle(deleteResponseByIdCommand);
        return ResponseEntity.noContent().build();
    }
}
