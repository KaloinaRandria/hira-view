package mg.working;

import mg.working.windows.MainWindow;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // JFrame fenetre = new JFrame("Application Swing avec Maven");
        // fenetre.setSize(400, 300);
        // fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JButton bouton = new JButton("Clique-moi !");
        // bouton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Bonjour depuis Swing avec Maven !"));

        // fenetre.add(bouton);
        // fenetre.setVisible(true);

        new MainWindow();
    }
}
