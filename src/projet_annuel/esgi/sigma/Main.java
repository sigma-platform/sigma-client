package projet_annuel.esgi.sigma;

import projet_annuel.esgi.sigma.views.ConnexionWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Runnable run= new Runnable(){
            @Override
            public void run() {
                new ConnexionWindow();
            }
        };

        SwingUtilities.invokeLater(run);
    }
}
