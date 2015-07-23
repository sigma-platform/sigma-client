package projet_annuel.esgi.sigma;

import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.views.ConnectionForm;
import projet_annuel.esgi.sigma.views.ProjectView;

import javax.swing.*;
import java.util.prefs.Preferences;

public class Main {

    public static void main(String[] args) {
        final Preferences preferences = Preferences.userRoot().node("Sigma");

        Runnable run = new Runnable(){
            @Override
            public void run() {
                String token = preferences.get("token", "BAD_TOKEN");
                if(token == null || token.equals("BAD_TOKEN")) {
                    ConnectionForm form = new ConnectionForm();
                    form.init();
                } else {
                    User user = new User();
                    user.setId(preferences.getInt("userId", 0));
                    user.setToken(preferences.get("token", "BAD_TOKEN"));
                    User.setInstance(user);
                    ProjectView view = new ProjectView();
                    view.init();
                }
            }
        };

        SwingUtilities.invokeLater(run);
    }
}
