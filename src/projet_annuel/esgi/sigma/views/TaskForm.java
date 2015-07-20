package projet_annuel.esgi.sigma.views;

import javax.swing.*;

public class TaskForm {
    private JPanel taskFormPanel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JButton addButton;
    private JTextArea textArea1;

    public TaskForm() {}

    public void init() {
        JFrame frame = new JFrame("New task");
        frame.setContentPane(new TaskForm().taskFormPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
