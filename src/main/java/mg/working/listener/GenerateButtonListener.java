package mg.working.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import mg.working.message.MessageTraitement;
import mg.working.powerpoint.PowerpointTraitement;

public class GenerateButtonListener implements ActionListener {
    private JTextArea input;

    public GenerateButtonListener(JTextArea input) {
        this.input = input;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Input received: " + this.input.getText());
        MessageTraitement messageTraitement = new MessageTraitement();
        // String[] hira = messageTraitement.getHira(
        //         "SAL.103:1-8.HIR:171/1,3-VVK-S'Aa-FF16/1-VVK-417/2-FKELOKA-487/1-FPINON3-RAHAR-205/2-ANKZ.&T-VVK&S.M-354/3-TORTEN-VVK-547/4-TSDR-RAKITR");
        String[] hira = messageTraitement.getHira(this.input.getText());
        new PowerpointTraitement(hira);
    }
}
