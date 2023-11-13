package vvm.reportmanager.view;

import vvm.reportmanager.logic.ConfigData;
import vvm.reportmanager.logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AccessForm {
    private final User user;
    private JTextField loginField;
    private JPasswordField passwordField;
    private final JInternalFrame jif;
    private ConfigData configData;


    public AccessForm(JInternalFrame jif, User user, ConfigData configData) {
        this.jif = jif;
        this.configData = configData;
        accessGUI(jif);
        this.user = user;
    }

    private void accessGUI(JInternalFrame jif){
        jif.setLayout(new BorderLayout());
        jif.setBounds(100, 50, 255, 200);
        jif.setForeground(Color.BLUE);

        //панель для расположения элементов , в конструкторе передается менеджер раскладки, null - AbsoluteLayout ( для добавления компонентов необходимо указывать координаты и размер)
        JPanel panel = new JPanel(null);

        //установка панели во фрейм в центральную часть (панель будет растягиваться на весь фрейм)
        jif.add(panel, BorderLayout.CENTER);

        //текстовое поле и его метка
        JLabel lablogin = new JLabel("Логин");
        loginField = new JTextField("");
        JLabel labpass = new JLabel("Пароль");
        passwordField = new JPasswordField("");
        JButton btnEnter = new JButton("Войти");

        //установка координаты и размеров (x, y, ширина, высота) для компонента
        lablogin.setBounds(20, 20, 200, 20);
        loginField.setBounds(20, 40, 200, 20);
        labpass.setBounds(20, 70, 200, 20);
        passwordField.setBounds(20, 90, 200, 20);
        btnEnter.setBounds(80, 120, 80, 25);

        //добавление поля на панель
        panel.add(loginField); panel.add(passwordField); panel.add(lablogin); panel.add(labpass); panel.add(btnEnter);

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConnect(jif);
            }
        });

        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    if (checkEmptyLogin()) return;
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    setConnect(jif);
                }
            }
        });
    }

    public User getUser() {
        return user;
    }

    /**
     * Подключение к БД, при успешной попытке возвращает список отчетов пользователя
     *  при неудачной попытке очищает поле пароля и ждёт корректный пароль
     * @param jif - окно ввода логина и пароля
     */
    private void setConnect(JInternalFrame jif){
        //обработка нажатия кнопки "Войти"
        if (checkEmptyLogin()) return;

        user.setAccess(loginField.getText(), String.valueOf(passwordField.getPassword()));
        user.getDb().setConnectDB(configData.getConfigInfoDB(), user.getAccessDB().getLogin(), user.getAccessDB().getPassword());

        if (user.getAccessDB().checkPassword()) {
            jif.doDefaultCloseAction();
        } else {
            passwordField.setText("");
            passwordField.requestFocus();
            JOptionPane.showMessageDialog(jif, "Некорректный пароль или логин!\nПовторите ввод данных.   ", "ReportManager", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Проверка поля логина на пустое значение
     * @return если поле логина пустое возвращает false
     */
    private boolean checkEmptyLogin(){
        if (loginField.getText().length() == 0) {
            JOptionPane.showMessageDialog(jif, "Пустой логин не допускается!", "ReportManager", JOptionPane.ERROR_MESSAGE);
            loginField.requestFocus();
            return true;
        }
        return false;
    }


}
