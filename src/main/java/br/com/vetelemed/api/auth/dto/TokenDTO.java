package br.com.vetelemed.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDTO(@JsonProperty("token") String token,
                       @JsonProperty("type") String type,
                       @JsonProperty("dateGeneration") String dateGeneration,
                       @JsonProperty("dateExpiration") String dateExpiration) {

    public TokenDTO {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token não pode ser nulo ou vazio");
        }
    }
}
