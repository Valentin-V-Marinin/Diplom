package reportAdmin.view;

import javax.swing.*;
import java.awt.*;

public class ReportListPanel extends JPanel {
    private final JList<String> listReports;
    private final JScrollPane jScrollPane;
    private final ListSelectionModel listSelectionModel;

    public ReportListPanel() {
        listReports = new JList<>();
        listReports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = listReports.getSelectionModel();
        jScrollPane = new JScrollPane(listReports);
        jScrollPane.setLayout(new ScrollPaneLayout());
        jScrollPane.setPreferredSize(new Dimension(275, 218));
        add(jScrollPane);
    }

    public JList<String> getListReports() {
        return listReports;
    }

    public void setListReports(String[] reports) {
        listReports.setListData(reports);
    }

    public ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }

}
