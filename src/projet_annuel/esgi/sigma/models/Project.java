package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Project {
    private Integer id;
    private String name;
    private String description;
    private String slug;
    private Integer status;
    private Integer projectGroupId;
    private ProjectGroup projectGroup;

    public Project() {}

    public Project(Integer id, String name, String description, String slug,
                   Integer status, ProjectGroup projectGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.status = status;
        this.projectGroup = projectGroup;
    }

    public Project(JSONObject projectObject) throws JSONException{
        this(projectObject.getInt("id"), projectObject.getString("name"),
                projectObject.getString("description"), projectObject.getString("slug"),
                projectObject.getInt("status"), new ProjectGroup(projectObject.getJSONObject("project_group")));
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getProjectGroupId() {
        return projectGroupId;
    }

    public ProjectGroup getProjectGroup() {
        return projectGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " (" + projectGroup.getLabel() + ")";
    }
}
