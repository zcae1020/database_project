import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            Connection conn = SqlProcess.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}