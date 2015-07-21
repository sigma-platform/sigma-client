package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Task;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.Version;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskForm implements ActionListener {
    private Integer projectId;
    private Task selectedTask;

    public JFrame frame;
    private JPanel taskFormPanel;
    private JTextField labelTextField;
    private JComboBox<String> statusComboBox;
    private JTextField dateStartTextField;
    private JTextField dateEndTextField;
    private JTextField estimatedTimeTextField;
    private JComboBox<Version> versionComboBox;
    private JButton addTaskButton;
    private JTextArea descriptionTextArea;
    private JComboBox<User> userComboBox;

    public TaskForm(Integer projectId, Task task) {
        this.projectId = projectId;

        if(task != null) {
            selectedTask = task;
            labelTextField.setText(selectedTask.getLabel());
            descriptionTextArea.setText(selectedTask.getDescription());
            statusComboBox.getModel().setSelectedItem(selectedTask.getStatus());
            dateStartTextField.setText(selectedTask.getDateStart());
            dateEndTextField.setText(selectedTask.getDateEnd());
            estimatedTimeTextField.setText(String.valueOf(selectedTask.getEstimatedTime()));
            versionComboBox.getModel().setSelectedItem(selectedTask.getVersion());
            userComboBox.getModel().setSelectedItem(selectedTask.getUser());
            addTaskButton.setText("Save");
        }

        new GetVersionList().execute();
        new GetUserList().execute();
        for(String status : Task.STATUS_LIST)
            statusComboBox.addItem(status);

        addTaskButton.addActionListener(this);
    }

    public void init() {
        frame = new JFrame();

        if (selectedTask == null)
            frame.setTitle("New task");
        else
            frame.setTitle("Edit task");

        frame.setContentPane(this.taskFormPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addTaskButton)) {
            if(selectedTask == null)
                new PostTask().execute();
            else
                new UpdateTask().execute();
        }
    }

    class GetUserList extends SwingWorker {
        private List<User> users;

        protected Object doInBackground() throws Exception {
            users = new ArrayList<User>();
            try {
                WebService webService = new WebService();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", User.getInstance().getToken());
                params.put("id", String.valueOf(projectId));
                JSONObject result = webService.call(WebService.GET_METHOD, WebService.PROJECT_USER_LIST_URI, params, null);
                JSONArray usersData = result.getJSONArray("payload");

                for(int i =0; i < usersData.length(); i++) {
                    JSONObject object = usersData.getJSONObject(i);
                    User user = new User(object);
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void done() {
            super.done();
            for (User user : users)
                userComboBox.addItem(user);
        }
    }

    private HashMap<String, Object> getTaskPostMap() {
        HashMap<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("label", labelTextField.getText());
        postParams.put("description", descriptionTextArea.getText());
        postParams.put("status", statusComboBox.getSelectedItem());
        postParams.put("date_start", dateStartTextField.getText());
        postParams.put("date_end", dateEndTextField.getText());
        postParams.put("estimated_time", Double.parseDouble(estimatedTimeTextField.getText()));
        postParams.put("progress", 0);
        postParams.put("user_id", ((User) userComboBox.getSelectedItem()).getId());
        postParams.put("version_id", ((Version) versionComboBox.getSelectedItem()).getId());

        return postParams;
    }

    class GetVersionList extends SwingWorker {
        private List<Version> versions;

        protected Object doInBackground() throws Exception {
            versions = new ArrayList<Version>();
            try {
                WebService webService = new WebService();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", User.getInstance().getToken());
                params.put("id", String.valueOf(projectId));
                JSONObject result = webService.call(WebService.GET_METHOD, WebService.PROJECT_VERSION_LIST_URI, params, null);
                JSONArray versionsData = result.getJSONArray("payload");

                for(int i =0; i < versionsData.length(); i++) {
                    JSONObject object = versionsData.getJSONObject(i);
                    Version version = new Version(object);
                    versions.add(version);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return versions;
        }

        @Override
        protected void done() {
            super.done();
            for (Version version : versions)
                versionComboBox.addItem(version);
        }
    }

    class PostTask extends SwingWorker {
        private Task task = null;

        protected Object doInBackground() throws Exception {
            try {
                WebService webService = new WebService();
                HashMap<String, String> getParams = new HashMap<String, String>();
                getParams.put("token", User.getInstance().getToken());

                HashMap<String, Object> postParams = TaskForm.this.getTaskPostMap();

                JSONObject result = webService.call(WebService.POST_METHOD, WebService.STORE_TASK_URI, getParams, postParams);
                JSONObject taskData = result.getJSONObject("payload");

                task = new Task(taskData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return task;
        }

        @Override
        protected void done() {
            super.done();
            TaskForm.this.frame.dispose();
        }
    }

    class UpdateTask extends SwingWorker {
        protected Object doInBackground() throws Exception {
            JSONObject result;

            WebService webService = new WebService();

            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", selectedTask.getId().toString());

            HashMap<String, Object> postParams = TaskForm.this.getTaskPostMap();

            result = webService.call(WebService.PUT_METHOD, WebService.TASK_URI, getParams, postParams);
            JSONObject taskData = result.getJSONObject("payload");

            return new Task(taskData);
        }

        @Override
        protected void done() {
            super.done();
            TaskForm.this.frame.dispose();
        }
    }
}
