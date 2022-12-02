import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SearchData {

    private static final int STRING_DATA_SIZE = 6;
    private static final List<String> order = Arrays.asList("대학", "학과", "지역", "설립구분", "중계열", "주야간");
    private static final List<String> orderEng = Arrays.asList("Name", "Department", "Region", "Establish", "MiddleSeries", "DayNight");


    private List<List<String>> data; // 대학, 학과, 지역, 설립구분, 중계열, 주야간
    private List<List<Integer>> YearSystem;
    private List<List<Integer>> AdmissionFee;

    public void addData(int index, String input) {
        data.get(index).add(input);
    }
    public void addYearSystem(List<Integer> yearSystem) {
        YearSystem.add(yearSystem);
    }
    public void addAdmissionFee(List<Integer> admissionFee) {
        AdmissionFee.add(admissionFee);
    }

    public SearchData() {
        this.data = new ArrayList<>();
        for (int i = 0; i < STRING_DATA_SIZE; i++) {
            this.data.add(new ArrayList<>());
        }

        this.YearSystem = new ArrayList<>();
        this.AdmissionFee = new ArrayList<>();
    }

    private boolean isNone() {
        for(int i=0;i<STRING_DATA_SIZE;i++){
            if(!data.get(i).isEmpty()) {
                return false;
            }
        }

        if(!YearSystem.isEmpty() && !AdmissionFee.isEmpty()){
            return false;
        }

        return true;
    }

    public String getQuery() {
        final List<String> view = Arrays.asList("University","en","ne","nn");

        // 학과, 등록금의 유무에 따른 상황 분류
        int situation = data.get(1).isEmpty() ? (AdmissionFee.isEmpty() ? 0 : 1) : (AdmissionFee.isEmpty() ? 2 : 3);

        if(isNone()) {
            return null;
        }
        String ret = "select * from " + view.get(situation) + " where ";

        for(int i=0;i<STRING_DATA_SIZE;i++){
            if(!data.get(i).isEmpty()) {
                List<String> curData = data.get(i);
                for(int j=0;j<curData.size();j++) {
                    ret+=(orderEng.get(i)+" like \'%" + curData.get(j) + "%\' and ");
                }
            }
        }

        if(!YearSystem.isEmpty()){
            for(List<Integer> integerList:YearSystem) {
                ret+=(integerList.get(0) + " > YearSystem and " + integerList.get(1) + " < YearSystem and ");
            }
        }

        if(!AdmissionFee.isEmpty()){
            for(List<Integer> integerList:AdmissionFee) {
                ret+=(integerList.get(0) + " > AdmissionFee and " + integerList.get(1) + " < AdmissionFee and ");
            }
        }

        return ret.substring(0, ret.length() - 4);
    }
    @Override
    public String toString() {
        String ret = "";
        for(int i=0;i<STRING_DATA_SIZE;i++){
            if(!data.get(i).isEmpty()) {
                ret+=(order.get(i)+": ");
                for(String str: data.get(i)) {
                    ret+=(str+" | ");
                }
                ret+='\n';
            }
        }

        if(!YearSystem.isEmpty()){
            ret+=("수업연한: ");
            for(List<Integer> integerList:YearSystem) {
                ret+=("최대치: " + integerList.get(0) + "최소치: " + integerList.get(1) + " | ");
            }
            ret+='\n';
        }

        if(!AdmissionFee.isEmpty()){
            ret+=("등록금: ");
            for(List<Integer> integerList:AdmissionFee) {
                ret+=("최대치: " + integerList.get(0) + "최소치: " + integerList.get(1) + " | ");
            }
            ret+='\n';
        }

        return ret;
    }
}