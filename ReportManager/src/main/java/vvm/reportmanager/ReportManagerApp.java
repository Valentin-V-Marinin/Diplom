package vvm.reportmanager;

import vvm.reportmanager.logic.ConfigData;
import vvm.reportmanager.logic.User;
import vvm.reportmanager.repository.DataBase;
import vvm.reportmanager.view.MainForm;

import javax.swing.*;

public class ReportManagerApp {
    public static void main(String[] args) {
        ConfigData configData = new ConfigData();
        configData.loadConfigInfo(args[0]);
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run(){
                       new MainForm(new User(new DataBase()), configData);
                    }
                }
        );
    }
}
