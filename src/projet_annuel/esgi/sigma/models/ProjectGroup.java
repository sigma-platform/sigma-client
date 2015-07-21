package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectGroup {
    private Integer id;
    private String label;

    public ProjectGroup(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public ProjectGroup(JSONObject objectProjectGroup) throws JSONException{
        this(objectProjectGroup.getInt("id"),objectProjectGroup.getString("label"));
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
