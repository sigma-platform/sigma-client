package projet_annuel.esgi.sigma;

           import org.json.JSONArray;
           import org.json.JSONException;
           import org.json.JSONObject;

           import javax.swing.*;
import java.awt.*;
                import java.awt.event.ActionEvent;
                import java.awt.event.ActionListener;
           import java.io.IOException;
           import java.util.*;
           import java.util.List;

/**
 * Created by Jordan on 07/05/2015.
 */
public class ConnexionWindow extends JFrame implements ActionListener{

    JPanel jp = new JPanel();
    User uti = new User();
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
            WebService con = new WebService();
            String log = Login.getText();
            char[] pass = Password.getPassword();
            JSONObject result;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pass.length; i++) {
                sb.append(pass[i]);
            }
            try {
                result=con.connexion(log, sb);

                if(result.getBoolean("success")){
                    String to = result.getJSONObject("payload").getString("token");
                    uti.setToken(to);
                    System.out.println(uti.getToken());
                    int id = result.getJSONObject("payload").getInt("user_id");
                    uti.setId(id);


                    result = con.UserProjectList(to);
                    JSONArray pro = result.getJSONArray("payload");
                    List<Project> list = new ArrayList();
                    for(int i=0;i<result.length();i++){
                        Project prob = new Project();
                        JSONObject tempSeed = pro.getJSONObject(i);
                        prob.setId(tempSeed.getInt("id"));
                        prob.setName(tempSeed.getString("name"));
                        list.add(prob);

                    }

                    SelectProjectWindow projectWindow = new SelectProjectWindow(list);
                    projectWindow.setUser(uti);
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
