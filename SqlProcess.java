import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SqlProcess {
    public Connection init() throws SQLException {
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String id = "postgres";
        String pw = "1234";

        Connection conn = DriverManager.getConnection(url, id, pw);

        dropView(conn);

        DataProcess.insert(conn);

        makeView(conn);

        return conn;
    }

    private void makeView(Connection conn) throws SQLException {
        conn.prepareStatement("create view EN as select U.name, branch, region, establish, address, homepage, officenumber, faxnumber, admissionfee, tuitionfee from university U, tuition T where U.name = T.name;").executeUpdate();
        conn.prepareStatement("create view NE as select U.name, U.branch, region, homepage, officenumber, college, department, daynight, largeseries, middleseries, yearsystem, establish from university U, department D where U.name = D.name and U.branch = D.branch;").executeUpdate();
        conn.prepareStatement("create view NN as select U.name, U.branch, region, homepage, officenumber, college, department, daynight, largeseries, middleseries, yearsystem, admissionfee, tuitionfee, establish from university U, department D, tuition T where U.name = D.name and U.name = T.name and U.branch = D.branch;").executeUpdate();
    }

    private void dropView(Connection conn) throws SQLException {
        conn.prepareStatement("drop view NE").executeUpdate();
        conn.prepareStatement("drop view EN").executeUpdate();
        conn.prepareStatement("drop view NN").executeUpdate();
    }

    public void insertFavorite(Connection conn, ResultSet resultSet, List<Integer> favorite) throws SQLException {
        int idx = 1;
        int favoriteIdx = 0;
        String sql = "insert into Favorite values (?,?,?,?,?) on conflict (name, branch, department) do nothing";
        PreparedStatement p = conn.prepareStatement(sql);

        Collections.sort(favorite);

        while (resultSet.next() && favoriteIdx < favorite.size()) {
            if (idx == favorite.get(favoriteIdx)) {
                for (int i = 1; i <= 5; i++) {
                    if (i == 3) { // department
                        p.setString(i, resultSet.getString(7));
                        continue;
                    }
                    p.setString(i, resultSet.getString(i));
                }
                p.addBatch();

                favoriteIdx++;
            }
            idx++;
        }

        p.executeBatch();
    }

    public void deleteFavorite(Connection conn, ResultSet resultSet, List<Integer> delFavorite) throws SQLException {
        int idx = 1;
        int favoriteIdx = 0;

        Collections.sort(delFavorite);

        while (resultSet.next() && favoriteIdx < delFavorite.size()) {
            if (idx == delFavorite.get(favoriteIdx)) {
                String sql = "delete from Favorite where" +
                        " name = \'" + resultSet.getString(1) + "\' and" +
                        " branch = \'" + resultSet.getString(2) + "\' and" +
                        " department = \'" + resultSet.getString(3) + "\'";
                conn.prepareStatement(sql).execute();

                favoriteIdx++;
            }
            idx++;
        }
    }
}
