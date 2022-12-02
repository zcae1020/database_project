import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private final OutputView outputView = new OutputView();
    public void input(Connection conn) throws SQLException {
        SearchData searchData = new SearchData();
        String select = new String("");
        ResultSet rs = null;
        Statement stmt = null;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome University Comprehensive Search System!");
        while (true) {
            System.out.println("다음 중 원하는 기능에 대해 숫자로 입력하세요.");
            System.out.println("0. 검색 | 1. 대학 | 2. 학과 | 3: 지역 | 4. 등록금 | 5. 설립구분 | 6. 표준분류중계열 | 7. 학교구분 | 8. 주야간 | 9. 즐겨찾기 | Q or q: 프로그램 종료");
            select = sc.next();
            if (select.equals("Q") || select.equals("q")) {
                System.out.println("Program exit! See you again!");
                System.exit(0);
            } else if (select.equals("0")) {

                int i = 0;
                while(true) {
                    if(searchData.University.isEmpty()) {
                        System.out.println("다음 중 원하는 기능에 대해 숫자로 입력하세요.");
                    }
                    // check condition
                }

                rs = stmt.executeQuery("select * from University where Name like '%" + searchData.University + "%\';");
                System.out.println("학교 | 본분교 | 지역 | 설립구분 | 주소 | 학교홈페이지 | 학교대표전화 | 학교대표팩스번호");
                int index = 0;
                while(rs.next()) {
                    index += 1;
                    String Name = rs.getString("Name");
                    String branch = rs.getString("branch");
                    String region = rs.getString("region");
                    String establish = rs.getString("establish");
                    String address = rs.getString("address");
                    String officenum = rs.getString("officenum");
                    String faxnum = rs.getString("faxnum");

                    System.out.println(String.format("%s %s %s %s %s %s %s %s", index, Name, branch, region, establish, address, officenum, faxnum));
                }
            } else if (select.equals("1")) {
                System.out.println("키워드를 입력하세요.");
                searchData.University.add(sc.next());
            } else if (select.equals("2")) {
                System.out.println("키워드를 입력하세요.");
                searchData.Department.add(sc.next());

            } else if (select.equals("3")) {
                System.out.println("키워드를 입력하세요.");
                searchData.Region.add(sc.next());

            } else if (select.equals("4")) {
                System.out.println("등록금 최대치와 최소치를 순서대로 입력하세요.");
                System.out.print("최대치: ");
                int maxFee = sc.nextInt();
                System.out.print("최소치: ");
                int minFee = sc.nextInt();
                searchData.AdmissionFee.add(Arrays.asList(maxFee, minFee));

            } else if (select.equals("5")) {
                System.out.println("키워드를 입력하세요.");
                searchData.Establish.add(sc.next());
            } else if (select.equals("6")) {
                System.out.println("키워드를 입력하세요.");
                searchData.MiddleSeries.add(sc.next());
            } else if (select.equals("7")) {
                System.out.println("키워드를 입력하세요.");
                searchData.YearSystem.add(sc.next());
            } else if (select.equals("8")) {
                System.out.println("키워드를 입력하세요.");
                searchData.DayNignt.add(sc.next());
            } else if (select.equals("9")) {
                System.out.println("키워드를 입력하세요.");
                searchData.Favorite.add(sc.next());
            } else {
                System.out.println("Please enter correctly!");
            }
        }

    }
}