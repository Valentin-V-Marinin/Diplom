package balanceReport.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportSaver {

    /**
     * отступ для выравнивания текстового документа
     */
    private final int WHITE_SPACE_PLACE = 4;

    /**
     * Вывод отчёта в формате HTML
     * @param reportData данные отчёта
     * @param beginDate дата отчета / дата начала периода отчета
     * @param endDate конец периода отчёта
     * @param reportName название отчёта
     * @param configData конфигурационные данные - папка для сохранения отчёта
     * @throws IOException
     */
    public void saveHTML(ArrayList<String> reportData, String beginDate, String endDate, String reportName, ConfigData configData) throws IOException {

        String resultFile = configData.getReportsResultDir() + reportName + "_" + beginDate
                + ((endDate.length() == 0) ? ".html" : "_" + endDate + ".html");
        FileWriter fw = new FileWriter(resultFile);

        fw.write("<!DOCTYPE html>\n");
        fw.write("<html>\n");
        fw.write("<head>\n<title>" + reportName + "</title>\n</head>\n");

        fw.write("<h2 align=center>Отчёт " + reportName + "</h2>\n");
        fw.write("<h3 align=center>по состоянию на " + beginDate + "</h3>\n");

        fw.write("<body style=background-color:azure;>\n");
        fw.write("<table border=\"4\" bordercolor=\"#000000\" cellspacing=\"0\" cellpadding=\"0\"width= \"600\" height=\"150\" align=center>>\n");

        DecimalFormat dF = new DecimalFormat("#,###.##");
        for (int i = 0; i < reportData.size(); i++) {
            fw.write("<tr\n>");
            String[] str = reportData.get(i).split(";");
            for (int j = 0; j < str.length; j++) {
                if (j == str.length - 1) {
                    fw.write("<td align=right>" + dF.format(Double.parseDouble(str[j])) + "</td>\n");
                } else {
                    fw.write("<td align=left>" + str[j] + "</td>\n");
                }
            }
            fw.write("</tr>\n");
        }

        fw.write("</table>\n");
        fw.write("</body>\n");
        fw.write("</html>\n");
        fw.close();
    }


    /**
     * Вывод отчёта в текстовом формате
     * @param reportData данные отчёта
     * @param beginDate дата отчета / дата начала периода отчета
     * @param endDate конец периода отчёта
     * @param reportName название отчёта
     * @param configData конфигурационные данные - папка для сохранения отчёта
     * @throws IOException
     */
    public void saveTXT(ArrayList<String> reportData, String beginDate, String endDate, String reportName, ConfigData configData) throws IOException {

        String resultFile = configData.getReportsResultDir() + reportName + "_" + beginDate
                + ((endDate.length() == 0) ? ".txt" : "_" + endDate + ".txt");
        FileWriter fw = new FileWriter(resultFile);

        // количество колонок
        String[] columns = reportData.get(0).split(";");
        int columnsNumber = columns.length;

        // максимальный размер колонки
        int[] maxSizeCol = new int[columnsNumber];
        for (int i = 0; i < reportData.size(); i++) {
            String[] strLength = reportData.get(i).split(";");
            for (int j = 0; j < strLength.length; j++) {
                if (maxSizeCol[j] < strLength[j].length()) maxSizeCol[j] = strLength[j].length();
            }
        }

        int headLength = 0;
        for (Integer item: maxSizeCol) {
            headLength+= item;
        }
        fw.write(formatString((headLength + columnsNumber* WHITE_SPACE_PLACE),"Отчёт " + reportName,0) + "\n");
        fw.write(formatString((headLength + columnsNumber* WHITE_SPACE_PLACE),"по состоянию на " + beginDate,0) + "\n");
        fw.write("\n");

        DecimalFormat dF = new DecimalFormat("#,###.##");
        for (int i = 0; i < reportData.size(); i++) {
            String[] str = reportData.get(i).split(";");
            for (int j = 0; j < str.length; j++) {
                if (j == str.length - 1) {
                    fw.write(" " + formatString(maxSizeCol[j], dF.format(Double.parseDouble(str[j])),1) + "\n");
                } else {
                    fw.write( formatString(maxSizeCol[j], str[j], -1)+ "  ");
                }
            }
        }

        fw.close();
    }

    /**
     * Вывод отчёта в формате Excel
     * @param reportData данные отчёта
     * @param beginDate дата отчета / дата начала периода отчета
     * @param endDate конец периода отчёта
     * @param reportName название отчёта
     * @param configData конфигурационные данные - папка для сохранения отчёта
     * @throws IOException
     */
    public void saveExcel(ArrayList<String> reportData, String beginDate, String endDate, String reportName, ConfigData configData) throws IOException {

        String resultFile = configData.getReportsResultDir() + reportName + "_" + beginDate
                + ((endDate.length() == 0) ? ".xlsx" : "_" + endDate + ".xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(reportName);

        // количество колонок
        String[] columns = reportData.get(0).split(";");
        int columnsNumber = columns.length;
        int rowsNumber = reportData.size();

        Object[][] arrReportData = new Object[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            columns = reportData.get(i).split(";");
            for (int j = 0; j < columnsNumber; j++) {
                arrReportData[i][j] = columns[j];
            }
        }


        int rowNum = 0; String fieldStrSumma = "";
        for (Object[] item: arrReportData) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : item) {
                Cell cell = row.createCell(colNum);
                if (colNum == columnsNumber-1) {
                    fieldStrSumma = ((String) field);
                    fieldStrSumma.replace('.',',');
                    cell.setCellValue(Double.parseDouble(fieldStrSumma));
                } else {
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
                colNum++;
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(resultFile);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Форматирование строки для текстового документа
     * @param columnLength длина колонки
     * @param str содержимое колонки
     * @param align выравнивание -1 - по левому краю, 0 - по центру, 1 - по правому краю
     * @return отформатированное значение str
     */
    private String formatString(int columnLength, String str, int align){
        String result = "";
        switch (align){
            case -1: result = str + " ".repeat(columnLength + WHITE_SPACE_PLACE - str.length()); break;
            case  0: result = " ".repeat((columnLength + WHITE_SPACE_PLACE - str.length())/2) + str; break;
            case  1: result = " ".repeat(columnLength + WHITE_SPACE_PLACE - str.length()) + str; break;
        }
        return result;
    }
}