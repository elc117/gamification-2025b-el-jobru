package migrations;

import migrations.user.UserMigration;

public class Migrations {
    public static void executeMigrations() {
        Migration user = new UserMigration();

        user.up();
    }
}
