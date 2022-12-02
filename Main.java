import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final InputView inputView = new InputView();
    public static void main(String[] args) {
        try{
            Connection conn = SqlProcess.init();
            inputView.input(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}