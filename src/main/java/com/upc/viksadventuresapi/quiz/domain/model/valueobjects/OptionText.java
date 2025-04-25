package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record OptionText(String optionText) {
    public OptionText(){
        this(null);
    }
    public OptionText {
        if (optionText == null || optionText.isBlank()) {
            throw new IllegalArgumentException("imageUrl cannot be null or empty");
        }
    }
}
