import database.Database;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import migrations.Migrations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication {
    static class User {
        public int id;
        public String name;
        public int age;

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

                    config.events(event -> event.serverStopped(Database::close));
                })
                .get("/", ctx -> ctx.result("Hello, world"))
                .start(8080);
        app.get("/hello", ctx -> ctx.result("Bem-vindo"));

        app.get("/users", ctx -> {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM users";

            try (Connection connection = Database.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    ));
                }

                ctx.status(200).json(users);
            } catch (SQLException e) {
                System.err.println("Erro ao buscar usuários: " + e.getMessage());
                ctx.status(e.getErrorCode()).result("Erro ao buscar usuários: " + e.getMessage());
            }
        });

        app.get("/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));

            String sql = "SELECT * FROM users WHERE id = ?";

            User user = null;

            try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
            ) {
                statement.setInt(1, userId);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    );
                }

                if (user != null) {
                    context.status(200).json(user);
                } else {
                    context.status(404).result("Usuário não encontrado");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar usuário: " + e.getMessage());
                context.status(404).result("Erro ao buscar usuário: " + e.getMessage());
            }
        });

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

        app.put("users/{id}", context -> {
            User user = context.bodyAsClass(User.class);
            int userId = Integer.parseInt(context.pathParam("id"));

            String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";

            try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
            ) {
                statement.setString(1, user.name);
                statement.setInt(2, user.age);
                statement.setInt(3, userId);

                statement.executeUpdate();
                user.id = userId;

                context.status(200).json(user);
            }
        });

        app.delete("/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));

            String sql = "DELETE FROM users WHERE id = ?";

            try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);

                    statement.executeUpdate();

                    context.status(200).result("Usuário apagado");
            } catch (SQLException e) {
                System.err.println("Erro ao apagar registro: " + e.getMessage());
                context.status(404).result("Erro ao apagar: " + e.getMessage());
            }
        });
    }
}
