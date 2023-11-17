package balanceReport.view;

import balanceReport.logic.ReportChecks;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewBalanceReport extends JFrame {

    private final JFrame mainForm;
    private String[] args;


    public ViewBalanceReport(String[] args){

        this.args = args;
        mainForm = new JFrame();
        mainForm.setTitle("Отчёт БАЛАНС");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setGUI();

        setPreferredSize(new Dimension(450, 350));
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
        JPanel header = new JPanel(); JPanel params = new JPanel(); JPanel selection = new JPanel();

        header.setPreferredSize(new Dimension(content.getWidth(), 30));
        content.add(header, BorderLayout.NORTH);
        JLabel headLabel = new JLabel(args[0] + " " + args[1]);
        headLabel.setBounds(20,10,175, 20);
        header.add(headLabel);

        content.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setTabPlacement(1);
        tabbedPane.addTab("params", params);
        tabbedPane.addTab("selection", selection);

        selection.setLayout(new BorderLayout());
        JTable table = new JTable(10,10);
        selection.add(table, BorderLayout.CENTER);
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
        JLabel dateLabel   = new JLabel("Дата выборки (dd/mm/yyyy): ");
        JLabel choiceLabel = new JLabel("Формат для сохранения:     ");
        JXDatePicker datePicker = new JXDatePicker();
//        MaskFormatter fmt;
//        try {
//            fmt = new MaskFormatter("##/##/####");
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        JFormattedTextField dateSelection = new JFormattedTextField(fmt);

        String[] items = {"  text  ", "  excel  ", "   xml   "};
        JComboBox<String> comboBox = new JComboBox<>(items);
        dateLabel.setBounds(20,10,175, 20);
        //dateSelection.setBounds(190,10,100, 20);
        datePicker.setBounds(190,10,100, 20);
        choiceLabel.setBounds(20,40,175, 20);
        comboBox.setBounds(190,40,100, 20);
        params.add(dateLabel); params.add(datePicker);//params.add(dateSelection);
        params.add(choiceLabel); params.add(comboBox);

        add(content);

        selectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date date = datePicker.getDate();
                JOptionPane.showMessageDialog(mainForm, "Текущая дата: " + dateFormat.format(date));
//                try {
//                    if (ReportChecks.checkDate((String) dateSelection.getValue())) {
//                        JOptionPane.showMessageDialog(mainForm, "Некорректная дата!");
//                    } else {
//                        JOptionPane.showMessageDialog(mainForm, dateSelection.getValue());
//                    }
//                } catch (NullPointerException en){
//                    JOptionPane.showMessageDialog(mainForm, "Ошибка! Пустая дата!");
//                }
                //db.select();
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }


}
