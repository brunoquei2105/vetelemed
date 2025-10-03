package br.com.vetelemed.api.auth.controller;

import br.com.vetelemed.api.auth.dto.TokenDTO;
import br.com.vetelemed.api.auth.dto.UserDTO;
import br.com.vetelemed.api.auth.model.UserModel;
import br.com.vetelemed.api.auth.service.AuthenticationService;
import br.com.vetelemed.api.auth.service.JwtCookieService;
import br.com.vetelemed.api.auth.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@AllArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and JWT management")
@RequestMapping("${vetelemed.request.mapping}")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final JwtCookieService jwtCookieService;

    @PostMapping("/login")
    @Operation(summary = "Efetuar login e receber token JWT",
    responses = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido, token JWT retornado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserDTO userDTO, HttpServletResponse response){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
        var authentication = authenticationManager.authenticate(token);
        var jwt = tokenService.generateToken((UserModel)authentication.getPrincipal());
        jwtCookieService.addJwtToCookie(jwt, response);
        log.info("User {} logged in successfully", userDTO.email());

        LocalDateTime generatioDate = LocalDateTime.now();
        LocalDateTime expirationDate = generatioDate.plusHours(2);

        return ResponseEntity.ok(new TokenDTO(jwt, "Bearer", generatioDate.toString(), expirationDate.toString()));
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO userDTO){
        UserModel newUser = new UserModel(userDTO.email(), null, passwordEncoder.encode(userDTO.password()));
        authenticationService.save(newUser);
        return ResponseEntity.ok("User successfully created");

    }
}
