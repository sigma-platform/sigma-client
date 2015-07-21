package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Project;
import projet_annuel.esgi.sigma.models.Task;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.Version;
import projet_annuel.esgi.sigma.models.tables.TaskTableAdapter;
import projet_annuel.esgi.sigma.models.tables.VersionTableAdapter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

public class ProjectView extends MouseAdapter implements ListSelectionListener, ActionListener {

    private JFrame frame;
    private JList<Project> projectList;
    private JTabbedPane tabbedPane;
    private JPanel projectPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JButton addTaskButton;
    private JTable tasksTable;
    private JButton addVersionButton;
    private JTable versionsTable;
    private JButton disconnectButton;
    private JButton addProjectButton;

    public ProjectView() {
        addTaskButton.addActionListener(this);
        addVersionButton.addActionListener(this);
        addProjectButton.addActionListener(this);
        disconnectButton.addActionListener(this);

        loadProjects();
        projectList.addListSelectionListener(this);
        projectList.setSelectedIndex(0);

        tasksTable.addMouseListener(this);
        versionsTable.addMouseListener(this);
    }

    public void init() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = t.getScreenSize();
        frame = new JFrame("Sigma");

        JMenuBar menu = new JMenuBar();
        JMenu item = new JMenu("Disconnect");
        menu.add(item);

        frame.setContentPane(this.projectPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        frame.setVisible(true);
    }

    private void loadProjects() {
        try {
            WebService webService = new WebService();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token", User.getInstance().getToken());
            JSONObject result = webService.call(WebService.GET_METHOD, WebService.USER_PROJECT_LIST_URI, params, null);

            DefaultListModel<Project> listModel = new DefaultListModel<Project>();
            JSONArray projectsData = result.getJSONArray("payload");

            for(int i =0; i < projectsData.length(); i++) {
                JSONObject object = projectsData.getJSONObject(i);

                Project project = new Project(object);

                listModel.addElement(project);
            }

            projectList.setModel(listModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {
        tasksTable.setModel(new TaskTableAdapter(projectList.getSelectedValue().getId()));
        tasksTable.setAutoCreateRowSorter(true);
    }

    public void loadVersions() {
        versionsTable.setModel(new VersionTableAdapter(projectList.getSelectedValue().getId()));
        versionsTable.setAutoCreateRowSorter(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        loadTasks();
        loadVersions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addTaskButton)) {
            TaskForm form = new TaskForm(projectList.getSelectedValue().getId(), null);
            form.init();
            form.frame.addWindowListener(taskFormWindowAdapter());
        }

        if(e.getSource().equals(addVersionButton)) {
            VersionForm form = new VersionForm(projectList.getSelectedValue().getId(), null);
            form.init();
            form.frame.addWindowListener(versionFormWindowAdapter());
        }

        if(e.getSource().equals(addProjectButton)) {
            ProjectForm form = new ProjectForm();
            form.init();
        }

        if(e.getSource().equals(disconnectButton)) {
            new LogOut().execute();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if(e.getSource().equals(tasksTable)) {
            int r = tasksTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < tasksTable.getRowCount())
                tasksTable.setRowSelectionInterval(r, r);
            else
                tasksTable.clearSelection();

            int rowIndex = tasksTable.getSelectedRow();
            if (rowIndex < 0) return;

            if (SwingUtilities.isRightMouseButton(e) && e.getComponent() instanceof JTable) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem editItem = new JMenuItem("Edit");
                editItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Task selectedTask = ((TaskTableAdapter) tasksTable.getModel()).getElementAt(tasksTable.getSelectedRow());
                        TaskForm form = new TaskForm(ProjectView.this.projectList.getSelectedValue().getId(), selectedTask);
                        form.init();
                        form.frame.addWindowListener(taskFormWindowAdapter());
                    }
                });
                JMenuItem deleteItem = new JMenuItem("Delete");
                deleteItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new DeleteTask().execute();
                    }
                });
                popup.add(editItem);
                popup.add(deleteItem);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        if(e.getSource().equals(versionsTable)) {
            int r = versionsTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < versionsTable.getRowCount())
                versionsTable.setRowSelectionInterval(r, r);
            else
                versionsTable.clearSelection();

            int rowIndex = versionsTable.getSelectedRow();
            if (rowIndex < 0) return;

            if (SwingUtilities.isRightMouseButton(e) && e.getComponent() instanceof JTable) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem editItem = new JMenuItem("Edit");
                editItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    Version selectedVersion = ((VersionTableAdapter) versionsTable.getModel()).getElementAt(versionsTable.getSelectedRow());
                    VersionForm form = new VersionForm(ProjectView.this.projectList.getSelectedValue().getId(), selectedVersion);
                    form.init();
                    form.frame.addWindowListener(versionFormWindowAdapter());
                    }
                });
                JMenuItem deleteItem = new JMenuItem("Delete");
                deleteItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new DeleteVersion().execute();
                    }
                });
                popup.add(editItem);
                popup.add(deleteItem);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private WindowAdapter taskFormWindowAdapter() {
        return  new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ProjectView.this.loadTasks();
            }
        };
    }

    private WindowAdapter versionFormWindowAdapter() {
        return  new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ProjectView.this.loadVersions();
            }
        };
    }

    class DeleteTask extends SwingWorker {
        protected Object doInBackground() throws Exception {
            JSONObject result;

            WebService webService = new WebService();
            Task selectedTask = ((TaskTableAdapter) tasksTable.getModel()).getElementAt(tasksTable.getSelectedRow());
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", selectedTask.getId().toString());

            result = webService.call(WebService.DELETE_METHOD, WebService.TASK_URI, getParams, null);

            return result;
        }

        @Override
        protected void done() {
            super.done();
            try {
                if(((JSONObject) get()).getBoolean("success"))
                    ((TaskTableAdapter) tasksTable.getModel()).removeRow(tasksTable.getSelectedRow());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class DeleteVersion extends SwingWorker {
        protected Object doInBackground() throws Exception {
            JSONObject result;

            WebService webService = new WebService();
            Version selectedTask = ((VersionTableAdapter) versionsTable.getModel()).getElementAt(versionsTable.getSelectedRow());
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", selectedTask.getId().toString());

            result = webService.call(WebService.DELETE_METHOD, WebService.VERSION_URI, getParams, null);

            return result;
        }

        @Override
        protected void done() {
            super.done();
            try {
                if(((JSONObject) get()).getBoolean("success"))
                    ((VersionTableAdapter) versionsTable.getModel()).removeRow(versionsTable.getSelectedRow());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class LogOut extends SwingWorker {
        @Override
        protected Object doInBackground() throws Exception {
            JSONObject result;

            WebService webService = new WebService();
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());

            result = webService.call(WebService.GET_METHOD, WebService.LOGOUT_URI, getParams, null);

            return result;
        }

        @Override
        protected void done() {
            try {
                JSONObject result = (JSONObject) get();
                if(result.getBoolean("success")) {
                    Preferences preferences = Preferences.userRoot().node("Sigma");
                    preferences.remove("token");
                    preferences.remove("userId");
                    ProjectView.this.frame.dispose();
                    ConnectionForm form = new ConnectionForm();
                    form.init();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.done();
        }
    }
}
