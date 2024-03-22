package balanceReport.repository;


import java.sql.*;
import java.util.ArrayList;

public class DataBase implements iRepo {
    // JDBC URL, username и password  MySQL server-а
    private String url;
    private String user;
    private String password;

    // JDBC переменные для подключения и управления подключением
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public DataBase(String url, String user, String password) {
        if (url.length() == 0) {
            this.url = "jdbc:mysql://localhost:3306/enterprise";
        } else {
            this.url = url;
        }
        this.user = user;
        this.password = password;
    }

    public DataBase(){
        url = "";
        user = "";
        password = "";
    }


    @Override
    public void setConnectDB(String url, String user, String password) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    /**
     * Метод для связи с базой данных и получения
     * результата SQL-выборки в виде строкового массива
     *
     * @param query        - готовый SQL запрос к БД
     * @param columnNumber - количество полей в возвращаемом списке
     * @return строковый массив
     * @throws RuntimeException
     */
    public String[] loadInfo(String query, int columnNumber) throws RuntimeException {

        ArrayList<String> resultArrList = new ArrayList<>();
        String[] result;

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                if (columnNumber == 1) {
                    resultArrList.add(rs.getString(1));
                } else {
                    String strResult = "";
                    for (int i = 1; i <= columnNumber; i++) {
                        strResult += rs.getString(i) + ";";
                    }
                    resultArrList.add(strResult);
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx.getMessage());
        } finally {

            result = new String[resultArrList.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = resultArrList.get(i);
            }

            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

        return result;
    }

}
