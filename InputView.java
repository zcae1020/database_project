import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private final OutputView outputView = new OutputView();
    private final Scanner sc = new Scanner(System.in);
    private SearchData searchData = new SearchData();
    private String select;
    private int minFee;
    private int maxFee;

    public void input(Connection conn) throws SQLException {
        System.out.println("Welcome University Comprehensive Search System!");

        while (true) {
            System.out.println("다음 중 원하는 기능에 대해 숫자로 입력하세요.");
            System.out.println("0. 검색 | 1. 대학 | 2. 학과 | 3: 지역 | 4. 설립구분 | 5. 표준분류중계열 | 6. 주야간 | 7. 수업연한 | 8. 등록금 | 9. 즐겨찾기 | Q or q: 프로그램 종료");
            select = sc.next();
            if (select.equals("Q") || select.equals("q")) {
                System.out.println("Program exit! See you again!");
                return;
            } else {
                int selectNum = getSelectNum(select);

                switch (selectNum) {
                    case 0:
                        String query = searchData.getQuery();
                        System.out.println(searchData.getQuery());
                        ResultSet rs = conn.prepareStatement(query).executeQuery();
                        outputView.printQuery(rs);
                        searchData = new SearchData();
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        System.out.println("키워드를 입력하세요.");
                        searchData.addData(selectNum - 1, sc.next());
                        break;
                    case 7:
                        System.out.println("수업연한 최대치와 최소치를 순서대로 입력하세요.");
                        System.out.print("최대치: ");
                        maxFee = sc.nextInt();
                        System.out.print("최소치: ");
                        minFee = sc.nextInt();
                        searchData.addYearSystem(Arrays.asList(maxFee, minFee));
                        break;
                    case 8:
                        System.out.println("등록금 최대치와 최소치를 순서대로 입력하세요.");
                        System.out.print("최대치: ");
                        maxFee = sc.nextInt();
                        System.out.print("최소치: ");
                        minFee = sc.nextInt();
                        searchData.addAdmissionFee(Arrays.asList(maxFee, minFee));
                        break;
                    case 9:
                        //즐겨찾기
                        break;
                    default:
                        System.out.println("Please enter correctly!");
                }
            }

            System.out.println(searchData);
        }
    }

    private int getSelectNum(String select) {
        int selectNum;
        try {
            selectNum = Integer.parseInt(select);
        } catch (NumberFormatException e) {
            selectNum = 10;
        }
        return selectNum;
    }
}