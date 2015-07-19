package projet_annuel.esgi.sigma.views;

import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Jordan on 07/05/2015.
 */
public class ConnexionWindow extends JFrame implements ActionListener{

    JPanel jp = new JPanel();
    User user = new User();
    JButton ButtonConnexion = new JButton("Connexion");
    JTextField Login = new JTextField("admin@sigma.com");
    JPasswordField Password = new JPasswordField("admin");
    public ConnexionWindow(){

        Toolkit t = Toolkit.getDefaultToolkit();
        //Dimension d =t.getScreenSize();
        setSize(new Dimension(500,150));
        setTitle("Sigma");


        ButtonConnexion.setPreferredSize(new Dimension(150,50));

        Login.setPreferredSize(new Dimension(150,50));

        Password.setPreferredSize(new Dimension(150,50));
        Container ct = getContentPane();

        jp.add(Login);
        jp.add(Password);
        jp.add(ButtonConnexion);
        ct.add(jp,BorderLayout.CENTER);
        ButtonConnexion.addActionListener(this);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == ButtonConnexion){
            WebService webService = new WebService();
            String log = Login.getText();
            char[] pass = Password.getPassword();
            JSONObject result;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pass.length; i++) {
                sb.append(pass[i]);
            }
            try {
                result = webService.connexion(log, sb);

                if(result.getBoolean("success")){
                    String to = result.getJSONObject("payload").getString("token");
                    user.setToken(to);
                    System.out.println(user.getToken());
                    int id = result.getJSONObject("payload").getInt("user_id");
                    user.setId(id);

                    ProjectWindow projectWindow = new ProjectWindow();
                    projectWindow.setUser(user);
                    projectWindow.setVisible(true);

                    this.dispose();
                }else{
                    Login.setText(result.getString("message"));
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
