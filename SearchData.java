import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchData {

    private static final int STRING_DATA_SIZE = 6;
    private static final List<String> ORDER = Arrays.asList("대학", "학과", "지역", "설립구분", "중계열", "주야간");
    private static final List<String> ORDER_ENG = Arrays.asList("Name", "Department", "Region", "Establish", "MiddleSeries", "DayNight");
    private static final List<Integer> DEPARTMENT_TABLE_INDEX = Arrays.asList(1, 4, 5);
    private List<List<String>> data; // 대학, 학과, 지역, 설립구분, 중계열, 주야간
    private List<List<Integer>> YearSystem;
    private List<List<Integer>> TuitionFee;

    public void addData(int index, String input) {
        data.get(index).add(input);
    }

    public void addYearSystem(List<Integer> yearSystem) {
        YearSystem.add(yearSystem);
    }

    public void addTuitionFee(List<Integer> tuition) {
        TuitionFee.add(tuition);
    }

    public SearchData() {
        this.data = new ArrayList<>();
        for (int i = 0; i < STRING_DATA_SIZE; i++) {
            this.data.add(new ArrayList<>());
        }

        this.YearSystem = new ArrayList<>();
        this.TuitionFee = new ArrayList<>();
    }

    private boolean isNone() {
        for (int i = 0; i < STRING_DATA_SIZE; i++) {
            if (!data.get(i).isEmpty()) {
                return false;
            }
        }

        if (!YearSystem.isEmpty() || !TuitionFee.isEmpty()) {
            return false;
        }

        return true;
    }

    public String getQuery() {
        if (isNone()) {
            return null;
        }

        String ret = "select * from " + getView() + " where ";

        for (int i = 0; i < STRING_DATA_SIZE; i++) {
            if (!data.get(i).isEmpty()) {
                List<String> curData = data.get(i);
                for (int j = 0; j < curData.size(); j++) {
                    ret += (ORDER_ENG.get(i) + " like \'%" + curData.get(j) + "%\' or ");
                }
                ret = ret.substring(0, ret.length() - 3);
                ret += "and ";
            }
        }

        if (!YearSystem.isEmpty()) {
            for (List<Integer> integerList : YearSystem) {
                ret += (integerList.get(0) + " >= YearSystem and " + integerList.get(1) + " <= YearSystem or ");
            }
            ret = ret.substring(0, ret.length() - 3);
            ret += "and ";
        }

        if (!TuitionFee.isEmpty()) {
            for (List<Integer> integerList : TuitionFee) {
                ret += (integerList.get(0) + " >= TuitionFee and " + integerList.get(1) + " <= TuitionFee or ");
            }
            ret = ret.substring(0, ret.length() - 3);
            ret += "and ";
        }

        return ret.substring(0, ret.length() - 4);
    }

    public String getView() {
        final List<String> view = Arrays.asList("University", "en", "ne", "nn");

        int situation = YearSystem.isEmpty() ? 0 : 1;
        // 학과 테이블의 등록된 attribute, 등록금의 유무에 따른 상황 분류
        for (int idx : DEPARTMENT_TABLE_INDEX) {
            if (!data.get(idx).isEmpty()) {
                situation = 1;
            }
        }

        situation = situation == 0 ? (TuitionFee.isEmpty() ? 0 : 1) : (TuitionFee.isEmpty() ? 2 : 3);

        return view.get(situation);
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < STRING_DATA_SIZE; i++) {
            if (!data.get(i).isEmpty()) {
                ret += (ORDER.get(i) + ": ");
                for (String str : data.get(i)) {
                    ret += (str + " | ");
                }
                ret = ret.substring(0, ret.length() - 3) + '\n';
            }
        }

        if (!YearSystem.isEmpty()) {
            ret += ("수업연한: ");
            for (List<Integer> integerList : YearSystem) {
                ret += ("최대치: " + integerList.get(0) + "최소치: " + integerList.get(1) + " | ");
            }
            ret = ret.substring(0, ret.length() - 3) + '\n';
        }

        if (!TuitionFee.isEmpty()) {
            ret += ("등록금: ");
            for (List<Integer> integerList : TuitionFee) {
                ret += ("최대치: " + integerList.get(0) + "최소치: " + integerList.get(1) + " | ");
            }
            ret = ret.substring(0, ret.length() - 3) + '\n';
        }

        return ret;
    }
}