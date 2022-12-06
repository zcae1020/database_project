import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OutputView {

    private final List<List<String>> attr = Arrays.asList(
            Arrays.asList("No", "학교", "본분교", "지역", "설립구분", "주소", "학교홈페이지", "학교대표전화", "학교대표팩스번호"),
            Arrays.asList("No", "학교", "본분교", "지역", "설립구분", "주소", "학교홈페이지", "학교대표전화", "학교대표팩스번호", "입학금", "등록금"),
            Arrays.asList("No", "학교", "본분교", "지역", "학교홈페이지", "학교대표전화", "단과대학", "학과", "주야간", "표준분류대계열", "표준분류중계열", "수업연한"),
            Arrays.asList("No", "학교", "본분교", "지역", "학교홈페이지", "학교대표전화", "단과대학", "학과", "주야간", "표준분류대계열", "표준분류중계열", "수업연한", "입학금", "등록금"),
            Arrays.asList("No", "학교", "본분교", " 학과", "학교홈페이지", "학교대표전화")
    );

    private final List<List<Integer>> attrLength = Arrays.asList(
            Arrays.asList(5, 30, 5, 5, 5, 50, 30, 20, 20),
            Arrays.asList(5, 30, 5, 5, 5, 50, 30, 20, 20, 10, 10),
            Arrays.asList(5, 30, 5, 5, 30, 20, 20, 30, 5, 30, 30, 5),
            Arrays.asList(5, 30, 5, 5, 30, 20, 20, 30, 5, 30, 30, 5, 10, 10),
            Arrays.asList(5, 30, 5, 30, 30, 20)
    );

    public List<Integer> printQuery(ResultSet rs, String view) throws SQLException {
        List<Integer> favorite = new ArrayList<>();
        int size;

        switch (view) {
            case "University":
                printAttr8(rs);
                break;
            case "en":
                printAttr10(rs);
                break;
            case "ne":
                size = printAttr11(rs);
                if (size > 1) {
                    favorite = askFavorite(size);
                }
                break;
            case "nn":
                size = printAttr13(rs);
                if (size > 1) {
                    favorite = askFavorite(size);
                }
                break;
        }

        return favorite;
    }

    private int printAttr8(ResultSet rs) throws SQLException {
        int idx = 1;
        printAttrList(0);

        while (rs.next()) {
            System.out.printf("%-5d", idx++);
            for (int i = 1; i <= 8; i++) {
                switch (attrLength.get(0).get(i)) {
                    case 5:
                        System.out.printf("| %-5s", rs.getString(i));
                        break;
                    case 10:
                        System.out.printf("| %-10s", rs.getString(i));
                        break;
                    case 20:
                        System.out.printf("| %-20s", rs.getString(i));
                        break;
                    case 30:
                        System.out.printf("| %-30s", rs.getString(i));
                        break;
                    case 50:
                        System.out.printf("| %-50s", rs.getString(i));
                        break;
                }
            }
            System.out.println("|");
        }
        System.out.println();
        return idx;
    }

    private int printAttr10(ResultSet rs) throws SQLException {
        int idx = 1;

        printAttrList(1);

        while (rs.next()) {
            System.out.printf("%-5d", idx++);
            for (int i = 1; i <= 8; i++) {
                switch (attrLength.get(1).get(i)) {
                    case 5:
                        System.out.printf("| %-5s", rs.getString(i));
                        break;
                    case 10:
                        System.out.printf("| %-10s", rs.getString(i));
                        break;
                    case 20:
                        System.out.printf("| %-20s", rs.getString(i));
                        break;
                    case 30:
                        System.out.printf("| %-30s", rs.getString(i));
                        break;
                    case 50:
                        System.out.printf("| %-50s", rs.getString(i));
                        break;
                }
            }
            for (int i = 9; i <= 10; i++) {
                System.out.printf("| %-10d원", rs.getInt(i));
            }
            System.out.println("|");
        }
        System.out.println();
        return idx;
    }

    private int printAttr11(ResultSet rs) throws SQLException {
        int idx = 1;

        printAttrList(2);

        while (rs.next()) {
            System.out.printf("%-5d", idx++);
            for (int i = 1; i <= 10; i++) {
                switch (attrLength.get(2).get(i)) {
                    case 5:
                        System.out.printf("| %-5s", rs.getString(i));
                        break;
                    case 10:
                        System.out.printf("| %-10s", rs.getString(i));
                        break;
                    case 20:
                        System.out.printf("| %-20s", rs.getString(i));
                        break;
                    case 30:
                        System.out.printf("| %-30s", rs.getString(i));
                        break;
                    case 50:
                        System.out.printf("| %-50s", rs.getString(i));
                        break;
                }
            }
            System.out.printf("| %-5f년", rs.getFloat(11));
            System.out.println("|");
        }
        System.out.println();
        return idx;
    }

    private int printAttr13(ResultSet rs) throws SQLException {
        int idx = 1;

        printAttrList(3);

        while (rs.next()) {
            System.out.printf("%-5d", idx++);
            for (int i = 1; i <= 10; i++) {
                switch (attrLength.get(3).get(i)) {
                    case 5:
                        System.out.printf("| %-5s", rs.getString(i));
                        break;
                    case 10:
                        System.out.printf("| %-10s", rs.getString(i));
                        break;
                    case 20:
                        System.out.printf("| %-20s", rs.getString(i));
                        break;
                    case 30:
                        System.out.printf("| %-30s", rs.getString(i));
                        break;
                    case 50:
                        System.out.printf("| %-50s", rs.getString(i));
                        break;
                }
            }
            System.out.printf("| %-5f년", rs.getFloat(11));
            for (int i = 12; i <= 13; i++) {
                System.out.printf("| %-10d원", rs.getInt(i));
            }
            System.out.println("|");
        }
        System.out.println();
        return idx;
    }

    private void printAttrList(int idx) {
        int size = attr.get(idx).size();

        for (int i = 0; i < size; i++) {
            String str = attr.get(idx).get(i);
            switch (attrLength.get(idx).get(i)) {
                case 5:
                    System.out.printf("%-5s| ", str);
                    break;
                case 10:
                    System.out.printf("%-10s| ", str);
                    break;
                case 20:
                    System.out.printf("%-20s| ", str);
                    break;
                case 30:
                    System.out.printf("%-30s| ", str);
                    break;
                case 50:
                    System.out.printf("%-50s| ", str);
                    break;
            }
        }
        System.out.println();
    }

    private List<Integer> askFavorite(int size) {
        List<Integer> favorite = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("즐겨찾기를 추가하시려면 해당정보의 번호를 넣어주세요. 추가하시지 않으려면 번호를 제외한 아무 글자나 눌러주세요.");
            String input = scanner.next();
            if (!isNumber(input)) {
                break;
            }

            int num = Integer.parseInt(input);
            if (size >= num) {
                favorite.add(num);
            }
        }

        return favorite;
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Integer> printFavorite(ResultSet rs) throws SQLException {
        int idx = 1;
        boolean flag = false;

        printAttrList(4);

        while (rs.next()) {
            flag = true;
            System.out.printf("%-5d", idx++);
            for (int i = 1; i <= 5; i++) {
                switch (attrLength.get(4).get(i)) {
                    case 5:
                        System.out.printf("| %-5s", rs.getString(i));
                        break;
                    case 10:
                        System.out.printf("| %-10s", rs.getString(i));
                        break;
                    case 20:
                        System.out.printf("| %-20s", rs.getString(i));
                        break;
                    case 30:
                        System.out.printf("| %-30s", rs.getString(i));
                        break;
                    case 50:
                        System.out.printf("| %-50s", rs.getString(i));
                        break;
                }
            }
            System.out.println("|");
        }
        System.out.println();

        if (flag) {
            return askDelFavorite(idx);
        }
        return new ArrayList<>();
    }

    private List<Integer> askDelFavorite(int size) {
        List<Integer> delFavorite = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("즐겨찾기를 삭제하시려면 해당정보의 번호를 넣어주세요. 삭제하시지 않으려면 번호를 제외한 아무 글자나 눌러주세요.");
            String input = scanner.next();
            if (!isNumber(input)) {
                break;
            }

            int num = Integer.parseInt(input);
            if (size >= num) {
                delFavorite.add(num);
            }
        }

        return delFavorite;
    }
}
