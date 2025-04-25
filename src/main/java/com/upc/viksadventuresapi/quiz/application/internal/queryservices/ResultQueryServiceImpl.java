package com.upc.viksadventuresapi.quiz.application.internal.queryservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllResultsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByProfileIdAndQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultsByProfileIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.ResultQueryService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultQueryServiceImpl implements ResultQueryService {
    private final ResultRepository resultRepository;

    public ResultQueryServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public Optional<Result> query(GetResultByIdQuery query) {
        return resultRepository.findById(query.resultId());
    }

    @Override
    public List<Result> query(GetAllResultsQuery query) {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> handle(GetResultsByProfileIdQuery query) {
        Long profileId = query.profileId();
        return resultRepository.findResultsByProfileId(profileId);
    }

    @Override
    public Optional<Result> handle(GetResultByProfileIdAndQuizIdQuery query) {
        Long profileId = query.profileId();
        Long quizId = query.quizId();
        return resultRepository.findResultByProfileIdAndQuizId(profileId, quizId);
    }
}
