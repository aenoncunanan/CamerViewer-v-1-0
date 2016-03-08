package ph.edu.dlsu;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuVBox extends VBox{

    public MenuVBox(CustomMenuItem...items){

        getChildren().add(createSeparator());

        for (CustomMenuItem item : items){
            getChildren().addAll(item, createSeparator());
        }

    }

    private Line createSeparator(){

        Line sep = new Line();
        sep.setEndX(245);
        sep.setStroke(Color.DARKGREY);
        return sep;

    }

}
