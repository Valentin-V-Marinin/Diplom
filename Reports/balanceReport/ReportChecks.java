package balanceReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReportChecks {
    public static boolean checkDate(String date) throws NullPointerException{
        boolean result = false;
        if (date.equals(null)) return result;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int month = Integer.parseInt(date, 3, 4, 10);
        if (month > 0 && month < 13) {
            int day = Integer.parseInt(date, 0, 1, 10);
            switch (month){
                case 1:  if (day > 0 && day < 32) result = true; break;
                case 2:  if (day > 0 && day < 30) result = true; break;
                case 3:  if (day > 0 && day < 32) result = true; break;
                case 4:  if (day > 0 && day < 31) result = true; break;
                case 5:  if (day > 0 && day < 32) result = true; break;
                case 6:  if (day > 0 && day < 31) result = true; break;
                case 7:  if (day > 0 && day < 32) result = true; break;
                case 8:  if (day > 0 && day < 32) result = true; break;
                case 9:  if (day > 0 && day < 31) result = true; break;
                case 10: if (day > 0 && day < 32) result = true; break;
                case 11: if (day > 0 && day < 31) result = true; break;
                case 12: if (day > 0 && day < 32) result = true; break;
            }
        }
        return result;
    }

}
