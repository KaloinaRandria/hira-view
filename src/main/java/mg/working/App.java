package mg.working;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mg.working.message.MessageTraitement;
import mg.working.powerpoint.PowerpointTraitement;
import mg.working.windows.MainWindow;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        new MainWindow();
        // MessageTraitement messageTraitement = new MessageTraitement();
        // Map<String, List<String>> hiraAndininy = messageTraitement.getHiraSyAndininy(
        //         "HIRA:14/1,2,3-VVK..787/1-ANKZ.&T-VVK& S.M-163/1,3-TORTEN-VVK&RAINA..F-PINONA1-RAHARA..F'TOMPO:FF4/1-ANTEM.1-FIZARAN:95,96-295/1");
        // PowerpointTraitement ppt = new PowerpointTraitement();
        // List<String> hiraList = ppt.getSlidesContenuHira("205");
        // for (int i = 0; i < hiraList.size(); i++) {
        // System.out.println("slide " + (i + 1) + " : " + hiraList.get(i));
        // System.out.println();
        // System.out.println();
        // System.out.println("---------------------------------");
        // }
        // for (Map.Entry<String, List<String>> entry : hiraAndininy.entrySet()) {
        //     System.out.println("Hira: " + entry.getKey() + " → Versets: " + entry.getValue());
        // }
    }
}
