package projet_annuel.esgi.sigma.models.tables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.Version;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VersionTableAdapter extends AbstractTableModel {
    public final String[] headers = { "Label", "Description", "Started", "Ending" };
    private List<Version> versionList;
    private Integer projectId;

    public VersionTableAdapter(int projectId) {
        versionList = new ArrayList<Version>();
        this.projectId = projectId;
        new GetVersionList().execute();
    }

    @Override
    public int getRowCount() {
        return versionList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    public Version getElementAt(int rowIndex) {
        return versionList.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return versionList.get(rowIndex).getLabel();

            case 1:
                return versionList.get(rowIndex).getDescription();

            case 2:
                return versionList.get(rowIndex).getDateStart();

            case 3:
                return versionList.get(rowIndex).getDateEnd();

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    public void removeRow(int rowIndex) {
        versionList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
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
            try {
                versionList = (List<Version>) get();
                fireTableDataChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
