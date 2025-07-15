package mg.working;

import mg.working.message.MessageTraitement;
import mg.working.powerpoint.PowerpointTraitement;
import mg.working.windows.MainWindow;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // new MainWindow();
        // MessageTraitement messageTraitement = new MessageTraitement();
        // String[] hira =  messageTraitement.getHira("SAL.103:1-8.HIR:171/1,3-VVK-S'Aa-FF16/1-VVK-417/2-FKELOKA-487/1-FPINON3-RAHAR-205/2-ANKZ.&T-VVK&S.M-354/3-TORTEN-VVK-547/4-TSDR-RAKITR");
        PowerpointTraitement ppt = new PowerpointTraitement();
        System.out.println(ppt.getContenuHira("123"));
    }
}
