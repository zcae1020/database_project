import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlProcess {
    public static Connection init() throws SQLException {
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String id = "postgres";
        String pw = "1234";

        Connection conn = DriverManager.getConnection(url, id, pw);

        DataProcess.insert(conn);
        return conn;
    }
}
