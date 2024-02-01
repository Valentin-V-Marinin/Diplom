package vvm.reportmanager.logic;

import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * класс для работы с отчетами, с набором полей для идентификации отчета
 * и набором методов для работы с этими отчетами:
 * callReport()           -(вызов отчета),
 * reportHashCount()      -(расчет hash-числа отчёта),
 * reportReplacement()    -(замена старой версии отчёта на новую)
 */

public class Report{

    private String reportViewName;      // название отчёта выводимое для пользователя
    private String reportAppName;       // название архива (jar) в который упакован отчет
    private String reportHash;          // hash отчета для обновления версий
    private final HashCount hashCount;  // объект класса для расчета hash отчета

    public Report() {
        this.reportViewName = "";
        this.reportAppName = "";
        this.reportHash = "";
        this.hashCount = new HashCount();
    }

    /**
     * Метод для вызова/загрузки отчета на исполнение
     *
     * @param login      логин пользователя для запроса данных отчетом из БД
     * @param pass       пароль пользователя для запроса данных отчетом из БД
     * @param configFile место расположения конфигурационного файла
     * @param path       место расположения jar-архивов с отчетами
     * @param reportName данный параметр использует данные поля reportAppName
     * @return возвращает ссылку на процесс, вызвавший отчёт на исполнение
     * @throws RuntimeException метод предает исключение на обработку в вызывающий метод
     */
    public Process callReport(String login, String pass, String configFile, String path, String reportName) throws RuntimeException{
        Process p;
        try {
            String str = "java -jar " + path + reportName + " " + login + " " + pass + " " + configFile;
            p = Runtime.getRuntime().exec(str);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return p;
    }

    /**
     * Расчёт hash для файла переданного параметром
     * @param fileName - файл для расчёта hash
     * @return строка с hash числом
     */
    public String reportHashCount(String fileName){
        return hashCount.getSHA256HashForFile(fileName);
    }

    /**
     * Метод для замены старой версии отчёта его новой версией
     * @param netFileName - файл новой версии отчёта, расположенный в сетевом хранилище
     * @param localFileName - файл старой версии, расположенный на машине пользоваетеля
     * @throws IOException
     */
    private void reportReplacement(String netFileName, String localFileName) throws IOException {
        InputStream netFile = null;
        OutputStream localFile = null;
        try {
            netFile = new FileInputStream(netFileName);
            localFile = new FileOutputStream(localFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = netFile.read(buffer)) > 0) {
                localFile.write(buffer, 0, length);
            }
        } finally {
            if (netFile != null) {
                netFile.close();
            }
            if (localFile != null) {
                localFile.close();
            }
        }
    }

    /**
     * Метод сравнивает версии отчётов в локальном и сетевом хранилищах, при
     * несовпадении версий(hash) копирует файл с отчётом из сетевого хранилища в локальное
     * @param checkList - набор отчётов пользователя
     * @param netPath - путь к сетевому хранилищу отчётов
     * @param localPath - путь к локльному хранилищу отчётов
     */
    public void checkReportVersion(List<Report> checkList, String netPath, String localPath) throws IOException, RuntimeException {

        String hashNetFile = "";
        String hashLocalFile = "";
        String localFileName = "";
        String netFileName = "";

        for (int i = 0; i < checkList.size(); i++) {
            hashNetFile = checkList.get(i).getReportHash();
            localFileName = localPath + checkList.get(i).getReportAppName();
            netFileName = netPath + checkList.get(i).getReportAppName();

            // если файл отсутствует в локальном хранилище отчётов
            // (пользователю назначили новый отчёт)
            File file = new File(localFileName);
            if (file.exists()) {
                hashLocalFile = reportHashCount(localPath + checkList.get(i).getReportAppName());
            } else {
                hashLocalFile = "0";
            }

            // проверям наличие файла отчёта в сетевом хранилище отчётов, ссли файл отсутствует
            // у пользователя остаётся старая версия, сообщаем об этом в исключении
            File netFile = new File(netFileName);
            if (netFile.exists()) {
                if (!hashNetFile.equals(hashLocalFile)) reportReplacement(netFileName, localFileName);
            } else {
                throw new RuntimeException("Файл отчёта отсутствует в сетевом хранилище!");
            }
        }
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
