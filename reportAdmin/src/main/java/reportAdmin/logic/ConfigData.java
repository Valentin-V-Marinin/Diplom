package reportAdmin.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigData {
    protected String appHomeDir;        // домашняя папка приложения
    protected String reportsHomeDir;    // папка с приложениями-отчётами пользователя
    protected String reportsNetDir;     // сетевая хранилище с отчётами
    protected String reportsResultDir;  // папка для документов-отчётов
    protected String configInfoDB;      // информация для подключения к БД
    protected String hostDB;            // имя/адрес хоста БД
    protected String configFile;        // расположение файла конфигурации

    public ConfigData() {
        this.reportsHomeDir = "";
        this.reportsNetDir = "";
        this.appHomeDir = "";
        this.reportsResultDir = "";
        this.configInfoDB = "";
        this.hostDB = "";
        this.configFile = "";
    }

    public void loadConfigInfo(String file){
        Object o = null;
        try {
            o = new JSONParser().parse(new FileReader(file));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject j = (JSONObject) o;
        setAppHomeDir((String) j.get("AppHomeDir"));
        setReportsHomeDir( (String) j.get("ReportsHomeDir"));
        setReportsNetDir( (String) j.get("ReportsNetDir"));
        setReportsResultDir( (String) j.get("ReportsResultDir"));
        setConfigInfoDB( (String) j.get("ConfigInfoDB"));
        setHostDB((String) j.get("HostDB"));
        setConfigFile((String) j.get("ConfigFile"));
    }

    // region GettersSetters
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

    public String getHostDB() {
        return hostDB;
    }
    public void setHostDB(String hostDB) {this.hostDB = hostDB;}

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
    //endregion

    
}
