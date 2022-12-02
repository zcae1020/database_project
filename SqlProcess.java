import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlProcess {
    public static Connection init() throws SQLException {
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String id = "postgres";
        String pw = "1234";

        Connection conn = DriverManager.getConnection(url, id, pw);

        dropView(conn);

        DataProcess.insert(conn);

        makeView(conn);

        return conn;
    }

    private static void makeView(Connection conn) throws SQLException {
        conn.prepareStatement("create view NE as select university.name, university.branch, region, homepage, officenumber, college, department, daynight, largeseries, middleseries, yearsystem from university, department;").executeUpdate();
        conn.prepareStatement("create view EN as select university.name, branch, region, homepage, officenumber, admissionfee, tuitionfee from university, tuition;").executeUpdate();
        conn.prepareStatement("create view NN as select university.name, university.branch, region, officenumber, college, department, daynight, largeseries, middleseries, yearsystem, admissionfee, tuitionfee from university, department, tuition;").executeUpdate();
    }

    private static void dropView(Connection conn) throws SQLException {
        conn.prepareStatement("drop view NE").executeUpdate();
        conn.prepareStatement("drop view EN").executeUpdate();
        conn.prepareStatement("drop view NN").executeUpdate();
    }
}
