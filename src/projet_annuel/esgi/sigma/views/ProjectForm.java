package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.ProjectGroup;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.Version;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectForm {

    private JFrame frame;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField slugTextField;
    private JComboBox<ProjectGroup> projectGroupComboBox;
    private JButton addProjectGroupButton;
    private JPanel projectPanel;

    public ProjectForm() {
        new GetProjectGroupList().execute();
    }

    public void init() {
        frame = new JFrame("New project");
        frame.setContentPane(this.projectPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 400);
        frame.setResizable(false);
        frame.setVisible(true);
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
}
