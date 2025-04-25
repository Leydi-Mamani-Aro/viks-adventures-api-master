package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Question;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuestionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuestionByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.services.QuestionCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuestionRepository;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionCommandServiceImpl implements QuestionCommandService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuestionCommandServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Question> handle(CreateQuestionCommand command) {
        // Verify quiz existence
        Optional<Quiz> optionalQuiz = quizRepository.findById(command.quizId());
        if (optionalQuiz.isEmpty()) {
            throw new IllegalArgumentException("Quiz with ID " + command.quizId() + " does not exist.");
        }
        Quiz quiz = optionalQuiz.get();

        //Create question
        var question = new Question(quiz, command);
        try {
            questionRepository.save(question);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving question: " + e.getMessage());
        }
        return Optional.of(question);

    }


    @Override
    public void handle(DeleteQuestionByIdCommand command) {
        if (!questionRepository.existsById(command.questionId())) {
            throw new IllegalArgumentException("Question with ID " + command.questionId() + " does not exist.");
        }
        try {
            questionRepository.deleteById(command.questionId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting question with ID " + command.questionId());
        }
    }
}
