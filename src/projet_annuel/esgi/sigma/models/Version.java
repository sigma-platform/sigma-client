package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Version {
    private Integer id;
    private String label;
    private String description;
    private String dateStart;
    private String dateEnd;

    public Version(Integer id, String label, String description, String dateStart, String dateEnd) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Version(JSONObject versionObject) throws JSONException {
        this(versionObject.getInt("id"), versionObject.getString("label"),
                versionObject.getString("description"), versionObject.getString("date_start"),
                versionObject.getString("date_end"));
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

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        return label;
    }
}
