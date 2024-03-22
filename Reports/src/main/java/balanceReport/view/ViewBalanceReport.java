package balanceReport.view;

import balanceReport.presenter.PresenterBalanceReport;
import balanceReport.presenter.iPresenterBalanceReport;
import balanceReport.repository.DataBase;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ViewBalanceReport extends JFrame implements iViewBalanceReport{

    private final JFrame mainForm;
    private final String[] args;
    private JTextArea textArea;
    private JXDatePicker datePicker;
    private JTabbedPane tabbedPane;
    private iPresenterBalanceReport presenterBalanceReport;


    public ViewBalanceReport(String[] args) {

        this.args = args;
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
        tabbedPane = new JTabbedPane();
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
        textArea = new JTextArea();
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
        datePicker = new JXDatePicker();

        String[] items = {"  text   ", "  html   ", "  excel  "};
        JComboBox<String> comboBox = new JComboBox<>(items);
        dateLabel.setBounds(20,10,175, 20);
        datePicker.setBounds(190,10,110, 20);
        choiceLabel.setBounds(20,40,175, 20);
        comboBox.setBounds(190,40,110, 20);
        params.add(dateLabel); params.add(datePicker);
        params.add(choiceLabel); params.add(comboBox);

        setPresenter(new PresenterBalanceReport(new DataBase(), this, args));

        add(content);

        selectBtn.addActionListener(e -> {
            presenterBalanceReport.getData();
        });

        saveBtn.addActionListener(e -> {
            presenterBalanceReport.saveData(comboBox.getSelectedIndex(), this);
        });

        closeBtn.addActionListener(e -> System.exit(0));
    }


    @Override
    public void getData(ArrayList<String> db) {
        textArea.setText("");
        for (String str: db) {
            textArea.append(str + "\n");
        }
        tabbedPane.setSelectedIndex(1);
    }

    @Override
    public Date sendData() {
        return datePicker.getDate();
    }

    private void setPresenter(iPresenterBalanceReport presenterBalanceReport)
    {this.presenterBalanceReport = presenterBalanceReport;}
}
