package vvm.reportmanager.view;

import vvm.reportmanager.logic.ConfigData;
import vvm.reportmanager.logic.User;
import vvm.reportmanager.repository.DataBase;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainForm extends JFrame {

    private JFrame mainForm;
    private AccessForm accessForm;
    private final User user;
    private final ConfigData configData;



    public MainForm(User user, ConfigData configData) {
        this.user = user;
        this.configData = configData;
        setGUI();
    }

    /**
     * Настройка главой формы.
     * добавление компонентов и обработчиков событий
     */
    private void setGUI(){
        mainForm = new JFrame("ReportManager");
        mainForm.setLayout(new BorderLayout());
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.setBounds(600, 250, 455, 400);
        mainForm.setResizable(false);

        JDesktopPane jDesktopPane = new JDesktopPane();

        JInternalFrame passLoginForm = new JInternalFrame("ReportManager", false, true, false, false);
        accessForm = new AccessForm(passLoginForm, user);

        mainForm.add(jDesktopPane);
        jDesktopPane.add(passLoginForm);
        mainForm.setContentPane(jDesktopPane);

        // устанавливаем стартовые параметры
        JButton btnSelect = new JButton("выбрать"); btnSelect.setEnabled(false);
        JButton btnExit = new JButton("выйти"); btnExit.setEnabled(false);
        JList<String> userListReports = new JList<>(); int[] selectedIndex = {0};
        userListReports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userListReports.setEnabled(false);

        //добавляем элементы на панель, задаём положение и размеры
        JScrollPane jScrollPane = new JScrollPane(userListReports);
        jScrollPane.setBounds(20, 60, 400, 260);
        JLabel mainLab = new JLabel("");
        mainLab.setBounds(20,20,400, 20);
        JLabel mainListCaption = new JLabel("Список доступных отчётов");
        mainListCaption.setBounds(20,40,400, 20);
        btnSelect.setBounds(210, 330, 100, 25);
        btnExit.setBounds(320, 330, 100, 25);
        jDesktopPane.add(btnSelect); jDesktopPane.add(btnExit); jDesktopPane.add(jScrollPane);
        jDesktopPane.add(mainLab); jDesktopPane.add(mainListCaption);

        mainForm.setVisible(true);
        passLoginForm.setVisible(true);

        // получаем имя пользователя и список его отчётов
        passLoginForm.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                String login = accessForm.getUser().getAccessDB().getLogin();
                String pass  = accessForm.getUser().getAccessDB().getPassword();
                super.internalFrameClosed(e);
                user.shapeList();
                userListReports.setListData(user.getViewUserListReports());
                user.loadUserName();
                mainLab.setText("Пользователь: " + user.getUserName());
                mainForm.repaint();
                btnSelect.setEnabled(true); btnExit.setEnabled(true);
                userListReports.setEnabled(true);
            }
        });

        userListReports.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList jL = (JList)evt.getSource();
                selectedIndex[0] = jL.getSelectedIndex();
                if ((evt.getClickCount() == 2) && userListReports.isEnabled()) {
                    callReport(selectedIndex[0]);
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callReport(selectedIndex[0]);
            }
        });

    }

    /**
     * Вызывается на исполнение отчет, который реализован отдельным
     * приложением упакованным в jar
     */
    public void callReport(int idx) {
        String path = configData.getReportsHomeDir();
        String reportName = user.getUserListReports().get(idx).getReportAppName();
        try {
            mainForm.setVisible(false);
            user.getReport().callReport(accessForm.getUser().getAccessDB().getLogin(), accessForm.getUser().getAccessDB().getPassword(),
                    path, reportName).waitFor();
            mainForm.setVisible(true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}