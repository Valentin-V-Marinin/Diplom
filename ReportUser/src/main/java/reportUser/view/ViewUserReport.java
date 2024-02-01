package reportUser.view;

import reportUser.logic.User;
import reportUser.presenter.SetReportsPresenter;
import reportUser.presenter.UserDetailsPresenter;
import reportUser.presenter.UserLoginPresenter;
import reportUser.presenter.iUserDetailsPresenter;
import reportUser.repository.FullSetReportsDB;
import reportUser.repository.UserDetailDB;
import reportUser.repository.UserLoginDB;
import reportUser.repository.UserSetReportsDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ViewUserReport extends JFrame implements iViewDetailsData<User>{

    private DetailUserPanel detailUserPanel;
    private ListUserPanel listUserPanel;
    private List<User> userList;
    private User selectedUser;
    private final ViewUserReport mainFrame;
    private iUserDetailsPresenter<User> userDetailsPresenter;


    public ViewUserReport(){

        mainFrame = this;
        setTitle("ПОЛЬЗОВАТЕЛЬ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620,330));
        setResizable(false);

        setGUI(mainFrame);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setGUI(ViewUserReport vur){

        selectedUser = null;

        // панель заголовок
        JPanel headpanel = new JPanel();
        headpanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 30));
        JLabel headLabel = new JLabel();
        headpanel.add(headLabel);

        // закладки
        JTabbedPane tabbedPane = new JTabbedPane();

        // панель с информацией о пользователе (ФИО, ДР и т.д.)
        JPanel userDataPanel = new JPanel();
        tabbedPane.setTabPlacement(1);
        tabbedPane.addTab("Пользователь", userDataPanel);
        userDataPanel.setLayout(new BorderLayout());

        // панель с информацией доступа
        LoginDataPanel loginDataPanel = new LoginDataPanel(vur);
        loginDataPanel.setPresenter(new UserLoginPresenter(loginDataPanel, new UserLoginDB("userLogin.cfg.xml")));
        tabbedPane.addTab("Пароль", loginDataPanel);

        // панель с набором отчётов
        UserReportsPanel userReportsPanel = new UserReportsPanel(vur);
        userReportsPanel.setPresenter(  new SetReportsPresenter(
                                            new FullSetReportsDB("fullSetReports.cfg.xml"),
                                            new UserSetReportsDB("userSetReports.cfg.xml"),
                                            userReportsPanel));
        tabbedPane.addTab("Отчёты", userReportsPanel);

        // панели параметров и выборки
        detailUserPanel = new DetailUserPanel();
        GridLayout detailUserLayout = new GridLayout();
        detailUserLayout.setColumns(2); detailUserLayout.setRows(8);
        detailUserPanel.setLayout(detailUserLayout);
        detailUserPanel.setPreferredSize(new Dimension(210,220));

        listUserPanel = new ListUserPanel();
        setController(new UserDetailsPresenter(this, new UserDetailDB("userDetail.cfg.xml")));

        userDataPanel.add(listUserPanel, BorderLayout.CENTER);
        userDataPanel.add(detailUserPanel, BorderLayout.WEST);


        // панель кнопок управления списком
        JPanel btnPanel = new JPanel();
        userDataPanel.add(btnPanel, BorderLayout.EAST);
        btnPanel.setPreferredSize(new Dimension(110, 220));
        btnPanel.setLayout(new FlowLayout());
        JButton loadBtn = new JButton("загрузить");
        JButton clearBtn = new JButton("очистить");
        JButton addBtn = new JButton("добавить");
        JButton editBtn = new JButton("изменить");
        JButton delBtn = new JButton("удалить");
        loadBtn.setPreferredSize(new Dimension(100, 25));
        clearBtn.setPreferredSize(new Dimension(100, 25));
        addBtn.setPreferredSize(new Dimension(100, 25));
        editBtn.setPreferredSize(new Dimension(100, 25));
        delBtn.setPreferredSize(new Dimension(100, 25));
        btnPanel.add(loadBtn); btnPanel.add(clearBtn); btnPanel.add(addBtn);
        btnPanel.add(editBtn); btnPanel.add(delBtn);

        // панель кнопок управления отчетом (закрыть отчет)
        JPanel btnRepPanel = new JPanel();
        btnRepPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 35));
        btnRepPanel.setLayout(new FlowLayout());
        JButton closeBtn = new JButton("выйти");
        closeBtn.setPreferredSize(new Dimension(100, 25));
        btnRepPanel.add(closeBtn);

        add(headpanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(btnRepPanel, BorderLayout.SOUTH);

 // обработчики нажатия кнопок
        closeBtn.addActionListener(e ->{
            try {
                userReportsPanel.getPresenter().closeDB();
                loginDataPanel.getPresenter().closeDB();
                userDetailsPresenter.closeDB();
                System.exit(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });

        addBtn.addActionListener(e -> {
            try {
                userDetailsPresenter.add();
                loadBtn.doClick();
                clearBtn.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });


        editBtn.addActionListener(e -> {
            try {
                userDetailsPresenter.update();
                loadBtn.doClick();
                clearBtn.doClick();
                JOptionPane.showMessageDialog(mainFrame, "Данные обновлены!",
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });


        loadBtn.addActionListener(e -> {
            try {
                clearBtn.doClick();
                userDetailsPresenter.load();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            detailUserPanel.clearData();
            loginDataPanel.clearData();
            userReportsPanel.clearData();
            headLabel.setText("");
            addBtn.setEnabled(true);
            editBtn.setEnabled(false);
            delBtn.setEnabled(false);
        });

        delBtn.addActionListener(e -> {
            try {
                loginDataPanel.getPresenter().loadUserLogInData(selectedUser.getId());
                loginDataPanel.getPresenter().deleteUserLogInData();
                userDetailsPresenter.delete();
                loadBtn.doClick();
                clearBtn.doClick();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });


        listUserPanel.getListSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                loginDataPanel.clearData();
                userReportsPanel.clearData();
                changeSelectPositionList(headLabel, tabbedPane, btnPanel);
            }
        });
    }

    private void changeSelectPositionList(JLabel selectedUserLabel, JTabbedPane tabbedPane, JPanel btnPanel){
        detailUserPanel.clearData();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int idx = listUserPanel.getListUsers().getSelectedIndex();
        if (idx == -1) return;;
        detailUserPanel.setTfUserSurname(userList.get(idx).getUserSurName());
        detailUserPanel.setTfUserName(userList.get(idx).getUserName());
        detailUserPanel.setTfUserPatronymic(userList.get(idx).getUserPatronymic());
        detailUserPanel.setTfBirthday(df.format(userList.get(idx).getBirthday()));
        detailUserPanel.setTfStatus(Integer.toString(userList.get(idx).getStatus()));
        detailUserPanel.setTfStartAction(df.format(userList.get(idx).getStartAction()));
        if (userList.get(idx).getEndAction() != null){
            detailUserPanel.setTfEndAction(df.format(userList.get(idx).getEndAction()));
            tabbedPane.setEnabledAt(1, false);    // clear button
            btnPanel.getComponent(2).setEnabled(false);     // add button
            btnPanel.getComponent(3).setEnabled(false);     // edit button
            btnPanel.getComponent(4).setEnabled(true);      // delete button
        } else {
            tabbedPane.setEnabledAt(1, true);
            btnPanel.getComponent(2).setEnabled(false);
            btnPanel.getComponent(3).setEnabled(true);
            btnPanel.getComponent(4).setEnabled(true);
        }
        detailUserPanel.setTfComment(userList.get(idx).getComment());
        selectedUser = userList.get(idx);
        selectedUserLabel.setText(selectedUser.getUserSurName() + " " +
                selectedUser.getUserName() + " " +
                selectedUser.getUserPatronymic());
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    @Override
    public void load(List<User> item) {
        try {
            userList = new ArrayList<>();
            userList.addAll(item);
            Collections.sort(userList);
            String[] users = new String[userList.size()];
            int idx = 0;
            for (User user: userList) {
                users[idx++] = user.getUserSurName() + " " + user.getUserName() + " " + user.getUserPatronymic();
            }
            listUserPanel.setListUsers(users);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(mainFrame, exception.getMessage(),
                    "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public User add() {
        User user = null;
        if (  !detailUserPanel.getTfUserSurname().getText().equals("") &&
                !detailUserPanel.getTfUserName().getText().equals("")  &&
                !detailUserPanel.getTfUserPatronymic().getText().equals("") &&
                !detailUserPanel.getTfBirthday().getText().equals("") &&
                !detailUserPanel.getTfStartAction().getText().equals("")
        ) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                user = new User(
                        0,
                        detailUserPanel.getTfUserSurname().getText(),
                        detailUserPanel.getTfUserName().getText(),
                        detailUserPanel.getTfUserPatronymic().getText(),
                        dateFormat.parse(detailUserPanel.getTfBirthday().getText()),
                        1,
                        dateFormat.parse(detailUserPanel.getTfStartAction().getText()),
                        null,
                        detailUserPanel.getTfComment().getText()
                );
            } catch (ParseException ex) {
                throw new RuntimeException(ex.getMessage() +
                        "\nФормат даты: ГГГГ-ММ-ДД");
            }
        } else {
            throw new RuntimeException("Для добавления нового пользователя не хватает данных!");
        }
        return user;
    }

    @Override
    public User edit() {
        if ( (detailUserPanel.getTfUserSurname().getText().equals("") &&
                detailUserPanel.getTfUserName().getText().equals("")) ||
                userList == null
        ){
            throw new RuntimeException ("Пользователь для редактировния не выбран!");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = userList.get(listUserPanel.getListUsers().getSelectedIndex());
        try {
            user.setUserSurName(detailUserPanel.getTfUserSurname().getText());
            user.setUserName(detailUserPanel.getTfUserName().getText());
            user.setUserPatronymic(detailUserPanel.getTfUserPatronymic().getText());
            user.setBirthday(dateFormat.parse(detailUserPanel.getTfBirthday().getText()));
            user.setStatus(Integer.parseInt(detailUserPanel.getTfStatus().getText()));
            user.setStartAction(dateFormat.parse(detailUserPanel.getTfStartAction().getText()));
            user.setComment(detailUserPanel.getTfComment().getText());
            if (!Objects.equals(detailUserPanel.getTfEndAction().getText(), "")) {
                user.setEndAction(dateFormat.parse(detailUserPanel.getTfEndAction().getText()));
            }
        } catch (ParseException ep){
            JOptionPane.showMessageDialog(mainFrame, ep.getMessage() + "\nФормат даты: ГГГГ-ММ-ДД",
                    "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }

    @Override
    public User delete() {
        if (getSelectedUser() == null || getSelectedUser().getId() == 0) {
            throw  new RuntimeException("Пользователь для удаления не выбран!");
        } else {
            return userList.get(listUserPanel.getListUsers().getSelectedIndex());
        }
    }

    private void setController(iUserDetailsPresenter<User> userDetailsPresenter){
        this.userDetailsPresenter = userDetailsPresenter;
    }
}
