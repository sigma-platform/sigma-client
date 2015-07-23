package projet_annuel.esgi.sigma.models.tables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserTableAdapter extends AbstractTableModel {
    public final String[] headers = { "Email", "Name", "Since", "Role" };
    private List<User> userList;
    private Integer projectId;

    public UserTableAdapter(Integer projectId) {
        userList = new ArrayList<User>();
        new GetUserList().execute();
        this.projectId = projectId;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    public User getElementAt(int rowIndex) {
        return userList.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return userList.get(rowIndex).getEmail();

            case 1:
                return userList.get(rowIndex).getFirstname() + " " + userList.get(rowIndex).getLastname();

            case 2:
                return userList.get(rowIndex).getCreatedAt();

            case 3:
                Integer roleId = userList.get(rowIndex).getProjects().get(projectId).getRoleId();
                if(roleId == 3)
                    return "Manager";
                else if(roleId == 2)
                    return "Developer";
                else if(roleId == 1)
                    return "Client";

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    public void removeRow(int rowIndex) {
        userList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
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
                JSONArray versionsData = result.getJSONArray("payload");

                for(int i =0; i < versionsData.length(); i++) {
                    JSONObject object = versionsData.getJSONObject(i);
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
            try {
                userList = (List<User>) get();
                fireTableDataChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
