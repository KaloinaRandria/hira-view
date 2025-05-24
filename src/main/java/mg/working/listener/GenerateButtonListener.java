package mg.working.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mg.working.powerpoint.PowerpointTraitement;


public class GenerateButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
       new PowerpointTraitement();
    }
}
