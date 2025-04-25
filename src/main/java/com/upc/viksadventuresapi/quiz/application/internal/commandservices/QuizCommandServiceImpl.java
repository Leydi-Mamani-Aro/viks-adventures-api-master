package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuizCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuizByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.services.QuizCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizCommandServiceImpl implements QuizCommandService {
    private final QuizRepository quizRepository;

    public QuizCommandServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Optional<Quiz> handle(CreateQuizCommand command) {
        var quiz = new Quiz(
                command.title(),
                command.description()
        );
        quizRepository.save(quiz);
        return Optional.of(quiz);
    }

    @Override
    public void handle(DeleteQuizByIdCommand command) {
        if (!quizRepository.existsById(command.quizId())) {
            throw new IllegalArgumentException("Quiz with ID " + command.quizId() + " does not exist.");
        }
        try {
            quizRepository.deleteById(command.quizId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting quiz with ID " + command.quizId());
        }
    }
}
