package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectForm implements ActionListener {

    public JFrame frame;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField slugTextField;
    private JComboBox<ProjectGroup> projectGroupComboBox;
    private JButton addProjectButton;
    private JPanel projectPanel;

    public ProjectForm() {
        addProjectButton.addActionListener(this);
        new GetProjectGroupList().execute();
    }

    public void init() {
        frame = new JFrame("New project");
        frame.setContentPane(this.projectPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 370);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private HashMap<String, Object> getProjectPostMap() {
        HashMap<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("name", nameTextField.getText());
        postParams.put("description", descriptionTextField.getText());
        postParams.put("slug", slugTextField.getText());
        postParams.put("project_group_id", projectGroupComboBox.getModel().getElementAt(projectGroupComboBox.getSelectedIndex()).getId());

        return postParams;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addProjectButton)) {
            new PostProject().execute();
        }
    }

    class GetProjectGroupList extends SwingWorker {
        List<ProjectGroup> projectGroupList;

        @Override
        protected Object doInBackground() throws Exception {
            projectGroupList = new ArrayList<ProjectGroup>();
            try {
                WebService webService = new WebService();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", User.getInstance().getToken());

                JSONObject result = webService.call(WebService.GET_METHOD, WebService.PROJECT_GROUP_LIST, params, null);
                JSONArray projectGroupsData = result.getJSONArray("payload");

                for(int i =0; i < projectGroupsData.length(); i++) {
                    JSONObject object = projectGroupsData.getJSONObject(i);
                    ProjectGroup projectGroup = new ProjectGroup(object);
                    projectGroupList.add(projectGroup);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return projectGroupList;
        }

        @Override
        protected void done() {
            super.done();
            for (ProjectGroup projectGroup : projectGroupList)
                projectGroupComboBox.addItem(projectGroup);
        }
    }

    class PostProject extends SwingWorker {
        private Project project = null;

        protected Object doInBackground() throws Exception {
            try {
                WebService webService = new WebService();
                HashMap<String, String> getParams = new HashMap<String, String>();
                getParams.put("token", User.getInstance().getToken());

                HashMap<String, Object> postParams = ProjectForm.this.getProjectPostMap();

                JSONObject result = webService.call(WebService.POST_METHOD, WebService.STORE_PROJECT_URI, getParams, postParams);

                if(!result.getBoolean("success"))
                    return result;

                JSONObject projectData = result.getJSONObject("payload");

                project = new Project(projectData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return project;
        }

        @Override
        protected void done() {
            super.done();
            try {
                if(get() instanceof JSONObject)
                    return;

                new SyncUserAccess((Project) get()).execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class SyncUserAccess extends SwingWorker {
        private Project project;

        public SyncUserAccess(Project project) {
            this.project = project;
        }

        @Override
        protected Object doInBackground() throws Exception {
            WebService webService = new WebService();
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", String.valueOf(project.getId()));

            HashMap<String, Object> postParams = new HashMap<String, Object>();
            JSONArray users = new JSONArray();
            JSONObject user = new JSONObject().put("user_id", User.getInstance().getId()).put("role_id", 3);
            users.put(user);

            postParams.put("users", users.toString());

            return webService.call(WebService.PUT_METHOD, WebService.SYNC_USER_ACCESS_PROJECT_URI, getParams, postParams);
        }

        @Override
        protected void done() {
            super.done();
            try {
                if(((JSONObject) get()).getBoolean("success"))
                    ProjectForm.this.frame.dispose();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
