import database.Database;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import migrations.Migrations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MainApplication {
    static class User {
        public int id;
        public String name;
        public int age;
        public User() {
            this.id = 1;
            this.name = "Lisa";
            this.age = 18;
        }

        public User(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        Database.init(dotenv);

        Migrations.executeMigrations();

        var app = Javalin.create(config -> {
                    config.jsonMapper(new JavalinJackson());

                    config.events(event -> {
                        event.serverStopped(Database::close);
                    });
                })
                .get("/", ctx -> ctx.result("Hello, world"))
                .start(8080);
        app.get("/hello", ctx -> ctx.result("Bem-vindo"));

        User user_one = new User();
        User user_two = new User(1, "Carlos", 30);

        List<User> users = List.of(user_one, user_two);

        app.get("/users", ctx -> ctx.json(users));

        app.post("/users", ctx -> {
            User user = ctx.bodyAsClass(User.class);

            String sql = "INSERT INTO users (name, age) VALUES (?, ?)";

            try (Connection conn = Database.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, user.name);
                statement.setInt(2, user.age);
                statement.executeUpdate();
            }
            ctx.status(201).json(user);
        });
    }
}
