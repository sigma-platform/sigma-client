package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Version {
    private int id;
    private String label;
    private String description;
    private String dateStart;
    private String dateEnd;
    private int projectId;

    public Version(int id, String label, String description, String dateStart, String dateEnd, int projectId) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.projectId = projectId;
    }

    public Version(JSONObject versionObject) throws JSONException {
        this(versionObject.getInt("id"), versionObject.getString("label"),
                versionObject.getString("description"), versionObject.getString("date_start"),
                versionObject.getString("date_end"), versionObject.getInt("project_id"));
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }
}
