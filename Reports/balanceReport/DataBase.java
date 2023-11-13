package balanceReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
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

    /**
     * Метод для связи с базой данных и получения
     * результата SQL-выборки в виде строкового массива
     * @param param параметр для хранимой процедуры admin()
     * @return строковый массив, результат запроса
     */
    public String[] dbConnect(String[] dbargs) throws RuntimeException {

        String query = "CALL report("+ dbargs[0]+", "+ dbargs[1]+", "+ dbargs[2]+", '"+ dbargs[3]+"', '"+ dbargs[4] +"');";
        List<String> resultArrList = new ArrayList<>();
        String[] result;

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // выполняем SQL-запрос
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                resultArrList.add(String.valueOf(rs.getRow()));
//                if (par1 == 3) {
//                    resultArrList.add(rs.getString(1) + " " + rs.getString(2));
//                } else {
//                    resultArrList.add(rs.getString(1));
//                }
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
