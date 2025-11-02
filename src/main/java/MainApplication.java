import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import java.util.List;

class User {
    public String name;
    public int age;
    public User() {
        this.name = "Lisa";
        this.age = 18;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class MainApplication {
    public static void main(String[] args) {
        var app = Javalin.create(config -> config.jsonMapper(new JavalinJackson()))
                .get("/", ctx -> ctx.result("Hello, world"))
                .start(8080);
        app.get("/hello", ctx -> ctx.result("Bem-vindo"));

        User user_one = new User();
        User user_two = new User("Carlos", 30);

        List<User> users = List.of(user_one, user_two);

        app.get("/users", ctx -> ctx.json(users));
    }
}
