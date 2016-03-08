package ph.edu.dlsu.utils;

import javafx.collections.ObservableList;
import javafx.stage.Screen;

public class ScreenSize {

    private double displayWidth;
    private double displayHeight;

    public ScreenSize(){

        ObservableList<Screen> screenList = Screen.getScreens();
        displayWidth = screenList.get(0).getBounds().getWidth();
        displayHeight = screenList.get(0).getBounds().getHeight();

    }

    public double getDisplayWidth() {
        return displayWidth;
    }

    public double getDisplayHeight() {
        return displayHeight;
    }
}
