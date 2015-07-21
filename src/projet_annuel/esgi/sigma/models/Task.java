package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {
    public final static String[] STATUS_LIST = {"Etude", "Validation", "Réalisation", "Recette", "Acceptée"};

    private Integer id;
    private String label;
    private String description;
    private String status;
    private String dateStart;
    private String dateEnd;
    private Double estimatedTime;
    private Integer progress;
    private Integer userId;
    private Integer versionId;
    private Version version;
    private User user;

    public Task(Integer id, String label, String description, String status, String dateStart, String dateEnd,
                Double estimatedTime, Integer progress, Integer userId, Version version, User user) {
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
        this.user = user;
    }

    public Task(JSONObject taskObject) throws JSONException {
        this(taskObject.getInt("id"), taskObject.getString("label"), taskObject.getString("description"),
                taskObject.getString("status"), taskObject.getString("date_start"),
                taskObject.getString("date_end"), taskObject.getDouble("estimated_time"),
                taskObject.getInt("progress"), taskObject.getInt("user_id"),
                new Version(taskObject.getJSONObject("version")), new User(taskObject.getJSONObject("user")));
    }

    public Integer getId() {
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

    public Double getEstimatedTime() {
        return estimatedTime;
    }

    public Integer getProgress() {
        return progress;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public User getUser() {
        return user;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return label;
    }
}
