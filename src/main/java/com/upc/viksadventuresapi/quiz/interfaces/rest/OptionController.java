package com.upc.viksadventuresapi.quiz.interfaces.rest;

import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteOptionByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.*;
import com.upc.viksadventuresapi.quiz.domain.services.OptionCommandService;
import com.upc.viksadventuresapi.quiz.domain.services.OptionQueryService;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateOptionResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.OptionResource;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.CreateOptionCommandFromResourceAssembler;
import com.upc.viksadventuresapi.quiz.interfaces.rest.transform.OptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/options", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Options", description = "Option Management Endpoints")
public class OptionController {
    private final OptionCommandService optionCommandService;
    private final OptionQueryService optionQueryService;

    public OptionController(OptionCommandService optionCommandService, OptionQueryService optionQueryService) {
        this.optionCommandService = optionCommandService;
        this.optionQueryService = optionQueryService;
    }

    @PostMapping
    public ResponseEntity<OptionResource> createOption(@RequestBody CreateOptionResource resource) {
        var createOptionCommand = CreateOptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var option = optionCommandService.handle(createOptionCommand);
        if (option.isEmpty()) return ResponseEntity.badRequest().build();
        var optionResource = OptionResourceFromEntityAssembler.toResourceFromEntity(option.get());
        return new ResponseEntity<>(optionResource, HttpStatus.CREATED);
    }

    @GetMapping("/{optionId}")
    public ResponseEntity<OptionResource> getOption(@PathVariable Long optionId) {
        var getOptionByIdQuery = new GetOptionByIdQuery(optionId);
        var option = optionQueryService.handle(getOptionByIdQuery);
        if (option.isEmpty()) return ResponseEntity.badRequest().build();
        var optionResource = OptionResourceFromEntityAssembler.toResourceFromEntity(option.get());
        return ResponseEntity.ok(optionResource);
    }

    @GetMapping
    public ResponseEntity<List<OptionResource>> getAllOptions() {
        var getAllOptionsQuery = new GetAllOptionsQuery();
        var options = optionQueryService.handle(getAllOptionsQuery);
        var optionResources = options.stream().map(OptionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(optionResources);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<OptionResource>> getOptionsByQuestion(@PathVariable Long questionId) {
        var getOptionsByQuestionIdQuery = new GetOptionsByQuestionIdQuery(questionId);
        var options = optionQueryService.handle(getOptionsByQuestionIdQuery);
        var optionResources = options.stream().map(OptionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(optionResources);
    }

    @GetMapping("correct/quiz/{quizId}")
    public ResponseEntity<List<OptionResource>> getCorrectOptionsByQuiz(@PathVariable Long quizId) {
        var getCorrectOptionsByCorrectAndQuizIdQuery = new GetOptionsByCorrectAndQuizIdQuery(quizId);
        var options = optionQueryService.handle(getCorrectOptionsByCorrectAndQuizIdQuery); // Llama al query correcto
        var optionResources = options.stream().map(OptionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(optionResources);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<OptionResource>> getOptionsByQuiz(@PathVariable Long quizId) {
        var getOptionsByQuizIdQuery = new GetOptionsByQuizIdQuery(quizId);
        var options = optionQueryService.handle(getOptionsByQuizIdQuery);
        var optionResources = options.stream().map(OptionResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(optionResources);
    }


    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        var deleteOptionByIdCommand = new DeleteOptionByIdCommand(optionId);
        optionCommandService.handle(deleteOptionByIdCommand);
        return ResponseEntity.noContent().build();
    }
}
