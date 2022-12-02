import java.sql.*;
import java.util.*;

public class SqlTest4{
    private static final String[] csa = {"college","student","apply"};
    private static final String[][] collegeData = {
            {"Stanford", "CA", "15000"},
            {"Berkeley", "CA", "36000"},
            {"MIT", "MA", "10000"},
            {"Cornell", "NY", "21000"}
    },
            studentData = {
                    {"123", "Amy", "3.9", "1000"},
                    {"234", "Bob", "3.6", "1500"},
                    {"345", "Craig", "3.5", "500"},
                    {"456", "Doris", "3.9", "1000"},
                    {"567", "Edward", "2.9", "2000"},
                    {"678", "Fay", "3.8", "200"},
                    {"789", "Gary", "3.4", "800"},
                    {"987", "Helen", "3.7", "800"},
                    {"876", "Irene", "3.9", "400"},
                    {"765", "Jay", "2.9", "1500"},
                    {"654", "Amy", "3.9", "1000"},
                    {"543", "Craig", "3.4", "2000"}
            },
            applyData = {
                    {"123", "Stanford", "CS", "Y"},
                    {"123", "Stanford", "EE", "N"},
                    {"123", "Berkeley", "CS", "Y"},
                    {"123", "Cornell", "EE", "Y"},
                    {"234", "Berkeley", "biology", "N"},
                    {"345", "MIT", "bioengineering", "Y"},
                    {"345", "Cornell", "bioengineering", "N"},
                    {"345", "Cornell", "CS", "Y"},
                    {"345", "Cornell", "EE", "N"},
                    {"678", "Stanford", "history", "Y"},
                    {"987", "Stanford", "CS", "Y"},
                    {"987", "Berkeley", "CS", "Y"},
                    {"876", "Stanford", "CS", "N"},
                    {"876", "MIT", "biology", "Y"},
                    {"876", "MIT", "marinebiology", "N"},
                    {"765", "Stanford", "history", "Y"},
                    {"765", "Cornell", "history", "N"},
                    {"765", "Cornell", "psychology", "Y"},
                    {"543", "MIT", "CS", "N"}
            };


    public static void main(String[] args) throws SQLException{
        try {
            Scanner scan = new Scanner(System.in);
            String nl;
            System.out.println("SQL Programming Test");

            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
            String id = "postgres";
            String pw = "1234";

            Connection conn = DriverManager.getConnection(url, id, pw);
            PreparedStatement p;
            ResultSet r;

            conn.prepareStatement("drop view csee").execute();

            conn.prepareStatement("drop function r2").execute();
            conn.prepareStatement("drop function r4").execute();
            conn.prepareStatement("drop function cseeinsert").execute();

            fresh(conn);

            System.out.println("Trigger test 1");

            String sql = """
			 		create or replace function r2() returns trigger as $$
			 		begin
			 			delete from apply where sid = old.sid;
			 			return old;
			 		end;
			 		$$ language 'plpgsql';

			 		create trigger r2
			 		after delete on student
			 		for each row
			 		execute procedure r2();
			 		""";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            sql = "delete from Student where GPA < 3.5";

            p = conn.prepareStatement(sql);
            p.executeUpdate();

            //query 1

            sql = "select * from Student order by sID";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            int idx = 1;

            System.out.println("line\tsID\tsName\tGPA\tsizeHS");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t" + r.getDouble(3) +"\t" + r.getInt(4));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            //query 2

            sql = "select * from Apply order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor\t\tdecision");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3) +"\t\t" + r.getString(4));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            ///////// trigger 2

            System.out.println("Trigger test 2");

            sql = """
				 		create or replace function r4() returns trigger as $$
				 		begin
				 			IF exists(select * from College where cName = New.cName) THEN
								return null;
							ELSE
			 					return New;
							END IF;
				 		end;
				 		$$ language 'plpgsql';

				 		create trigger r4
				 		before insert on college
				 		for each row
				 		execute procedure r4();
				 		""";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            sql = """
			 		insert into College values ('UCLA', 'CA', 20000);
			 		insert into College values ('MIT', 'MI', 10000);
			 		""";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            //query 3

            sql = "select * from College order by cName;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tcName\t\tstate\tenrollment");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getString(1) + "\t\t" + r.getString(2) + "\t" + r.getInt(3));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            ////view 1

            System.out.println("View test 1");

            fresh(conn);

            sql = """
				 		create view CSEE as
				 		select sID, cName, major
				 		from Apply
				 		where major = 'CS' or major = 'EE';
				 		""";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            //query 4

            sql = "select * from CSEE order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            //////view 2

            System.out.println("View test 2");

            sql = """
				 		create or replace function cseeinsert() returns trigger as $$
				 		begin
				 			IF New.major = 'CS' or New.major = 'EE' THEN
				 				insert into Apply values (new.sid, new.cname, new.major, null);
								return New;
							ELSE
								return New;
							END IF;
				 		end;
				 		$$ language 'plpgsql';

				 		create trigger CSEEinsert
				 		instead of insert on CSEE
				 		for each row
				 		execute procedure cseeinsert();
				 		""";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            sql = "insert into CSEE values (333, 'UCLA', 'biology');";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            //query 5

            sql = "select * from CSEE order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            //query 6

            sql = "select * from Apply order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor\t\tdecision");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3) +"\t\t" + r.getString(4));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            /////view 3

            System.out.println("View test 3");

            sql = "insert into CSEE values (333, 'UCLA', 'CS');";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            p.executeUpdate();

            //query 7

            sql = "select * from CSEE order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3));
            }
            System.out.println();

            System.out.println("Continue? (Enter 1 for continue)");
            nl = scan.nextLine();
            if(!nl.equals("1")) return;

            //query 8

            sql = "select * from Apply order by sID, cName, major;";

            p = conn.prepareStatement(sql);
            p.clearParameters();
            r = p.executeQuery();

            idx = 1;
            System.out.println("line\tsID\tcName\t\tmajor\t\tdecision");
            System.out.println("--------------------------------------------");
            while(r.next()) {
                System.out.println(idx++ + "\t" + r.getInt(1) + "\t" + r.getString(2) + "\t\t" + r.getString(3) +"\t\t" + r.getString(4));
            }
            System.out.println();


        }catch(SQLException ex) {
            throw ex;
        }
    }

    private static void fresh(Connection conn) throws SQLException {
        PreparedStatement p1;

        conn.prepareStatement("drop table college").execute();
        conn.prepareStatement("drop table student").execute();
        conn.prepareStatement("drop table apply").execute();

        // 3개 Table 생성: Create table문 이용
        conn.prepareStatement("create table College(cName varchar(20), state char(2), enrollment int)").execute();
        conn.prepareStatement("create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int)").execute();
        conn.prepareStatement("create table Apply(sID int, cName varchar(20), major varchar(20), decision char)").execute();

        // 3개 Table에 Tuple 생성: Insert문 이용

        String sql = "insert into College values (?,?,?)";
        p1 = conn.prepareStatement(sql);
        for(String[] ss:collegeData) {
            p1.setString(1, ss[0]);
            p1.setString(2, ss[1]);
            p1.setInt(3, Integer.parseInt(ss[2]));
            p1.addBatch();
        }
        p1.executeBatch();

        sql = "insert into Student values (?,?,?,?)";
        p1 = conn.prepareStatement(sql);
        for(String[] ss:studentData) {
            p1.setInt(1, Integer.parseInt(ss[0]));
            p1.setString(2, ss[1]);
            p1.setDouble(3, Double.parseDouble(ss[2]));
            p1.setInt(4, Integer.parseInt(ss[3]));
            p1.addBatch();
        }
        p1.executeBatch();

        sql = "insert into Apply values (?,?,?,?)";
        p1 = conn.prepareStatement(sql);
        for(String[] ss:applyData) {
            p1.setInt(1, Integer.parseInt(ss[0]));
            p1.setString(2, ss[1]);
            p1.setString(3, ss[2]);
            p1.setString(4, ss[3]);
            p1.addBatch();
        }
        p1.executeBatch();
    }
}