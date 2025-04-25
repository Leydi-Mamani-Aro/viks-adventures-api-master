package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOrUpdateResultCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResultByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetOptionsByCorrectAndQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.Score;
import com.upc.viksadventuresapi.quiz.domain.services.OptionQueryService;
import com.upc.viksadventuresapi.quiz.domain.services.ResultCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuizRepository;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.ResponseRepository;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultCommandServiceImpl implements ResultCommandService {
    private final ResultRepository  resultRepository;
    private final QuizRepository quizRepository;
    private final ProfileRepository profileRepository;
    private final ResponseRepository responseRepository;
    private final OptionQueryService optionQueryService;

    public ResultCommandServiceImpl(ResultRepository resultRepository, QuizRepository quizRepository, ProfileRepository profileRepository, ResponseRepository responseRepository, OptionQueryService optionQueryService) {
        this.resultRepository = resultRepository;
        this.quizRepository = quizRepository;
        this.profileRepository = profileRepository;
        this.responseRepository = responseRepository;
        this.optionQueryService = optionQueryService;
    }

    @Override
    public Optional<Result> handle(CreateOrUpdateResultCommand command) {
        Long profileId = command.profileId();
        Long quizId = command.quizId();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz with ID " + quizId + " not found."));
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile with ID " + profileId + " not found."));

        // Obtener todas las respuestas del estudiante en ese quiz
        List<Response> responses = responseRepository.findResponsesByProfileIdAndQuizId(profileId, quizId);

        System.out.println("=== RESPUESTAS DEL ESTUDIANTE ===");
        for (Response response : responses) {
            System.out.println("Response ID: " + response.getId() +
                    " | Option ID: " + response.getOptionId() +
                    " | Correcta: " + response.isOptionCorrect());
        }

        // Obtener las opciones correctas del quiz
        List<Option> correctOptions = optionQueryService.handle(new GetOptionsByCorrectAndQuizIdQuery(quizId));

        System.out.println("\n=== OPCIONES CORRECTAS DEL QUIZ ===");
        for (Option option : correctOptions) {
            System.out.println("Option ID: " + option.getId() +
                    " | Pregunta ID: " + option.getQuestionId() +
                    " | Texto: " + option.getOptionText());
        }

        // Contar cuántas respuestas del estudiante son correctas
        int correctAnswersCount = (int) responses.stream()
                .map(Response::getOptionId) // Obtener el ID de la opción de la respuesta
                .filter(responseOptionId -> correctOptions.stream()
                        .map(Option::getId)
                        .anyMatch(responseOptionId::equals)) // Comparar IDs
                .count();

        // Verificar si el Result ya existe
        Optional<Result> existingResult = resultRepository.findResultByProfileIdAndQuizId(profileId, quizId);

        Result result;
        if (existingResult.isPresent()) {
            result = existingResult.get();
            result.setScore(new Score(Math.min(correctAnswersCount, 10))); // Asegurar que el score no supere 10
        } else {
            result = new Result(profile, quiz, new Score(Math.min(correctAnswersCount, 10)));
        }

        resultRepository.save(result);
        return Optional.of(result);
    }

    @Override
    public void handle(DeleteResultByIdCommand command) {
        if (!resultRepository.existsById(command.resultId())) {
            throw new IllegalArgumentException("Result with ID " + command.resultId() + " does not exist.");
        }
        try {
            resultRepository.deleteById(command.resultId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting result with ID " + command.resultId());
        }
    }
}
