package projet_annuel.esgi.sigma.models.tables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.Task;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskTableAdapter extends AbstractTableModel {

    public final String[] headers = { "Label", "Description", "Status", "Started", "Ending", "Progress", "Estimated time", "Version" };
    private List<Task> taskList;
    private int projectId;

    public TaskTableAdapter(int projectId) {
        taskList = new ArrayList<Task>();
        this.projectId = projectId;
        new GetTaskList().execute();
    }

    public Task getElementAt(int rowIndex) {
        return taskList.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return taskList.get(rowIndex).getLabel();

            case 1:
                return taskList.get(rowIndex).getDescription();

            case 2:
                return taskList.get(rowIndex).getStatus();

            case 3:
                return taskList.get(rowIndex).getDateStart();

            case 4:
                return taskList.get(rowIndex).getDateEnd();

            case 5:
                return taskList.get(rowIndex).getProgress() + "%";

            case 6:
                return taskList.get(rowIndex).getEstimatedTime();

            case 7:
                return taskList.get(rowIndex).getVersion().getLabel();

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getRowCount() {
        return taskList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    public void removeRow(int rowIndex) {
        taskList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    class GetTaskList extends SwingWorker {
        private List<Task> tasks;

        protected Object doInBackground() throws Exception {
            tasks = new ArrayList<Task>();
            try {
                WebService webService = new WebService();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", User.getInstance().getToken());
                params.put("id", String.valueOf(projectId));
                JSONObject result = webService.call(WebService.GET_METHOD, WebService.PROJECT_TASK_LIST_URI, params, null);
                JSONArray tasksData = result.getJSONArray("payload");

                for(int i =0; i < tasksData.length(); i++) {
                    JSONObject object = tasksData.getJSONObject(i);
                    Task task = new Task(object);
                    tasks.add(task);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tasks;
        }

        @Override
        protected void done() {
            super.done();

            try {
                taskList = (List<Task>) get();
                fireTableDataChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
