package projet_annuel.esgi.sigma.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jordan on 18/07/2015.
 */
public class User {
    private int id;
    private String token;
    private String email;
    private String firstname;
    private String lastname;
    private Integer roleId;

    public User() {}

    public User(int id, String email, String firstname, String lastname, Integer roleId) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roleId = roleId;
    }

    public User(JSONObject userObject) throws JSONException{
        this(userObject.getInt("id"), userObject.getString("email"),
                userObject.getString("firstname"), userObject.getString("lastname"),
                userObject.getInt("role_id"));
    }

    public int getId() {
        return id;
    }

    public String getToken() {return token;}

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
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
