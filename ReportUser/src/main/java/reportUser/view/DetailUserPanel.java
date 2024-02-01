package reportUser.view;

import reportUser.logic.User;

import javax.swing.*;

public class DetailUserPanel extends JPanel {


    //region Метки
    private JLabel labUserSurname;
    private JLabel labUserName;
    private JLabel labUserPatronymic;
    private JLabel labBirthday;
    private JLabel labStatus;
    private JLabel labStartAction;
    private JLabel labEndAction;
    private JLabel labComment;

    //endregion

    //region Текстовые поля
    private JTextField tfUserSurname;
    private JTextField tfUserName;
    private JTextField tfUserPatronymic;
    private JTextField tfBirthday;
    private JTextField tfStatus;
    private JTextField tfStartAction;
    private JTextField tfEndAction;
    private JTextField tfComment;
    //endregion

    public DetailUserPanel(){
        labelsTextFieldsInitiation();
        placingComponents();
    }

    public void clearData(){
        tfUserSurname.setText("");
        tfUserName.setText("");
        tfUserPatronymic.setText("");
        tfBirthday.setText("");
        tfStatus.setText("");
        tfStartAction.setText("");
        tfEndAction.setText("");
        tfComment.setText("");
    }


    public void textFieldsInitiation(User user){
        tfUserSurname.setText(user.getUserSurName());
        tfUserName.setText(user.getUserName());
        tfUserPatronymic.setText(user.getUserPatronymic());
        tfBirthday.setText(String.valueOf(user.getBirthday()));
        tfStatus.setText(user.getStatus() == 1 ? "Работает" : "Уволен(а)");
        tfStartAction.setText(String.valueOf(user.getStartAction()));
        tfEndAction.setText(String.valueOf(user.getEndAction()));
        tfComment.setText(user.getComment());
    }

    private void labelsTextFieldsInitiation(){
        labUserSurname = new JLabel(" Фамилия");
        labUserName = new JLabel(" Имя");
        labUserPatronymic = new JLabel(" Отчество");
        labBirthday = new JLabel(" Дата рождения ");
        labStatus = new JLabel(" Статус");
        labStartAction = new JLabel(" Начало работы ");
        labEndAction = new JLabel(" Конец работы");
        labComment = new JLabel(" Комментарий");
        tfUserSurname = new JTextField();
        tfUserName = new JTextField();
        tfUserPatronymic = new JTextField();
        tfBirthday = new JTextField();
        tfStatus = new JTextField();
        tfStartAction = new JTextField();
        tfEndAction = new JTextField();
        tfComment = new JTextField();
    }

    private void placingComponents(){
        int width = 110;
        int height = 20;
        labUserSurname.setBounds(10, 10, width, height);
        tfUserSurname.setBounds(10, 40, width, height);
        labUserName.setBounds(10, 70, width, height);
        tfUserName.setBounds(10, 100, width, height);
        labUserPatronymic.setBounds(10, 130, width, height);
        tfUserPatronymic.setBounds(10, 160, width, height);
        labBirthday.setBounds(10, 190, width, height);
        tfBirthday.setBounds(10, 220, width, height);
        labStatus.setBounds(10, 250, width, height);
        tfStatus.setBounds(10, 280, width, height);
        labStartAction.setBounds(10, 310, width, height);
        tfStartAction.setBounds(10, 340, width, height);
        labEndAction.setBounds(10, 370, width, height);
        tfEndAction.setBounds(10, 400, width, height);
        labComment.setBounds(10, 430, width, height);
        tfComment.setBounds(10, 460, width, height);

        add(labUserSurname);    add(tfUserSurname);
        add(labUserName);       add(tfUserName);
        add(labUserPatronymic); add(tfUserPatronymic);
        add(labBirthday);       add(tfBirthday);
        add(labStatus);         add(tfStatus);
        add(labStartAction);    add(tfStartAction);
        add(labEndAction);      add(tfEndAction);
        add(labComment);        add(tfComment);
    }

    //region GettersSetters
    public JTextField getTfUserName() {
        return tfUserName;
    }

    public void setTfUserName(String userName)
    {
        tfUserName.setText(userName);
    }

    public JTextField getTfUserSurname() {
        return tfUserSurname;
    }

    public void setTfUserSurname(String userSurname) {
        tfUserSurname.setText(userSurname);
    }

    public JTextField getTfUserPatronymic() {
        return tfUserPatronymic;
    }

    public void setTfUserPatronymic(String userPatronymic) {
        tfUserPatronymic.setText(userPatronymic);
    }

    public JTextField getTfBirthday() {
        return tfBirthday;
    }

    public void setTfBirthday(String birthday)
    {
        tfBirthday.setText(birthday);
    }

    public JTextField getTfStatus() {
        return tfStatus;
    }

    public void setTfStatus(String status) {
        tfStatus.setText(status);
    }

    public JTextField getTfStartAction() {
        return tfStartAction;
    }

    public void setTfStartAction(String startAction) {
        this.tfStartAction.setText(startAction);
    }

    public JTextField getTfEndAction() {
        return tfEndAction;
    }

    public void setTfEndAction(String endAction) {
        this.tfEndAction.setText(endAction);
    }

    public JTextField getTfComment() {
        return tfComment;
    }

    public void setTfComment(String comment) {
        this.tfComment.setText(comment);
    }
    //endregion
}
