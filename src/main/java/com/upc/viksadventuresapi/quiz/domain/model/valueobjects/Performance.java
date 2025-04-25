package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record Performance(int value, String description) {

    // Constructor que asigna la descripción automáticamente según el valor
    public Performance(int value) {
        this(value, getDescriptionFromValue(value));
    }

    // Método para obtener la descripción basada en el valor
    private static String getDescriptionFromValue(int value) {
        return switch (value) {
            case 1 -> "Expresa su comprensión sobre las fracciones como parte-todo involucrando cantidades discretas desde su representación simbólica hacia su representación gráfica.";
            case 2 -> "Expresa su comprensión sobre la fracción como operador involucrando cantidades continuas, a partir de su representación simbólica.";
            case 3 -> "Expresa su comprensión sobre las fracciones como medida involucrando cantidades continuas, desde su representación gráfica hacia su representación simbólica.";
            case 4 -> "Emplea diversas estrategias para establecer equivalencias entre unidades de masa.";
            case 5 -> "Establece relaciones entre los datos y condiciones de situaciones vinculadas al reparto de cantidades. Las transforma a expresiones involucrando los criterios de divisibilidad de números naturales y las resuelve.";
            case 6 -> "Establece relaciones entre los datos y condiciones de situaciones vinculadas a las acciones de igualar cantidades involucrando fracciones y las resuelve.";
            case 7 -> "Establece relaciones entre datos y condiciones de situaciones para determinar la parte de un total, transformando a expresiones que interpretan el valor del porcentaje.";
            case 8 -> "Establece relaciones entre los datos y condiciones de situaciones vinculadas a una combinación de acciones de repetir y separar cantidades, involucrando números decimales.";
            case 9 -> "Argumenta la validez de una afirmación vinculada al descuento porcentual de una cantidad en situaciones de su entorno, sustentándola con ejemplos.";
            case 10 -> "Emplea diversas estrategias vinculadas al canje para establecer nuevas equivalencias entre cantidades.";
            default -> throw new IllegalArgumentException("Valor de Performance inválido: " + value);
        };
    }

    public Performance {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("El valor de Performance debe estar entre 1 y 10.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("La descripción de Performance no puede estar vacía.");
        }
    }

    public int value() {
        return value;
    }
    @Override
    public String toString() {
        return "%d - %s".formatted(value, description);
    }
}
