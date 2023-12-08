package balanceReport.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportData implements iRepo{

    private ArrayList<String> resultSelection;
    public final iRepo db;
    private String query;

    public ReportData(iRepo db) {
        resultSelection = new ArrayList<>();
        this.db = db;
        query = "";
    }

    public String getQuery() {
        return query;
    }
    public void setQuery(String date) {
        this.query = "CALL reports(1,0,0,'" + date + "','');";
    }
    public ArrayList<String> getResultSelection() {
        return resultSelection;
    }

    public void setResultSelection(String[] arrResultSelection) {
        resultSelection.addAll(Arrays.asList(arrResultSelection));
    }


    @Override
    public void setConnectDB(String url, String user, String password) {
        db.setConnectDB(url, user, password);
    }

    @Override
    public String[] loadInfo(String query, int columnNumber) {
        return db.loadInfo(query, columnNumber);
    }

}
