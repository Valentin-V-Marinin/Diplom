package balanceReport.view;

import balanceReport.logic.ConfigData;
import balanceReport.logic.ReportSaver;
import balanceReport.logic.ReportData;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewBalanceReport extends JFrame {

    private final JFrame mainForm;
    private final String[] args;
    private final ReportData db;
    private final ConfigData configData;


    public ViewBalanceReport(String[] args, ReportData db, ConfigData configData){

        this.args = args;
        this.db = db;
        this.configData = configData;
        mainForm = this;
        setTitle("Отчёт БАЛАНС");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450,300));

        setGUI();
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setGUI(){

        Font font = new Font("Times New Roman", Font.PLAIN, 12);
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);

        // основная панель
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        // панели заголовка, параметров и выборки
        JPanel header = new JPanel();
        JPanel params = new JPanel();
        JPanel selection = new JPanel();

        header.setPreferredSize(new Dimension(content.getWidth(), 30));
        content.add(header, BorderLayout.NORTH);
        JLabel headLabel = new JLabel("БАЛАНС");
        headLabel.setBounds(20,10,175, 20);
        header.add(headLabel);

        content.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setTabPlacement(1);
        tabbedPane.addTab("параметры", params);
        tabbedPane.addTab("выборка  ", selection);

        selection.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        selection.add(textArea, BorderLayout.CENTER);
        selection.add(new JPanel(), BorderLayout.SOUTH);

        // панель кнопок
        JPanel btnPanel = new JPanel();
        content.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setPreferredSize(new Dimension(params.getWidth(), 35));
        btnPanel.setLayout(new FlowLayout());
        JButton selectBtn = new JButton("выбрать");
        JButton saveBtn = new JButton("сохранить");
        JButton closeBtn = new JButton("выйти");
        selectBtn.setPreferredSize(new Dimension(100, 25));
        saveBtn.setPreferredSize(new Dimension(100, 25));
        closeBtn.setPreferredSize(new Dimension(100, 25));
        btnPanel.add(selectBtn); btnPanel.add(saveBtn); btnPanel.add(closeBtn);

        params.setLayout(null);
        JLabel dateLabel   = new JLabel("Дата выборки           ");
        JLabel choiceLabel = new JLabel("Формат для сохранения: ");
        JXDatePicker datePicker = new JXDatePicker();

        String[] items = {"  text   ", "  html   ", "  excel  "};
        JComboBox<String> comboBox = new JComboBox<>(items);
        dateLabel.setBounds(20,10,175, 20);
        datePicker.setBounds(190,10,110, 20);
        choiceLabel.setBounds(20,40,175, 20);
        comboBox.setBounds(190,40,110, 20);
        params.add(dateLabel); params.add(datePicker);
        params.add(choiceLabel); params.add(comboBox);

        add(content);

//        DateFormat dateFormatLabel = new SimpleDateFormat("вв-ЬЬ-yyyy");
//        datePicker.addActionListener(e ->{
//            JLabel lab = new JLabel(header.getComponent(0));// = "БАЛАНС за " + dateFormatLabel.format(datePicker.getDate());
//            System.out.println(lab);
//        });

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        selectBtn.addActionListener(e -> {
            Date date = datePicker.getDate();
            db.setConnectDB(configData.getConfigInfoDB(),args[0], args[1]);
            db.setQuery(dateFormat.format(date));
            db.setResultSelection(db.loadInfo(db.getQuery(),3));
            for (String str: db.getResultSelection()) {
                textArea.append(str + "\n");
            }
            tabbedPane.setSelectedIndex(1);
        });

        saveBtn.addActionListener(e -> {
            ReportSaver reportSaver = new ReportSaver();
            try {
                Date date = datePicker.getDate();
                switch (comboBox.getSelectedIndex()){
                    case 0: reportSaver.saveTXT(db.getResultSelection(), dateFormat.format(date), "", "БАЛАНС", configData);
                            break;
                    case 1: reportSaver.saveHTML(db.getResultSelection(), dateFormat.format(date), "", "БАЛАНС", configData);
                            break;
                    case 2: reportSaver.saveExcel(db.getResultSelection(), dateFormat.format(date), "", "БАЛАНС", configData);
                            break;
                }
                JOptionPane.showMessageDialog(mainForm, "Отчет сохранён в папке отчётов.", "Отчет БАЛАНС", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainForm, ex, "Отчет БАЛАНС", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> System.exit(0));


    }


}
