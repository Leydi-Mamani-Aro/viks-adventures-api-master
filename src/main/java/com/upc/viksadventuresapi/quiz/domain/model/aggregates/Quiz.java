package com.upc.viksadventuresapi.quiz.domain.model.aggregates;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuizCommand;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.Description;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.Title;
import com.upc.viksadventuresapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;

@Entity
public class Quiz extends AuditableAbstractAggregateRoot<Quiz> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    public Quiz(String title, String description) {
        this.title = new Title(title);
        this.description = new Description(description);
    }

    public Quiz(CreateQuizCommand command) {
        this(
                command.title(),
                command.description()
        );
    }

    public Quiz() {}

    public void updateTitle(String title) {
        this.title = new Title(title);
    }

    public void updateDescription(String description) {
        this.description = new Description(description);
    }

    public String getTitle() {
        return title.title();
    }

    public String getDescription() {
        return description.description();
    }
}
