package com.el_jobru;

import com.el_jobru.controller.AuthController;
import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.user.UserRole;
import com.el_jobru.repository.UserRepository;
import com.el_jobru.security.JwtUtil;
import com.el_jobru.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;

import static com.el_jobru.security.AuthMiddleware.authMiddleware;

public class MainApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        HibernateUtil.getEntityManager().close();

        //Singleton: Uma das maravilhas da POO pra garantir instância única em to-do o código
        JwtUtil.initialize(dotenv);

        //Injeção de Dependência (manualmente)
        UserRepository userRepository = new UserRepository();
        JwtUtil tokenService = JwtUtil.getInstance();
        UserService userService = new UserService(userRepository);
        AuthController authController = new AuthController(userService, tokenService);

        Javalin app = Javalin.create(config -> {
                    config.jsonMapper(new JavalinJackson());
                    config.http.defaultContentType = "application/json";
                })
                .start(8080);

        app.beforeMatched(ctx -> authMiddleware(ctx, tokenService, userRepository));

        app.get("/", ctx -> ctx.result("Hello, world"));
        app.get("/hello", ctx -> ctx.result("Bem-vindo"), UserRole.USER);

        app.post("/register", authController::register, UserRole.ANYONE);
        app.post("/login", authController::login, UserRole.ANYONE);

        app.get("/profile", authController::getProfile, UserRole.USER, UserRole.ADMIN);
        app.get("/admin/dashboard", ctx -> ctx.status(HttpStatus.OK).result("Bem-vindo, Admin"), UserRole.ADMIN);

    }
}
