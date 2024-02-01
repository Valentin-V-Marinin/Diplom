package reportAdmin.view;

import reportAdmin.logic.Report;

import javax.swing.*;

public class ReportFieldsPanel extends JPanel {


    //region Метки
    private JLabel labReportName;
    private JLabel labStatus;
    private JLabel labStartDate;
    private JLabel labEndDate;
    private JLabel labReportProgram;
    private JLabel labHash;
    private JLabel labComment;
    //endregion

    //region Текстовые поля
    private JTextField tfReportName;
    private JTextField tfStatus;
    private JTextField tfStartDate;
    private JTextField tfEndDate;
    private JTextField tfReportProgram;
    private JTextField tfHash;
    private JTextField tfComment;
    //endregion

    public ReportFieldsPanel(){
        labelsTextFieldsInitiation();
        placingComponents();
        tfHash.setEnabled(false);
    }

    public void clearData(){
        tfReportName.setText("");
        tfStatus.setText("");
        tfStartDate.setText("");
        tfEndDate.setText("");
        tfReportProgram.setText("");
        tfHash.setText("");
        tfComment.setText("");
    }


    public void textFieldsInitiation(Report report){
        tfReportName.setText(report.getRepname());
        tfStatus.setText(Integer.toString(report.getStatus()));
        tfStartDate.setText(report.getStartDate().toString());
        tfEndDate.setText(report.getEndDate().toString());
        tfHash.setText(report.getHash());
        tfReportProgram.setText(report.getRepProg());
        tfComment.setText(report.getComment());
    }

    private void labelsTextFieldsInitiation(){
        labReportName = new JLabel(" Отчет");
        labStatus = new JLabel(" Статус");
        labStartDate = new JLabel(" Начало работы");
        labEndDate = new JLabel(" Конец работы ");
        labHash = new JLabel(" Хэш отчёта");
        labReportProgram = new JLabel(" Файл  ");
        labComment = new JLabel(" Комментарий");
        tfReportName = new JTextField();
        tfStatus = new JTextField();
        tfStartDate = new JTextField();
        tfEndDate = new JTextField();
        tfHash = new JTextField();
        tfReportProgram = new JTextField();
        tfComment = new JTextField();
    }

    private void placingComponents(){
        int width = 110;
        int height = 20;
        labReportName.setBounds(10, 10, width, height);
        tfReportName.setBounds(10, 40, width, height);
        labStatus.setBounds(10, 70, width, height);
        tfStatus.setBounds(10, 100, width, height);
        labStartDate.setBounds(10, 130, width, height);
        tfStartDate.setBounds(10, 160, width, height);
        labEndDate.setBounds(10, 190, width, height);
        tfEndDate.setBounds(10, 220, width, height);
        labHash.setBounds(10, 250, width, height);
        tfHash.setBounds(10, 280, width, height);
        labReportProgram.setBounds(10, 310, width, height);
        tfReportProgram.setBounds(10, 340, width, height);
        labComment.setBounds(10, 430, width, height);
        tfComment.setBounds(10, 460, width, height);

        add(labReportName);     add(tfReportName);
        add(labStatus);         add(tfStatus);
        add(labStartDate);      add(tfStartDate);
        add(labEndDate);        add(tfEndDate);
        add(labHash);           add(tfHash);
        add(labReportProgram);  add(tfReportProgram);
        add(labComment);        add(tfComment);
    }

    //region GettersSetters

    public String getTfReportName() {
        return tfReportName.getText();
    }

    public void setTfReportName(String tfReportName) {
        this.tfReportName.setText(tfReportName);
    }

    public String getTfStatus() {
        return tfStatus.getText();
    }

    public void setTfStatus(String tfStatus) {
        this.tfStatus.setText(tfStatus);
    }

    public String getTfStartDate() {
        return tfStartDate.getText();
    }

    public void setTfStartDate(String tfStartDate) {
        this.tfStartDate.setText(tfStartDate);
    }

    public String getTfEndDate() {
        return tfEndDate.getText();
    }

    public void setTfEndDate(String tfEndDate) {
        this.tfEndDate.setText(tfEndDate);
    }

    public String getTfReportProgram() {
        return tfReportProgram.getText();
    }
    public void setTfReportProgram(String tfReportProgram) {
        this.tfReportProgram.setText(tfReportProgram);
    }

    public String getTfHash() {
        return tfHash.getText();
    }

    public void setTfHash(String tfHash) {
        this.tfHash.setText(tfHash);
    }

    public String getTfComment() {
        return tfComment.getText();
    }

    public void setTfComment(String tfComment) {
        this.tfComment.setText(tfComment);
    }


    //endregion
}
