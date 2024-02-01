package reportUser.view;

import java.util.HashMap;

public interface iViewSetReports {

    void getWholeSetReports(HashMap<Integer, String> set);
    void getUserSetReports(HashMap<Integer, Integer> set);
    int[] addReportToUser();
    int[] removeReportFromUser();
}
