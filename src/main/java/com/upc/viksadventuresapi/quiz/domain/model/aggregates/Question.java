package com.upc.viksadventuresapi.quiz.domain.model.aggregates;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuestionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.ImageUrl;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.Performance;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.QuestionText;
import com.upc.viksadventuresapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Question extends AuditableAbstractAggregateRoot<Question> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Embedded
    private Performance performance;

    @Embedded
    private QuestionText questionText;

    @Embedded
    private ImageUrl imageUrl;

    public Question() { }

    public Question(Quiz quiz, Performance performance, QuestionText questionText, ImageUrl imageUrl) {
        this();
        this.quiz = quiz;
        this.performance = performance;
        this.questionText = questionText;
        this.imageUrl = imageUrl;
        validate();
    }

    public Question(Quiz quiz, CreateQuestionCommand command) {
        this(
                quiz,
                new Performance(command.performance()),
                new QuestionText(command.questionText()),
                new ImageUrl(command.imageUrl())
        );
    }

    public Question updateDetails(Performance performance, QuestionText questionText, ImageUrl imageUrl) {
        if (performance != null) this.performance = performance;
        if (questionText != null) this.questionText = questionText;
        if (imageUrl != null) this.imageUrl = imageUrl;
        validate();
        return this;
    }

    private void validate() {
        if (this.performance == null || this.questionText == null) {
            throw new IllegalArgumentException("Performance and QuestionText cannot be null");
        }
    }

    public Long getQuizId() {
        return quiz != null ? quiz.getId() : null;
    }

    public int getPerformance() {
        return performance.value();
    }

    public String getQuestionText() {
        return questionText.questionText();
    }

    public String getImageUrl() {
        return imageUrl.imageUrl();
    }
}