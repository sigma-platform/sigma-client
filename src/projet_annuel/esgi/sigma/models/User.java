package projet_annuel.esgi.sigma.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private int id;
    private String token;
    private String email;
    private String firstname;
    private String lastname;
    private String createdAt;
    private HashMap<Integer, Project> projects;

    public final static List<Role> USER_PROJECT_ROLES;

    static {
        USER_PROJECT_ROLES = new ArrayList<Role>();
        USER_PROJECT_ROLES.add(new Role(3, "Manager"));
        USER_PROJECT_ROLES.add(new Role(2, "Developer"));
        USER_PROJECT_ROLES.add(new Role(1, "Client"));
    }

    public User() {}

    public User(int id, String email, String firstname,
                String lastname, String createdAt) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdAt = createdAt;
    }

    public User(JSONObject userObject) throws JSONException{
        this(userObject.getInt("id"), userObject.getString("email"),
                userObject.getString("firstname"), userObject.getString("lastname"),
                userObject.getString("created_at"));
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public HashMap<Integer, Project> getProjects() {
        return projects;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProjects(JSONArray projectsArray) {
        this.projects = new HashMap<Integer, Project>();

        try {
            for (int i = 0; i < projectsArray.length(); i++) {
                JSONObject object = projectsArray.getJSONObject(i);
                Project project = new Project(object);
                project.setRoleId(object.getJSONObject("pivot").getInt("role_id"));
                this.projects.put(project.getId(), project);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User)
            return ((User) obj).getId() == this.getId();

        return false;
    }

    static private User instance;
    static public User getInstance() { return instance; }
    static public void setInstance(User user) { instance = user; }
}
