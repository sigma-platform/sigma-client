package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {
    private int id;
    private String label;
    private String description;
    private String status;
    private String dateStart;
    private String dateEnd;
    private double estimatedTime;
    private int progress;
    private int userId;
    private Version version;

    public Task(int id, String label, String description, String status, String dateStart, String dateEnd,
                double estimatedTime, int progress, int userId, Version version) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.status = status;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.estimatedTime = estimatedTime;
        this.progress = progress;
        this.userId = userId;
        this.version = version;
    }

    public Task(JSONObject taskObject) throws JSONException {
        this(taskObject.getInt("id"), taskObject.getString("label"), taskObject.getString("description"),
                taskObject.getString("status"), taskObject.getString("date_start"),
                taskObject.getString("date_end"), taskObject.getDouble("estimated_time"),
                taskObject.getInt("progress"), taskObject.getInt("user_id"),
                new Version(taskObject.getJSONObject("version")));
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public int getProgress() {
        return progress;
    }

    public int getUserId() {
        return userId;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return label;
    }
}
