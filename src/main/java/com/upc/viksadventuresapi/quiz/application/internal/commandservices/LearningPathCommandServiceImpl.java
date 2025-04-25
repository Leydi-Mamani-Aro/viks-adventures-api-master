package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.domain.services.LearningPathCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LearningPathCommandServiceImpl implements LearningPathCommandService {

    private final ResponseRepository responseRepository;

    @Autowired
    public LearningPathCommandServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Integer> getLearningPath(Long profileId, Long quizId) {
        RestTemplate restTemplate = new RestTemplate();
        // Obtener respuestas del estudiante
        List<Response> responses = responseRepository.findResponsesByProfileIdAndQuizId(profileId, quizId);

        // Transformar respuestas a formato de preguntas (1 si correcto, 0 si incorrecto)
        List<Integer> preguntas = responses.stream()
                .map(response -> response.isOptionCorrect() ? 1 : 0)
                .collect(Collectors.toList());

        // Crear request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id_estudiante", profileId);
        requestBody.put("preguntas", preguntas);

        // Hacer la solicitud HTTP al API externo
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "http://127.0.0.1:8000/predecir_ruta", requestBody, Map.class);

        // Extraer la ruta de aprendizaje del response
        Map responseMap = responseEntity.getBody();
        if (responseMap != null && responseMap.containsKey("ruta")) {
            return (List<Integer>) responseMap.get("ruta");
        } else {
            throw new RuntimeException("Error al obtener la ruta de aprendizaje");
        }
    }
}
