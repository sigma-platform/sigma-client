package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Project;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectWindow extends JFrame implements ActionListener {

    private User currentUser;

    private JList<Project> projectBar = new JList<Project>();

    public ProjectWindow() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = t.getScreenSize();

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();

        leftPanel.setLayout(new GridLayout(0,1));
        rightPanel.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        tabbedPane.addTab("Tasks", panel1);
        tabbedPane.addTab("Times", panel2);
        tabbedPane.addTab("Versions", panel3);
        tabbedPane.addTab("Gantt", panel4);

        leftPanel.add(projectBar, BorderLayout.WEST);
        rightPanel.add(tabbedPane);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        pack();

        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        setTitle("Sigma");
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}