package reportUser.view;

import reportUser.presenter.iSetReportsPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UserReportsPanel extends JPanel implements iViewSetReports {

    private iSetReportsPresenter setReportsPresenter;
    private Map<Integer, String> fullRepSetMap;
    private Map<Integer, Integer> userRepSetMap;
    private final JList<String> fullReportsSetJL;
    private final JList<String> userReportsSetJL;
    private final ViewUserReport vur;

    public UserReportsPanel(ViewUserReport vur) {
        UserReportsPanel userReportsPanel = this;
        this.vur = vur;
        userRepSetMap = new HashMap<>();
        fullRepSetMap = new HashMap<>();

        JLabel fullReportsSetLab = new JLabel();
        fullReportsSetLab.setText("весь набор отчётов");
        JLabel userReportsSetLab = new JLabel();
        userReportsSetLab.setText("Набор отчётов пользователя");
        fullReportsSetJL = new JList<>(); fullReportsSetJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane fullRSjsp = new JScrollPane(fullReportsSetJL);
        userReportsSetJL = new JList<>(); userReportsSetJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userRSjsp = new JScrollPane(userReportsSetJL);

        setLayout(null);
        int width = 250; int heightLab = 15; int heightJSP = 170;
        fullReportsSetLab.setBounds(10, 5, width, heightLab);
        userReportsSetLab.setBounds(340, 5, width, heightLab);
        fullRSjsp.setBounds(10, 25, width, heightJSP);
        fullRSjsp.setPreferredSize(new Dimension(width, heightJSP));
        userRSjsp.setBounds(340, 25, width, heightJSP);
        userRSjsp.setPreferredSize(new Dimension(width, heightJSP));
        add(fullReportsSetLab); add(fullRSjsp);
        add(userReportsSetLab); add(userRSjsp);

        Icon iconLoad = new ImageIcon("C:\\GB\\GB_HomeWorks\\ReportUser\\src\\main\\resources\\load.png");
        Icon iconAdd  = new ImageIcon("C:\\GB\\GB_HomeWorks\\ReportUser\\src\\main\\resources\\add.png");
        Icon iconDel  = new ImageIcon("C:\\GB\\GB_HomeWorks\\ReportUser\\src\\main\\resources\\del.png");
        JButton loadBtn = new JButton(iconLoad); loadBtn.setComponentPopupMenu(new JPopupMenu("Загрузить отчёты"));
        JButton addBtn  = new JButton(iconAdd);
        JButton delBtn  = new JButton(iconDel);
        loadBtn.setBounds(282, 25, 36, 36);
        addBtn.setBounds(282, 90, 36, 36);
        delBtn.setBounds(282, 130, 36, 36);
        add(loadBtn); add(addBtn); add(delBtn);

        loadBtn.addActionListener(e -> {
            if (vur.getSelectedUser().getId() == 0 || vur.getSelectedUser() == null) return;   // пользователь не выбран
            setReportsPresenter.loadFullSetReports();
            setReportsPresenter.loadUserSetReports(vur.getSelectedUser().getId());
        });

        addBtn.addActionListener(e -> {
            if (fullReportsSetJL.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(userReportsPanel, "Отчёт для добавления не выбран!",
                        "ПОЛЬЗОВАТЕЛЬ", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            setReportsPresenter.addReportToUser();
            loadBtn.doClick();
        });

        delBtn.addActionListener(e -> {
            if (userReportsSetJL.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(userReportsPanel, "Отчёт для удаления не выбран!",
                        "ПОЛЬЗОВАТЕЛЬ", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            setReportsPresenter.removeReportFromUser();
            loadBtn.doClick();
        });
    }

    public void clearData(){
        String[] empty = new String[0];
        fullReportsSetJL.setListData(empty);
        userReportsSetJL.setListData(empty);
    }


    @Override
    public void getWholeSetReports(HashMap<Integer, String> fullRepSetMap) {
        this.fullRepSetMap = fullRepSetMap;
        String[] fullRepSetStr = new String[fullRepSetMap.size()];
        int idx = 0;
        for (String item: fullRepSetMap.values()) {
            fullRepSetStr[idx++] = item;
        }
        fullReportsSetJL.setListData(fullRepSetStr);
    }

    @Override
    public void getUserSetReports(HashMap<Integer, Integer> userRepSetMap) {
        int idx = 0;
        this.userRepSetMap = userRepSetMap;
        String[] userRepSetStr = new String[userRepSetMap.size()];
        for (Integer item: userRepSetMap.keySet()) {
            userRepSetStr[idx++] = fullRepSetMap.get(item);
        }
        userReportsSetJL.setListData(userRepSetStr);
    }

    @Override
    public int[] addReportToUser() {
        int[] result = new int[2];
        int reportID = 0;
        for (Integer item: fullRepSetMap.keySet()) {
            if (fullRepSetMap.get(item) == fullReportsSetJL.getSelectedValue()) {
                reportID = item; break;
            }
        }
        result[0] = vur.getSelectedUser().getId();
        result[1] = reportID;
        return result;
    }

    @Override
    public int[] removeReportFromUser() {
        int[] result = new int[2];
        int reportID = 0;
        for (Integer item: userRepSetMap.keySet()) {
            if (fullRepSetMap.get(item) == userReportsSetJL.getSelectedValue()) {
                reportID = item; break;
            }
        }
        result[0] = vur.getSelectedUser().getId();
        result[1] = reportID;
        return result;
    }

    public void setPresenter(iSetReportsPresenter setReportsPresenter){
        this.setReportsPresenter = setReportsPresenter;
    }

    public iSetReportsPresenter getPresenter(){
        return setReportsPresenter;
    }
}
