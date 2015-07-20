package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Project;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.tables.TaskTableAdapter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProjectView implements ListSelectionListener, ActionListener {

    private JList<Project> projectList;
    private JTabbedPane tabbedPane;
    private JPanel projectPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JButton addTaskButton;
    private JTable tasksTable;

    public ProjectView() {
        addTaskButton.addActionListener(this);

        loadProjects();
        projectList.addListSelectionListener(this);
        projectList.setSelectedIndex(0);
    }

    public void init() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = t.getScreenSize();
        JFrame frame = new JFrame("Sigma");
        frame.setContentPane(new ProjectView().projectPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        frame.setVisible(true);
    }

    private void loadTasks() {
        tasksTable.setModel(new TaskTableAdapter(projectList.getSelectedValue().getId()));
        tasksTable.setAutoCreateRowSorter(true);
    }

    private void loadProjects() {
        try {
            WebService webService = new WebService();
            JSONObject result = webService.UserProjectList(User.getInstance().getToken());

            DefaultListModel<Project> listModel = new DefaultListModel<Project>();
            JSONArray projectsData = result.getJSONArray("payload");

            for(int i =0; i < projectsData.length(); i++) {
                Project project = new Project();
                JSONObject object = projectsData.getJSONObject(i);

                project.setId(object.getInt("id"));
                project.setName(object.getString("name"));

                listModel.addElement(project);
            }

            projectList.setModel(listModel);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        loadTasks();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addTaskButton)) {
            TaskForm form = new TaskForm();
            form.init();
        }
    }
}
