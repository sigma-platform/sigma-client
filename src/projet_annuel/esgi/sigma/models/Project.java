package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Project {
    private Integer id;
    private String name;
    private String description;
    private Integer roleId;
    private ProjectGroup projectGroup;

    public Project() {}

    public Project(Integer id, String name, String description, ProjectGroup projectGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectGroup = projectGroup;
    }

    public Project(JSONObject projectObject) throws JSONException{
        this(projectObject.getInt("id"), projectObject.getString("name"),
                projectObject.getString("description"),
                new ProjectGroup(projectObject.getJSONObject("project_group")));
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return name + " (" + projectGroup.getLabel() + ")";
    }
}
