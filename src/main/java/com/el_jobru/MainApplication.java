package com.el_jobru;

import com.el_jobru.controller.*;
import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.user.UserRole;
import com.el_jobru.repository.*;
import com.el_jobru.security.JwtUtil;
import com.el_jobru.service.*;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;

import static com.el_jobru.security.AuthMiddleware.authMiddleware;

public class MainApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        HibernateUtil.getEntityManager().close();

        //Singleton: Uma das maravilhas da POO pra garantir instância única em to-do o código
        JwtUtil.initialize(dotenv);

        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        //Injeção de Dependência (manualmente)
        //Repositories
        UserRepository userRepository = new UserRepository();
        BookRepository bookRepository = new BookRepository();
        DiaryRepository diaryRepository = new DiaryRepository();
        ChapterRepository chapterRepository = new ChapterRepository();
        LevelRepository levelRepository = new LevelRepository();
        MissionRepository missionRepository = new MissionRepository();

        //Services
        JwtUtil tokenService = JwtUtil.getInstance();
        UserService userService = new UserService(userRepository);
        BookService bookService = new BookService(bookRepository);
        DiaryService diaryService = new DiaryService(diaryRepository);
        ChapterService chapterService = new ChapterService(chapterRepository);
        LevelService levelService = new LevelService(levelRepository);
        MissionService missionService = new MissionService(missionRepository);
      
        //Controllers
        UserController userController = new UserController(userService, tokenService);
        BookController bookController = new BookController(bookService);
        DiaryController diaryController = new DiaryController(diaryService);
        ChapterController chapterController = new ChapterController(chapterService);
        LevelController levelController = new LevelController(levelService);
        MissionController missionController = new MissionController(missionService);

        Javalin app = Javalin.create(config -> {
                    config.jsonMapper(new JavalinJackson());
                    config.http.defaultContentType = "application/json";
                    config.bundledPlugins.enableCors( cors -> cors.addRule(corsRule -> {
                                corsRule.allowHost("http://127.0.0.1:5500");
                                corsRule.allowHost("https://html-classic.itch.zone");
                                corsRule.allowHost("https://bbock.itch.io");
                            })

                    );
                })
                .start(port);

        app.beforeMatched(ctx -> authMiddleware(ctx, tokenService, userRepository));

        app.get("/", ctx -> ctx.result("Hello, world!"));
        app.get("/hello", ctx -> ctx.result("Bem-vindo"), UserRole.USER);

        app.post("/register", userController::register, UserRole.ANYONE);
        app.post("/login", userController::login, UserRole.ANYONE);

        app.get("/profile", userController::getProfile, UserRole.USER, UserRole.ADMIN);
        app.patch("/profile/book", userController::addBook, UserRole.USER, UserRole.ADMIN);
        app.patch("/mission/claim", userController::accomplishedMission, UserRole.USER, UserRole.ADMIN);

        app.get("/admin/dashboard", ctx -> ctx.status(HttpStatus.OK).result("Bem-vindo, Admin"), UserRole.ADMIN);

        app.get("/book", bookController::getAll, UserRole.USER, UserRole.ADMIN);
        app.post("/book", bookController::register, UserRole.USER, UserRole.ADMIN);

        app.get("/diary", diaryController::getAll, UserRole.USER, UserRole.ADMIN);
        app.post("/diary", diaryController::register, UserRole.USER, UserRole.ADMIN);

        app.get("/chapter", chapterController::getDiaryAll, UserRole.USER, UserRole.ADMIN);
        app.post("/chapter", chapterController::register, UserRole.USER, UserRole.ADMIN);
      
        app.post("/level", levelController::register, UserRole.ADMIN);
        app.get("/level", levelController::getAll, UserRole.USER, UserRole.ADMIN);

        app.post("/mission", missionController::register, UserRole.ADMIN);
        app.get("/mission", missionController::getAll, UserRole.USER, UserRole.ADMIN);
    }
}
