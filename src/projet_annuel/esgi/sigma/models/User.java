package projet_annuel.esgi.sigma.models;

/**
 * Created by Jordan on 18/07/2015.
 */
public class User {
    private String token;
    private int id;
    public User(){}
    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    static final private User INSTANCE = new User();
    static public User getInstance() { return INSTANCE; }
}
