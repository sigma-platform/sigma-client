package projet_annuel.esgi.sigma.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jordan on 07/05/2015.
 */
public class AddTaskWindow extends JFrame {

    JButton Retour = new JButton("Retour");

    public AddTaskWindow(){

        JPanel p = new JPanel();


        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d =t.getScreenSize();
        setSize(d);
        setTitle("Sigma: Task");
        JLabel statue = new JLabel("Statue: ");
        JTextField statu = new JTextField(" ");
        JLabel priorite = new JLabel("Priorite: ");
        JTextField prio = new JTextField(" ");
        JLabel assign = new JLabel("Assigner a: ");
        JTextField assigne = new JTextField(" ");
        JLabel debut = new JLabel("Statue: ");
        JTextField start = new JTextField(" ");
        JLabel echeance = new JLabel("Echeance: ");
        JTextField fin = new JTextField(" ");
        JLabel effectue = new JLabel("% effectuer: ");
        JLabel pourcentage = new JLabel("0% ");
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3,4));
        Container c = getContentPane();
        jp.add(statue);
        jp.add(statu);
        jp.add(debut);
        jp.add(start);
        jp.add(priorite);
        jp.add(prio);
        jp.add(echeance);
        jp.add(fin);
        jp.add(assign);
        jp.add(assigne);
        jp.add(effectue);
        jp.add(pourcentage);
        p.add(Retour);
        c.add(jp);
        c.add(Retour, BorderLayout.SOUTH);

        setVisible(true);
    }
}
