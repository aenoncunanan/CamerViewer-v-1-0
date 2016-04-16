package ph.edu.dlsu.utils;

import javafx.collections.ObservableList;
import javafx.stage.Screen;

public class ScreenSize {

    private double displayWidth;
    private double displayHeight;

    public ScreenSize(){                                                    //Get the screen size of the computer

        ObservableList<Screen> screenList = Screen.getScreens();
        displayWidth = screenList.get(0).getBounds().getWidth();            //To display Width
        displayHeight = screenList.get(0).getBounds().getHeight();          //To display height

    }

    public double getDisplayWidth() {
        return displayWidth;
    }

    public double getDisplayHeight() {
        return displayHeight;
    }
}
