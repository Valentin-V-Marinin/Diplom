package balanceReport;

import balanceReport.view.ViewBalanceReport;

public class BalanceReport {
    public static void main(String[] args) {
        String[] arr = new String[2];
        arr[0] = "vgb";
        arr[1] = "1234567";
        new ViewBalanceReport(arr);
    }
}
