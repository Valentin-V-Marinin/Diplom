package balanceReport.logic;

import java.util.ArrayList;

public class ReportData{

    private ArrayList<String> resultSelection;
    private iRepo db;

    public ReportData(iRepo db) {
        ArrayList<String> resultSelection = new ArrayList<>();
        this.db = db;
    }

    public iRepo getDb() {
        return db;
    }


}
