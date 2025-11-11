package com.el_jobru;

import com.el_jobru.controller.UserController;
import com.el_jobru.controller.BookController;
import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.user.UserRole;
import com.el_jobru.repository.BookRepository;
import com.el_jobru.repository.UserRepository;
import com.el_jobru.security.JwtUtil;
import com.el_jobru.service.BookService;
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
        BookRepository bookRepository = new BookRepository();
        JwtUtil tokenService = JwtUtil.getInstance();
        UserService userService = new UserService(userRepository);
        BookService bookService = new BookService(bookRepository);
        UserController userController = new UserController(userService, tokenService);
        BookController bookController = new BookController(bookService);

        Javalin app = Javalin.create(config -> {
                    config.jsonMapper(new JavalinJackson());
                    config.http.defaultContentType = "application/json";
                })
                .start(8080);

        app.beforeMatched(ctx -> authMiddleware(ctx, tokenService, userRepository));

        app.get("/", ctx -> ctx.result("Hello, world"));
        app.get("/hello", ctx -> ctx.result("Bem-vindo"), UserRole.USER);

        app.post("/register", userController::register, UserRole.ANYONE);
        app.post("/login", userController::login, UserRole.ANYONE);

        app.get("/profile", userController::getProfile, UserRole.USER, UserRole.ADMIN);
        app.patch("/profile/book", userController::addBook, UserRole.USER, UserRole.ADMIN);

        app.get("/admin/dashboard", ctx -> ctx.status(HttpStatus.OK).result("Bem-vindo, Admin"), UserRole.ADMIN);

        app.get("/book", bookController::getAll, UserRole.USER, UserRole.ADMIN);
        app.post("/book", bookController::register, UserRole.USER, UserRole.ADMIN);
    }
}
