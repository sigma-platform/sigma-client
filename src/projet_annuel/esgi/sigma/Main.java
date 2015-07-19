package projet_annuel.esgi.sigma;

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

       /* Runnable run1= new Runnable(){
            @Override
            public void run() {
                new ProjectWindow();
            }
        };
        SwingUtilities.invokeLater(run1);

        Runnable run2= new Runnable(){
            @Override
            public void run() {
                new SelectProjectWindow();
            }
        };
        SwingUtilities.invokeLater(run2);

        Runnable run3= new Runnable(){
            @Override
            public void run() {
                new TaskWindow();
            }
        };
        SwingUtilities.invokeLater(run3);

        Runnable run4= new Runnable(){
            @Override
            public void run() {
                new GanttWindow();
            }
        };
        SwingUtilities.invokeLater(run4);

        Runnable run5= new Runnable(){
            @Override
            public void run() {
                new TimeWindow();
            }
        };
        SwingUtilities.invokeLater(run5);*/



    }
}
