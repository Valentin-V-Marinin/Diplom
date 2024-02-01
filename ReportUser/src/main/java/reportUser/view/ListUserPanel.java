package reportUser.view;

import javax.swing.*;
import java.awt.*;

public class ListUserPanel extends JPanel {
    private final JList<String> listUsers;
    private final JScrollPane jScrollPane;
    private final ListSelectionModel listSelectionModel;

    public ListUserPanel() {
        listUsers = new JList<>();
        listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = listUsers.getSelectionModel();
        jScrollPane = new JScrollPane(listUsers);
        jScrollPane.setLayout(new ScrollPaneLayout());
        jScrollPane.setPreferredSize(new Dimension(275, 190));
        add(jScrollPane);
    }

    public JList<String> getListUsers() {
        return listUsers;
    }

    public void setListUsers(String[] users) {
        listUsers.setListData(users);
    }

    public ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }

}
