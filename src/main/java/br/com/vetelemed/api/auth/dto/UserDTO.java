package br.com.vetelemed.api.auth.dto;

import br.com.vetelemed.api.auth.model.UserModel;
import jakarta.validation.constraints.NotBlank;

public record UserDTO (@NotBlank String email, @NotBlank String password) {

    public UserDTO(UserModel user){
        this(user.getUsername(), user.getPassword());
    }
}
