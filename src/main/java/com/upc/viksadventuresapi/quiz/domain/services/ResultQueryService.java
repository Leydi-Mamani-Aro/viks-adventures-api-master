package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllResultsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultByProfileIdAndQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetResultsByProfileIdQuery;

import java.util.List;
import java.util.Optional;

public interface ResultQueryService {
    Optional<Result> query(GetResultByIdQuery query);
    List<Result> query(GetAllResultsQuery query);
    Optional<Result> handle(GetResultsByProfileIdQuery query);
    Optional<Result> handle(GetResultByProfileIdAndQuizIdQuery query);
}
