package projet_annuel.esgi.sigma.views;

import javax.swing.*;
import java.awt.*;

public class Toast extends JDialog {
    int milliseconds;

    public Toast(String toastString, int time) {
        this.milliseconds = time;
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel toastLabel = new JLabel("");
        toastLabel.setText(toastString);
        toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        toastLabel.setForeground(Color.DARK_GRAY);

        setBounds(100, 100, toastLabel.getPreferredSize().width+20, 31);

        setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = dim.height/2 - getSize().height/2;
        int half = y/2;
        setLocation(dim.width/2 - getSize().width/2, y+half + 50);
        panel.add(toastLabel);
        setVisible(false);

        new Thread(){
            public void run() {
                try {
                    Thread.sleep(milliseconds);
                    dispose();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}