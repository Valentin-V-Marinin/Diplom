package balanceReport.presenter;

import balanceReport.model.ConfigData;
import balanceReport.model.ReportSaver;
import balanceReport.repository.iRepo;
import balanceReport.view.iViewBalanceReport;

import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PresenterBalanceReport implements iPresenterBalanceReport{

   private final iRepo db;
   private final iViewBalanceReport balanceReport;
   private final ArrayList<String> resultSelection;
   private ReportSaver reportSaver;
   private String query;
   private final String login;
   private final String password;
   private final ConfigData configData;
   private final DateFormat dateFormat;


    public PresenterBalanceReport(iRepo db, iViewBalanceReport balanceReport, String[] args) {
        this.db = db;
        this.balanceReport = balanceReport;
        this.resultSelection = new ArrayList<>();
        this.query = "";
        this.login = args[0];
        this.password = args[1];
        this.configData = new ConfigData();
        configData.loadConfigInfo(args[2]);
        reportSaver = new ReportSaver();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void getData() {
        db.setConnectDB(configData.getConfigInfoDB(), login, password);
        setQuery(dateFormat.format(balanceReport.sendData()));
        setResultSelection(db.loadInfo(getQuery(),3));
        balanceReport.getData(resultSelection);
    }

    @Override
    public void saveData(int idx, JFrame frm) {
        try {
            Date date = balanceReport.sendData();
            switch (idx){
                case 0: reportSaver.saveTXT(resultSelection, dateFormat.format(date), "", "БАЛАНС", configData);
                    break;
                case 1: reportSaver.saveHTML(resultSelection, dateFormat.format(date), "", "БАЛАНС", configData);
                    break;
                case 2: reportSaver.saveExcel(resultSelection, dateFormat.format(date), "", "БАЛАНС", configData);
                    break;
            }
            JOptionPane.showMessageDialog(frm, "Отчет сохранён в папке отчётов.", "Отчет БАЛАНС", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frm, ex, "Отчет БАЛАНС", JOptionPane.ERROR_MESSAGE);
        }
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
        resultSelection.clear();
        resultSelection.addAll(Arrays.asList(arrResultSelection));
    }

}
