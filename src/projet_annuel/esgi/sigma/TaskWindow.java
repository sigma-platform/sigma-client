package projet_annuel.esgi.sigma;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jordan on 07/05/2015.
 */
public class TaskWindow extends JFrame {

    JButton Retour = new JButton("Retour");

    public TaskWindow(){

        JPanel p = new JPanel();


        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d =t.getScreenSize();
        setSize(new Dimension(400,400));
        setTitle("Sigma: Task");
        JLabel statue = new JLabel("Statue: ");
        JLabel statu = new JLabel(" ");
        JLabel priorite = new JLabel("Priorite: ");
        JLabel prio = new JLabel(" ");
        JLabel assign = new JLabel("Assigner a: ");
        JLabel assigne = new JLabel(" ");
        JLabel debut = new JLabel("Statue: ");
        JLabel start = new JLabel(" ");
        JLabel echeance = new JLabel("Echeance: ");
        JLabel fin = new JLabel(" ");
        JLabel effectue = new JLabel("% effectuer: ");
        JLabel pourcentage = new JLabel(" ");
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
