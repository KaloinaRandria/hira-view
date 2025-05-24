package mg.working.windows;

import javax.swing.*;
import java.util.List;

public class NavBar {
    List<JButton> navbarButtons;

    public List<JButton> getNavbarButtons() {
        return navbarButtons;
    }

    public void setNavbarButtons(List<JButton> navbarButtons) {
        this.navbarButtons = navbarButtons;
    }

    public NavBar(List<JButton> navbarButtons) {
        this.navbarButtons = navbarButtons;
    }

    public NavBar(){}
}
