package com.upc.viksadventuresapi.quiz.domain.model.events;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ResponseCreatedEvent extends ApplicationEvent {

    private final Response response;

    public ResponseCreatedEvent(Object source, Response response) {
        super(source);
        this.response = response;
    }

}