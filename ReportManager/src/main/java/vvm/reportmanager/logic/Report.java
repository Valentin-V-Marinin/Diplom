package vvm.reportmanager.logic;

import vvm.reportmanager.repository.DataBase;

import java.io.IOException;

/**
 * класс для работы с отчетами, с набором полей для идентификации отчета
 * и набором методов для работы с этими отчетами:
 * callReport()           -(вызов отчета),
 * reportHashCount()      -(расчет hash-числа отчёта),
 * reportReplacement()    -(замена старой версии отчёта на новую)
 */

public class Report{

    private String reportViewName;   // название отчёта выводимое для пользователя
    private String reportAppName;    // название архива (jar) в который упакован отчет
    private String reportHash;       // hash отчета для обновления версий
    private HashCount hashCount;     // объект класса для расчета hash отчета

    public Report() {
        this.reportViewName = "";
        this.reportAppName = "";
        this.reportHash = "";
        this.hashCount = new HashCount();
    }

    /**
     * Метод для вызова/загрузки отчета на исполнение
     * @param login логин пользователя для запроса данных отчетом из БД
     * @param pass  пароль пользователя для запроса данных отчетом из БД
     * @param path  место расположения jar-архивов с отчетами
     * @param reportName данный параметр использует данные поля reportAppName
     * @return возвращает ссылку на процесс, вызвавший отчёт на исполнение
     * @throws RuntimeException метод предает исключение на обработку в вызывающий метод
     */
    public Process callReport(String login, String pass, String path, String reportName) throws RuntimeException{
        Process p;
        try {
            //String str = "java -classpath C:\\GB\\Reports balanceReport.BalanceReport " + login_pass[0] + " " +  login_pass[1];
            //String str = "java -jar C:\\GB\\ReportManager\\Reports\\balanceReport.jar " + login_pass[0] + " " +  login_pass[1];
            String str = "java -jar " + path + reportName + " " + login + " " + pass;
            p = Runtime.getRuntime().exec(str);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return p;
    }

    public String reportHashCount(){
        return "";
    }

    public void reportReplacement(){

    }

    public String getReportViewName() {
        return reportViewName;
    }

    public void setReportViewName(String reportViewName) {
        this.reportViewName = reportViewName;
    }

    public String getReportAppName() {
        return reportAppName;
    }

    public void setReportAppName(String reportAppName) {
        this.reportAppName = reportAppName;
    }

    public String getReportHash() {
        return reportHash;
    }
    public void setReportHash(String reportHash) {
        this.reportHash = reportHash;
    }
}
