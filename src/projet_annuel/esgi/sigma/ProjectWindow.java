package projet_annuel.esgi.sigma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jordan on 07/05/2015.
 */
public class ProjectWindow extends JFrame implements ActionListener {

    JPanel jp = new JPanel();
    JButton Retour = new JButton("Retour");
    JButton Task = new JButton("Tache");
    JButton DG = new JButton("Gantt");
    JButton Time = new JButton("Temps");
    JButton S = new JButton("FF");
    JPanel a = new JPanel();
    Container ct = getContentPane();

    public ProjectWindow() {

        jp.setLayout(new GridLayout(4, 1));


        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        setSize(new Dimension(400,400));
        setTitle("Sigma");




        Retour.setPreferredSize(new Dimension(150, 50));

        Task.setPreferredSize(new Dimension(150, 50));

        DG.setPreferredSize(new Dimension(150, 50));

        Time.setPreferredSize(new Dimension(150, 50));



        a.add(S);

        jp.add(Task);
        jp.add(DG);
        jp.add(Time);
        jp.add(Retour);


        ct.add(jp, BorderLayout.CENTER);
        Retour.addActionListener(this);
        Task.addActionListener(this);
        DG.addActionListener(this);
        Time.addActionListener(this);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Retour) {

            ct.remove(jp);
            ct.add(a);

        }
        if (e.getSource() == Time) {
            new TimeWindow();
            this.dispose();

        }
        if (e.getSource() == DG) {
            new GanttWindow();
            this.dispose();

        }
        if (e.getSource() == Task) {
            new SelectTask();
            this.dispose();

        }
    }
}