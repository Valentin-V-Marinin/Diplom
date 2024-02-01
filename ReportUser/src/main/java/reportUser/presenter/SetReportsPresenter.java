package reportUser.presenter;

import reportUser.logic.FullReportsSet;
import reportUser.logic.UserReportsSet;
import reportUser.repository.iFullSetReportsRepo;
import reportUser.repository.iUserSetReportsRepo;
import reportUser.view.iViewSetReports;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SetReportsPresenter implements iSetReportsPresenter{

    private final iFullSetReportsRepo<FullReportsSet> fullSetReportsRepo;
    private final iUserSetReportsRepo<UserReportsSet> userSetReportsRepo;
    private List<UserReportsSet> userReportsSetList;
    private HashMap<Integer, Integer> userReportsSetMap;
    private final iViewSetReports viewSetReports;


    public SetReportsPresenter(iFullSetReportsRepo<FullReportsSet> fullSetReportsRepo,
                               iUserSetReportsRepo<UserReportsSet> userSetReportsRepo,
                               iViewSetReports viewSetReports) {
        this.fullSetReportsRepo = fullSetReportsRepo;
        this.userSetReportsRepo = userSetReportsRepo;
        this.viewSetReports = viewSetReports;
    }

    /**
     * получение пользовательского набора отчетов
     * @param id - пользователь (его id из таблицы users)
     */
    @Override
    public void loadUserSetReports(int userID) {
        userReportsSetList = userSetReportsRepo.getUserReports(userID);
        userReportsSetMap = new HashMap<>();
        for (int i = 0; i < userReportsSetList.size(); i++) {
            userReportsSetMap.put(userReportsSetList.get(i).getReportsID(), userReportsSetList.get(i).getUsersID());
        }
        viewSetReports.getUserSetReports(userReportsSetMap);
    }

    /**
     * Загрузка полного набора отчетов пользователя
     */
    @Override
    public void loadFullSetReports(){
        List<FullReportsSet> fullReportsSetList = fullSetReportsRepo.getFullSetReports();
        HashMap<Integer, String> fullReportsSetMap = new HashMap<>();
        for (int i = 0; i < fullReportsSetList.size(); i++) {
            fullReportsSetMap.put(fullReportsSetList.get(i).getId(), fullReportsSetList.get(i).getRepname());
        }
        viewSetReports.getWholeSetReports(fullReportsSetMap);
    }

    @Override
    public void addReportToUser() {
        int[] addReport = viewSetReports.addReportToUser();
        int userID   = addReport[0];
        int reportID = addReport[1];
        if (userReportsSetMap.get(reportID) == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());
            UserReportsSet urs = null;
            try {
                urs = new UserReportsSet(0, userID, reportID, 1, dateFormat.parse(date), null);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            userSetReportsRepo.addReport(urs);
        }
    }

    /**
     * Удаление отчета из списа отчетов доступных пользователю
     * @param userID пользователь у которого удаляют отчёт (таблица users)
     * @param ReportID удаляемый отчёт (таблица user_reports)
     */
    @Override
    public void removeReportFromUser() {
        int[] addReport = viewSetReports.removeReportFromUser();
        int userID   = addReport[0];
        int reportID = addReport[1];
        UserReportsSet deletedReport = new UserReportsSet();
        for (int i = 0; i < userReportsSetList.size(); i++) {
            if (userReportsSetList.get(i).getUsersID() == userID &&
                    userReportsSetList.get(i).getReportsID() == reportID){
                deletedReport = userReportsSetList.get(i);
                break;
            }
        }
        userSetReportsRepo.delReport(deletedReport);
    }

    @Override
    public void closeDB() {
        fullSetReportsRepo.closeDB();
        userSetReportsRepo.closeDB();
    }


}
