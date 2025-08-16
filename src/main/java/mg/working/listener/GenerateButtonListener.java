package mg.working.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

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
        // "-HIRA:3/1,2,3-VVK-S'Aa-FF6/2-vvk-294/1,2-FAMELAK.-FF7/1-F.PINON.2-RAHAR-177/1-ANK&T.-S.M-638/2-TTENY-VVK-298/1-");
        // String[] hira = messageTraitement.getHira(this.input.getText());
        Map<String, List<String>> hiraAndininy = messageTraitement.getHiraSyAndininy(
                this.input.getText());
        new PowerpointTraitement(hiraAndininy);
    }
}
