package com.el_jobru.controller;

import com.el_jobru.dto.auth.LoginDTO;
import com.el_jobru.dto.auth.LoginResponseDTO;
import com.el_jobru.dto.auth.RegisterDTO;
import com.el_jobru.dto.profile.ProfileResponseDTO;
import com.el_jobru.models.user.User;
import com.el_jobru.security.JwtUtil;
import com.el_jobru.service.UserService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.UnauthorizedResponse;

import java.util.Map;
import java.util.Optional;

public class UserController {

    private final UserService userService;
    private final JwtUtil tokenService;

    public UserController(UserService userService, JwtUtil tokenService) {
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
            ProfileResponseDTO profileData = userService.getUserProfile(currentUser);

            ctx.status(HttpStatus.OK).json(profileData);
        } else {
            throw new InternalServerErrorResponse("Usuário não encontrado no contexto.");
        }
    }

    public void addBook(Context ctx) {
        User currentUser = ctx.attribute("currentUser");
        long bookId = Long.parseLong(ctx.body());

        if (currentUser == null) {
            throw new InternalServerErrorResponse("Usuário não encontrado no contexto da requisição.");
        }

        ProfileResponseDTO updatedProfile = userService.addBookToUser(currentUser, bookId);

        ctx.status(HttpStatus.OK).json(updatedProfile);
    }
}
