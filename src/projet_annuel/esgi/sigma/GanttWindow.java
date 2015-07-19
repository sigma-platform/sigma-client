package projet_annuel.esgi.sigma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jordan on 07/05/2015.
 */
public class GanttWindow extends JFrame implements ActionListener {

    JButton Retour = new JButton("Retour");

    public GanttWindow() {

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        setSize(d);
        setTitle("Sigma: Diagramme de GANTT");

        JPanel jp = new JPanel();
        jp.add(Retour, BorderLayout.NORTH);


        setVisible(true);
        Retour.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Retour) {
            new ProjectWindow();
            this.dispose();

        }
    }
}
