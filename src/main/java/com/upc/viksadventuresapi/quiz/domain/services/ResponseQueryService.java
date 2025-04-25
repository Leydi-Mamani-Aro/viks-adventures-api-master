package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ResponseQueryService {
    Optional<Response> handle(GetResponseByIdQuery query);
    Optional<Response> handle(GetResponsesByProfileIdQuery query);
    List<Response> handle(GetResponsesByProfileIdAndQuizIdQuery query);
    List<Response> handle(GetAllResponsesQuery query);
}
