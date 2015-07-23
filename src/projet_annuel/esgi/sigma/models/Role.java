package projet_annuel.esgi.sigma.models;

public class Role {
    private int id;
    private String label;

    public Role(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }
}
