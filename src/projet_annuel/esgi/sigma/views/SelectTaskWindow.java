package projet_annuel.esgi.sigma.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jordan on 14/07/2015.
 */
public class SelectTaskWindow extends JFrame implements ActionListener {

    JPanel jp = new JPanel();
    JButton Select = new JButton("Ok");
    JButton Retour = new JButton("Retour");
    public SelectTaskWindow() {

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        setSize(new Dimension(400,400));
        setTitle("Sigma");

        JComboBox SelectTask = new JComboBox();

        SelectTask.setPreferredSize(new Dimension(150, 50));
        Container ct = getContentPane();

        jp.add(SelectTask);
        jp.add(Select);
        jp.add(Retour);

        ct.add(jp, BorderLayout.CENTER);
        Select.addActionListener(this);
        Retour.addActionListener(this);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Select) {
            new TaskWindow();
            this.dispose();

        }
        if(e.getSource() == Retour){
            new ProjectWindow();
            this.dispose();
        }
    }

}
