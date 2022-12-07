import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProcess {

    public static void insert(Connection conn) throws SQLException {
        File universityFile = new File("src/asset/university.csv");
        File departmentFile = new File("src/asset/department.csv");
        File tuitionFile = new File("src/asset/tuition.csv");

        List<List<String>> university = readToList(universityFile);
        List<List<String>> department = readToList(departmentFile);
        List<List<String>> tuition = readToList(tuitionFile);

        String sql;
        PreparedStatement p;

        conn.prepareStatement("drop table University").execute();
        conn.prepareStatement("drop table Department").execute();
        conn.prepareStatement("drop table Tuition").execute();
        conn.prepareStatement("drop table Favorite").execute();

        conn.prepareStatement("create table University(Name varchar(64), Branch varchar(10), Region varchar(4), Establish varchar(10), Address varchar(64), Homepage varchar(64), OfficeNumber varchar(32), FaxNumber varchar(32), primary key(Name, Branch))").execute();
        conn.prepareStatement("create table Department(Name varchar(64), Branch varchar(10), College varchar(64), Department varchar(128), DayNight varchar(16), LargeSeries varchar(32), MiddleSeries varchar(32), YearSystem numeric(2,1))").execute();
        conn.prepareStatement("create table Tuition(Name varchar(64), AdmissionFee int, TuitionFee int, primary key(Name))").execute();
        conn.prepareStatement("create table Favorite(Name varchar(64), Branch varchar(10), Department varchar(128), Homepage varchar(64), OfficeNumber varchar(32), primary key(Name, Branch, Department))").execute();

        sql = "insert into University values (?,?,?,?,?,?,?,?)";
        p = conn.prepareStatement(sql);
        for (int i = 1; i < university.size(); i++) {
            for (int j = 1; j <= university.get(i).size(); j++) {
                String value = university.get(i).get(j - 1);
                p.setString(j, value);
            }
            p.addBatch();
        }
        p.executeBatch();

        sql = "insert into Department values (?,?,?,?,?,?,?,?)";
        p = conn.prepareStatement(sql);
        for (int i = 0; i < department.size(); i++) {
            for (int j = 1; j <= department.get(i).size(); j++) {
                if (j == 8) { // YearSystem
                    String value = department.get(i).get(j - 1);
                    p.setDouble(j, Double.parseDouble(value));
                    continue;
                }
                String value = department.get(i).get(j - 1);
                p.setString(j, value);
            }
            p.addBatch();
        }
        p.executeBatch();

        sql = "insert into Tuition values (?,?,?)";
        p = conn.prepareStatement(sql);
        for (int i = 0; i < tuition.size(); i++) {
            for (int j = 1; j <= tuition.get(i).size(); j++) {
                if (j == 1) { // Name
                    String value = tuition.get(i).get(j - 1);
                    p.setString(j, value);
                    continue;
                }
                String value = tuition.get(i).get(j - 1);
                p.setInt(j, Integer.parseInt(value));
            }
            p.addBatch();
        }
        p.executeBatch();
    }

    private static List<List<String>> readToList(File csv) {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            Charset.forName("UTF-8");
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] token = line.split(",");
//                for(String tok: token) {
//                    System.out.print(tok+" | ");
//                }
//                System.out.println();
                List<String> tempList = new ArrayList<String>(Arrays.asList(token));
                list.add(tempList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}