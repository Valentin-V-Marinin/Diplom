package vvm.reportmanager.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigData {
    protected String appHomeDir;
    protected String reportsHomeDir;
    protected String reportsNetDir;
    protected String reportsResultDir;
    protected String configInfoDB;

    public ConfigData() {
        this.reportsHomeDir = "";
        this.reportsNetDir = "";
        this.appHomeDir = "";
        this.reportsResultDir = "";
        this.configInfoDB = "";
    }

    public void loadConfigInfo(){
        Object o = null;
        try {
            o = new JSONParser().parse(new FileReader("C:\\GB\\GB_HomeWorks\\ReportManager\\Config.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject j = (JSONObject) o;
        setAppHomeDir((String) j.get("AppHomeDir"));
        setReportsHomeDir( (String) j.get("ReportsHomeDir"));
        setReportsNetDir( (String) j.get("ReportsNetDir"));
        setReportsResultDir( (String) j.get("ReportsResultDir"));
        setConfigInfoDB( (String) j.get("ConfigInfoDB"));

    }

    public String getAppHomeDir() {
        return appHomeDir;
    }

    public void setAppHomeDir(String appHomeDir) {
        this.appHomeDir = appHomeDir;
    }

    public String getReportsHomeDir() {
        return reportsHomeDir;
    }

    public void setReportsHomeDir(String reportsHomeDir) {
        this.reportsHomeDir = reportsHomeDir;
    }

    public String getReportsNetDir() {
        return reportsNetDir;
    }

    public void setReportsNetDir(String reportsNetDir) {
        this.reportsNetDir = reportsNetDir;
    }

    public String getReportsResultDir() {
        return reportsResultDir;
    }

    public void setReportsResultDir(String reportsResultDir) {
        this.reportsResultDir = reportsResultDir;
    }

    public String getConfigInfoDB() {
        return configInfoDB;
    }

    public void setConfigInfoDB(String configInfoDB) {
        this.configInfoDB = configInfoDB;
    }
}
