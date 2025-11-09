package com.el_jobru.controller;

import com.el_jobru.dto.LoginDTO;
import com.el_jobru.dto.LoginResponseDTO;
import com.el_jobru.dto.RegisterDTO;
import com.el_jobru.models.user.User;
import com.el_jobru.security.JwtUtil;
import com.el_jobru.service.UserService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;

import java.util.Map;
import java.util.Optional;

public class AuthController {

    private final UserService userService;
    private final JwtUtil tokenService;

    public AuthController(UserService userService, JwtUtil tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void register(Context ctx) {
        try {
            RegisterDTO registerDTO = ctx.bodyAsClass(RegisterDTO.class);

            User newUser = userService.registerUser(registerDTO);

            String token = tokenService.generateToken(newUser);
            ctx.status(HttpStatus.CREATED).json(Map.of("user", newUser, "token", token));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("error", e.getMessage()));
        }
    }

    public void login(Context ctx) {
        LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
        Optional<User> userOptional = userService.validateLogin(loginDTO);

        if (userOptional.isEmpty()) {
            throw new UnauthorizedResponse("Email ou senha inválidos");
        }

        User user = userOptional.get();

        String token = tokenService.generateToken(user);
        ctx.status(HttpStatus.OK).json(new LoginResponseDTO(token));
    }

    public void getProfile(Context ctx) {
        User currentUser = ctx.attribute("currentUser");

        if (currentUser != null) {
            ctx.status(HttpStatus.OK).json(currentUser);
        } else {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("error", "Usuário não encontrado no contexto da requisição"));
        }
    }
}
