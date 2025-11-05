package migrations.user;

import database.Database;
import migrations.Migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserMigration implements Migration {
    @Override
    public void up() {
        String createTableUserSql = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    age INT NOT NULL
                );
            """;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableUserSql);
            System.out.println("User Migration up done");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void down() {
        String dropTableSql = "DROP TABLE IF EXISTS users;";

        try (Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.execute(dropTableSql);
            System.out.println("Migração 'UserMigration.down()' executada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
