import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final InputView inputView = new InputView();
    private static final SqlProcess sqlProcess = new SqlProcess();
    public static void main(String[] args) {
        try{
            Connection conn = sqlProcess.init();
            inputView.input(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}