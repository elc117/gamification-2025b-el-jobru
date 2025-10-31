import io.javalin.Javalin;

public class MainApplication {
    public static void main(String[] args) {
        var app = Javalin.create()
                .get("/", ctx -> ctx.result("Hello, world"))
                .start(8080);
        app.get("/hello", ctx -> ctx.result("Bem-vindo"));


    }
}
