package vvm.reportmanager.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит данные пользователя: имя, логин, пароль;
 * перечень отчетов, доступных данному пользовыателю и метод для его получения:
 * методы для работы с отчетами. При создании экземпляра класса подключается БД,
 * поддерживающая интерфейс iRepo
 */
public class User {
    private String userName;                // имя пользователя
    private final AccessDB accessDB;        // класс для работы с доступом к БД
    private Report report;                  // класс для работы с отчётами
    private List<Report> userListReports;   // перечень отчётов доступных пользователю
    private iRepo db;                       // подключаемая БД

    public User(iRepo db) {
        this.db = db;
        userName = "";
        accessDB = new AccessDB(db);
        userListReports = new ArrayList<>();
        report = new Report();
    }

    /**
     * Получение набора отчётов конкретного пользователя в виде списка
     * объектов типа Report, где каждый объект содержит имя отчета,
     * jar-архив отчёта и hash отчёта
     * @return List<Report>
     */
    public List<Report> shapeListReports(){
        String query = "CALL ADMIN(2,0,0,'"+ accessDB.getLogin() +"','" + accessDB.getPassword() + "');";
        String[] setReports = db.loadInfo(query, 3);
        Report[] reports = new Report[setReports.length];
        for (int i = 0; i < setReports.length; i++) {
            String[] str = setReports[i].split(" ");
            reports[i] = new Report();
            reports[i].setReportViewName(str[0]);
            reports[i].setReportAppName(str[1]);
            reports[i].setReportHash(str[2]);
            userListReports.add(reports[i]);
        }
        return userListReports;
    }

    /**
     * Метод возвращает набор отчетов пользователя, предназначенный
     * для отображения этого набора отчётов пользователю
     * @return возвращает набор отчётов в виде массива типа String
     */
    public String[] getViewUserListReports(){
        String[] result = new String[userListReports.size()];
        for (int i = 0; i < userListReports.size(); i++) {
            result[i] = userListReports.get(i).getReportViewName();
        }
        return result;
    }

    /**
     * Метод получает имя пользователя из БД
     * @return возвращает строку с именем пользователя
     */
    public void loadUserName(){
        String query = "call ADMIN(3,0,0,'" + accessDB.getLogin() + "', '" + accessDB.getPassword() + "');";
        String[] userNameArr = db.loadInfo(query, 2);
        userName = userNameArr[0];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AccessDB getAccessDB() {
        return accessDB;
    }

    public void setAccess(String login, String password) {
        this.accessDB.setLogin(login);
        this.accessDB.setPassword(password);
    }

    public List<Report> getUserListReports() {
        return userListReports;
    }

    public void setUserListReports(List<Report> userListReports) {
        this.userListReports = userListReports;
    }

    public Report getReport() {
        return report;
    }

    public iRepo getDb() {
        return db;
    }
}
