package com.upc.viksadventuresapi.quiz.domain.model.aggregates;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOptionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.IsCorrect;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.OptionText;
import com.upc.viksadventuresapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Option extends AuditableAbstractAggregateRoot<Option> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Embedded
    private OptionText optionText;

    @Embedded
    private IsCorrect isCorrect;

    public Option() { }

    public Option(Question question, OptionText optionText, IsCorrect isCorrect) {
        this.question = question;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        validate();
    }

    public Option(Question question, CreateOptionCommand command) {
        this(
                question,
                new OptionText(command.optionText()),
                new IsCorrect(command.isCorrect())
        );
    }

    public Option updateDetails(OptionText optionText, IsCorrect isCorrect) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        validate();
        return this;
    }

    private void validate() {
        if (this.optionText == null || this.isCorrect == null) {
            throw new IllegalArgumentException("OptionText and IsCorrect cannot be null");
        }
    }

    public Long getQuizId() {
        return question.getQuizId();
    }

    public Long getQuestionId() {
        return question.getId();
    }

    public String getOptionText() {
        return optionText.optionText();
    }

    public boolean isCorrect() {
        return isCorrect.correct();
    }
}
