package com.upc.viksadventuresapi.quiz.domain.services;

import java.util.List;

public interface LearningPathCommandService {
    List<Integer> getLearningPath(Long profileId, Long quizId);
}
