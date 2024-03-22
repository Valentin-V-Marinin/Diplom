package reportUser.view;

import reportUser.model.HashCount;
import reportUser.model.UserLoginData;
import reportUser.presenter.iUserLoginPresenter;

import javax.persistence.NoResultException;
import javax.swing.*;

public class LoginDataPanel extends JPanel implements iViewloginData<UserLoginData> {
    private iUserLoginPresenter<UserLoginData> userLoginPresenter;
    private final JTextField tfLogin;
    private final JTextField tfPassword;
    private final HashCount hashCount;
    private UserLoginData userLoginData;

    private UserLoginData userLoginDataOld;
    private final ViewUserReport vur;
    private final JButton editBtn;
    private final JButton addBtn;

    public LoginDataPanel(ViewUserReport vur) {
        this.vur = vur;
        int width = 100;
        int height = 20;
        hashCount = new HashCount();
        JLabel loginLabel = new JLabel();
        loginLabel.setText("Логин");
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Пароль");
        tfLogin = new JTextField();
        tfLogin.setText("");
        tfPassword = new JTextField();
        tfPassword.setText("");
        loginLabel.setBounds(40, 10, width, height);
        tfLogin.setBounds(40, 30, width, height);
        passwordLabel.setBounds(160, 10, width, height);
        tfPassword.setBounds(160, 30, 220, height);

        JButton loadBtn = new JButton("загрузить");
                editBtn = new JButton("изменить");
                addBtn = new JButton("добавить");
        loadBtn.setBounds(40, 70, 100, 25);
        editBtn.setBounds(160, 70, 100, 25);
        addBtn.setBounds(280, 70, 100, 25);
        setLayout(null);
        add(loginLabel); add(passwordLabel);
        add(tfLogin); add(tfPassword);
        add(loadBtn); add(editBtn); add(addBtn);


        /*
         *   загрузка логина и пароля для текущего пользователя
         *   текущиий пользователь - тот, который выбран в списке пользователей
         */
        loadBtn.addActionListener(e ->{
            if (vur.getSelectedUser() == null || vur.getSelectedUser().getId() == 0) return;   // пользователь не выбран
            try {
                userLoginPresenter.loadUserLogInData(vur.getSelectedUser().getId());
                addBtn.setEnabled(true); editBtn.setEnabled(true);
            } catch (NoResultException resultException){
                addBtn.setEnabled(true);
                JOptionPane.showMessageDialog(vur, "Для этого пользователя пароль и логин не определены!",
                "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });

        /*
         *   редактирование логина и пароля пользователя
         */
        editBtn.addActionListener(e -> {
            if (vur.getSelectedUser().getId() == 0 || vur.getSelectedUser() == null) return;    // пользователь не выбран
            if (tfLogin.getText().equals("") || tfPassword.getText().equals("")) return;        // нет данных для редактирования
            try {
                userLoginPresenter.updateUserLogInData();
                loadBtn.doClick();
                JOptionPane.showMessageDialog(vur, "Данные изменены.",
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(vur, exception.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });

        /*
         * Добавление логина и пароля для нового пользователя
         */
        addBtn.addActionListener(e -> {
            if (vur.getSelectedUser() == null || vur.getSelectedUser().getId() == 0) return;    // пользователь не выбран
            if (tfLogin.getText().equals("") || tfPassword.getText().equals("")) return;        // нет данных для добавления
            try {
                if (userLoginData != null) {
                    if (vur.getSelectedUser().getId() == userLoginData.getUsersID()) {
                        throw new RuntimeException("Логин и пароль уже существуют для данного пользователя! \n " +
                                "Логин и пароль этого пользователя можно изменить.");
                    }
                }
                userLoginPresenter.addUserLogInData();
                loadBtn.doClick();
                JOptionPane.showMessageDialog(vur, "Данные добавлены.",
                            "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(vur, exception.getMessage(),
                        "Административный отчет ПОЛЬЗОВАТЕЛЬ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Метод очищает поля для логина и пароля, а также сбрасывает данные
     * предыдущего пользователя
     */
    public void clearData(){
        tfLogin.setText(""); tfPassword.setText("");
        addBtn.setEnabled(false); editBtn.setEnabled(false);
    }


    /**
     * Заносим значенмя UserLoginData (логин, пароль) в
     * соответствующие текстовые поля
     * @param item - тип UserLoginData - запись из таблицы enterprise.access
     */
    @Override
    public void load(UserLoginData item) {
        userLoginData = item;
        userLoginDataOld = item;
        tfLogin.setText(item.getLogin());
        tfPassword.setText(item.getPassword());
    }

    /**
     * Создание нового пользователя, запись данных в таблицу
     * enteprise.access (флаг flagHashOn = true)  и в системную
     * таблицу mysql.user (флаг flagHashOn = false)
     * @param flagHashOn - определяет в каком виде пароль будет записан в таблицу
     *                  true - зашифрован для записи в enterprise.access
     *                  false - шифруется средствами mysql при записи в mysql.user
     * @return - тип UserLoginData - запись из таблицы enterprise.access
     */
    @Override
    public UserLoginData add(boolean flagHashOn) {
        UserLoginData userLoginData = new UserLoginData();
        userLoginData.setId(0);
        userLoginData.setUsersID(vur.getSelectedUser().getId());
        userLoginData.setLogin(tfLogin.getText());
        if (flagHashOn) {
            userLoginData.setPassword(hashCount.alterHash(tfPassword.getText()));
        } else {
            userLoginData.setPassword(tfPassword.getText());
        }
        return userLoginData;
    }

    /**
     * Редактирование пароля и логина в таблицах enterprise.access и mysql.user
     * @param flagHashOn - определяет в каком виде пароль будет записан в таблицу
     *                      true - зашифрован для записис enterprise.access
     *                      false - шифрует mysql при записи в mysql.user
     * @return - тип UserLoginData - запись из таблицы enterprise.access
     */
    @Override
    public UserLoginData edit(boolean flagHashOn) {
        userLoginData.setLogin(tfLogin.getText());
        if (flagHashOn) {
            userLoginData.setPassword(hashCount.alterHash(tfPassword.getText()));
        } else {
            userLoginData.setPassword(tfPassword.getText());
        }
        return userLoginData;
    }

    /**
     * Удаление данных доступа из таблицы access (логина и пароля) при удалении пользователя
     * из таблицы users
     * @return - тип UserLoginData - запись из таблицы enterprise.access
     */
    @Override
    public UserLoginData delete() {
        return userLoginDataOld;
    }

    /**
     * Подключается презентер для взаимодействия с ДБ
     * @param userLoginPresenter - презентер для работы с данными доступа (логин, пароль)
     */
    public void setPresenter(iUserLoginPresenter<UserLoginData> userLoginPresenter){
        this.userLoginPresenter = userLoginPresenter;
    }

    /**
     * Возвращает презентер для управления кнопками на закладке данных для доступа (закладка "Пароль")
     * @return  - презентер для работы с данными доступа (логин, пароль)
     */
    public iUserLoginPresenter<UserLoginData> getPresenter() {
        return userLoginPresenter;
    }
}
