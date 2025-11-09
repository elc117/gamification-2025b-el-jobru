package com.el_jobru.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.el_jobru.models.user.User;
import com.el_jobru.models.user.UserRole;
import com.el_jobru.repository.UserRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AuthMiddleware {

    public static void authMiddleware(Context ctx, JwtUtil tokenService, UserRepository userRepository) {
        // Pega as roles permitidas para a rota que o usuário está tentando acessar
        Set<RouteRole> permittedRoles = ctx.routeRoles();

        // Se a rota não tem papéis (ANYONE), permite o acesso
        if (permittedRoles.isEmpty() || permittedRoles.contains(UserRole.ANYONE)) {
            return;
        }

        // Pega o token do header
        String authHeader = ctx.header("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Token não fornecido ou mal formatado");
        }
        String token = authHeader.substring(7);

        // Valida o token
        DecodedJWT decodedTokenOpt = tokenService.validateJwt(token);
        if (decodedTokenOpt == null) {
            throw new UnauthorizedResponse("Token inválido ou expirado");
        }

        String tokenRole = tokenService.getRole(decodedTokenOpt);
        UUID userId = tokenService.getSubject(decodedTokenOpt);

        // Verifica a role do token
        if (!permittedRoles.contains(UserRole.valueOf(tokenRole))) {
            throw new UnauthorizedResponse("Acesso negado. Permissão insuficiente.");
        }

        // Busca o usuário no banco
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UnauthorizedResponse("Usuário do token não encontrado");
        }

        // Anexa o usuário ao contexto para uso no Controller
        ctx.attribute("currentUser", userOpt.get());
    }

}
