package balanceReport;

import java.util.ArrayList;

public class ReportData{

    private ArrayList<String> resultSelection;
    private DataBase db;



    @Override
    public ArrayList<String> select() {
        resultSelection =  db.dbConnect();
        return resultSelection;
    }
}
