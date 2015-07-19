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
public class SelectProjectWindow extends JFrame implements ActionListener {

    JPanel jp = new JPanel();
    JButton Select = new JButton("Ok");
    String token ;
    WebService ListProject = new WebService();
    JSONObject result;
    User uti;
    JComboBox SelectProject = new JComboBox();

    public SelectProjectWindow(List<Project> list) {

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        setSize(d);
        setTitle("Sigma");

        JComboBox SelectProject = new JComboBox();
        for(int i=0; i<list.size();i++)
        {
            SelectProject.addItem(list.get(i));
        }

        SelectProject.setPreferredSize(new Dimension(150, 50));
        Container ct = getContentPane();

        jp.add(SelectProject);
        jp.add(Select);

        ct.add(jp, BorderLayout.CENTER);
        Select.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Select) {
            new ProjectWindow();
            this.dispose();
        }
    }
    public void setUser(User use){
        System.out.println(use.getToken());
        uti = use;
        System.out.println(uti.getToken());
        try {
            token = uti.getToken();
            result = ListProject.UserProjectList(token);

            List<Project> list = new ArrayList();
            JSONArray pro= result.getJSONArray("payload");

            for(int i =0;i<result.length();i++){
                Project prob = new Project();
                JSONObject tempSteed = pro.getJSONObject(i);

                prob.setId(tempSteed.getInt("id"));
                prob.setName(tempSteed.getString("name"));

                list.add(prob);
                System.out.println(list.get(i));
                SelectProject.addItem(list.get(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

