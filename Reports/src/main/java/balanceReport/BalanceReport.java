package balanceReport;

import balanceReport.logic.ConfigData;
import balanceReport.logic.ReportData;
import balanceReport.repository.DataBase;
import balanceReport.view.ViewBalanceReport;

public class BalanceReport {
    public static void main(String[] args) {

        ConfigData configData = new ConfigData();
        configData.loadConfigInfo(args[2]);
        new ViewBalanceReport(args, new ReportData(new DataBase()), configData);

    }
}
