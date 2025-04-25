package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Question;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOptionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteOptionByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.services.OptionCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.OptionRepository;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OptionCommandServiceImpl implements OptionCommandService {
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public OptionCommandServiceImpl(QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public Optional<Option> handle(CreateOptionCommand command) {
        // Verify question exists
        Optional<Question> optionalQuestion = questionRepository.findById(command.questionId());
        if (optionalQuestion.isEmpty()) {
            throw new IllegalArgumentException("Question with ID " + command.questionId() + " does not exist.");
        }
        Question question = optionalQuestion.get();

        // Create option
        var option = new Option(question, command);
        try{
            optionRepository.save(option);
        } catch (Exception e) {
            throw new IllegalArgumentException("Option could not be created.");
        }
        return Optional.of(option);
    }

    @Override
    public void handle(DeleteOptionByIdCommand command) {
        if (!optionRepository.existsById(command.optionId())) {
            throw new IllegalArgumentException("Option with ID " + command.optionId() + " does not exist.");
        }
        try {
            optionRepository.deleteById(command.optionId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting option with ID " + command.optionId());
        }
    }

}
