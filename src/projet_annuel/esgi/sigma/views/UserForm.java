package projet_annuel.esgi.sigma.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Project;
import projet_annuel.esgi.sigma.models.Role;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserForm implements ActionListener {

    private Project project;
    private User user;
    private List<User> currentUsers;

    public JFrame frame;
    private JComboBox<User> userComboBox;
    private JComboBox<Role> roleComboBox;
    private JButton addUserButton;
    private JPanel userPanel;
    private JLabel userLabel;

    public UserForm(Project project, User user, List<User> currentUsers) {
        this.project = project;
        this.user = user;
        this.currentUsers = currentUsers;

        new GetUserList().execute();

        for(Role role : User.USER_PROJECT_ROLES)
            roleComboBox.addItem(role);

        if(user != null) {
            userLabel.setVisible(false);
            userComboBox.setVisible(false);
            userComboBox.getModel().setSelectedItem(user);

            Integer roleId = user.getProjects().get(project.getId()).getRoleId();

            if(roleId == 3)
                roleComboBox.setSelectedIndex(0);
            else if(roleId == 2)
                roleComboBox.setSelectedIndex(1);
            else if(roleId == 1)
                roleComboBox.setSelectedIndex(2);

            addUserButton.setText("Save");
        }

        addUserButton.addActionListener(this);
    }

    public void init() {
        frame = new JFrame();
        frame.setContentPane(this.userPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 340);
        frame.setResizable(false);

        if(user != null)
            frame.setTitle("Edit user");
        else
            frame.setTitle("New user");

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addUserButton)) {
            new SyncUserAccess().execute();
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
                JSONObject result = webService.call(WebService.GET_METHOD, WebService.USER_LIST, params, null);
                JSONArray usersData = result.getJSONArray("payload");

                for(int i =0; i < usersData.length(); i++) {
                    JSONObject object = usersData.getJSONObject(i);
                    User user = new User(object);
                    user.setProjects(object.getJSONArray("projects"));
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

    class SyncUserAccess extends SwingWorker {
        protected Object doInBackground() throws Exception {
            JSONObject result;

            WebService webService = new WebService();
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", String.valueOf(project.getId()));
            HashMap<String, Object> postParams = new HashMap<String, Object>();

            List<User> users = currentUsers;
            Integer roleId = ((Role) roleComboBox.getSelectedItem()).getId();

            if(UserForm.this.user == null) {
                User userToAdd = userComboBox.getModel().getElementAt(userComboBox.getSelectedIndex());

                if(!userToAdd.getProjects().containsKey(project.getId()))
                    userToAdd.getProjects().put(project.getId(), project);

                userToAdd.getProjects().get(project.getId()).setRoleId(roleId);

                users.add(userToAdd);
            } else {
                Integer userIndex = null;

                for (int i = 0; i < users.size(); i++) {
                    if(users.get(i).getId() == UserForm.this.user.getId()) {
                        userIndex = i;
                        break;
                    }
                }

                if(userIndex != null)
                    users.get(userIndex).getProjects().get(project.getId()).setRoleId(roleId);
            }

            JSONArray usersArray = new JSONArray();
            for(User user : users) {
                roleId = null;

                if(user.getProjects().containsKey(UserForm.this.project.getId()))
                    roleId = user.getProjects().get(UserForm.this.project.getId()).getRoleId();

                if(roleId != null)
                    usersArray.put(new JSONObject()
                            .put("user_id", user.getId())
                            .put("role_id", roleId));
            }

            postParams.put("users", usersArray.toString());

            result = webService.call(WebService.PUT_METHOD, WebService.SYNC_USER_ACCESS_PROJECT_URI, getParams, postParams);

            return result;
        }

        @Override
        protected void done() {
            super.done();
            try {
                JSONObject result = (JSONObject) get();
                if(result.getBoolean("success")) {
                    UserForm.this.frame.dispose();
                    new Toast(result.getString("message"), 5000).setVisible(true);
                }
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
