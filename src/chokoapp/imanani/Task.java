package chokoapp.imanani;

public class Task {
    public static final String TABLE_NAME = "tasks";
    private long id;
    private String code;
    private String description;

    public Task(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String create_sql() {
        return "CREATE TABLE tasks (\n"
                + "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "  code TEXT,\n"
                + "  description TEXT\n"
                + ");";
    }

    public static String insert_sql() {
        return "INSERT INTO tasks (code, description) VALUES (?, ?);";
    }

    public String toString() {
        return code + ":" + description;
    }
}