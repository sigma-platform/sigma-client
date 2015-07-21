package projet_annuel.esgi.sigma.views;

import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

public class ConnectionForm implements ActionListener {
    private Preferences prefs;

    private JFrame frame;
    private JPanel connectionPanel;
    private JTextField emailTextField;
    private JButton logInButton;
    private JLabel imageLabel;
    private JPasswordField passwordTextField;

    public ConnectionForm() {
        logInButton.addActionListener(this);
        prefs = Preferences.userRoot().node("Sigma");
    }

    public void init() {
        frame = new JFrame("Sigma");
        frame.setContentPane(this.connectionPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(380, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(logInButton)) {
            new LogIn().execute();
        }
    }

    class LogIn extends SwingWorker {

        @Override
        protected Object doInBackground() throws Exception {
            WebService webService = new WebService();
            JSONObject result;

            String email = emailTextField.getText();
            char[] passwordChars = passwordTextField.getPassword();
            StringBuilder password = new StringBuilder();
            for (char c : passwordChars) password.append(c);

            HashMap<String, Object> postParams = new HashMap<String, Object>();
            postParams.put("email", email);
            postParams.put("password", password);
            result = webService.call(WebService.POST_METHOD, WebService.LOGIN_URI, null, postParams);

            return result;
        }

        @Override
        protected void done() {
            super.done();
            try {
                JSONObject result = (JSONObject) get();
                if(result.getBoolean("success")) {
                    String token = result.getJSONObject("payload").getString("token");
                    int id = result.getJSONObject("payload").getInt("user_id");

                    User user = new User();

                    user.setToken(token);
                    user.setId(id);
                    User.setInstance(user);
                    prefs.putInt("userId", id);
                    prefs.put("token", token);

                    ConnectionForm.this.frame.dispose();
                    ProjectView projectWindow = new ProjectView();
                    projectWindow.init();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
