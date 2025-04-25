package com.upc.viksadventuresapi.quiz.domain.model.aggregates;

import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOrUpdateResultCommand;
import com.upc.viksadventuresapi.quiz.domain.model.valueobjects.Score;
import com.upc.viksadventuresapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Result extends AuditableAbstractAggregateRoot<Result> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="quiz_id", nullable = false)
    private Quiz quiz;

    @Setter
    @Embedded
    private Score score;

    public Result(){ }

    public Result(Profile profile, Quiz quiz, Score score) {
        this.profile = profile;
        this.quiz = quiz;
        this.score = score;
    }

    public Result(Profile profile, Quiz quiz, CreateOrUpdateResultCommand command) {
        this(
                profile,
                quiz,
                new Score(command.score())
        );
    }

    public Long getProfileId() {
        return profile.getId();
    }

    public Long getQuizId() {
        return quiz.getId();
    }

    public int getScore() {
        return score.score();
    }
}
