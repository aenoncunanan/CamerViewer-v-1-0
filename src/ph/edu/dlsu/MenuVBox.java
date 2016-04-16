package ph.edu.dlsu;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuVBox extends VBox{                                     //Extended class for Vertical Box

    public MenuVBox(CustomMenuItem...items){

        getChildren().add(createSeparator());

        for (CustomMenuItem item : items){
            getChildren().addAll(item, createSeparator());
        }

    }

    private Line createSeparator(){                                     //line separates the item menu

        Line sep = new Line();
        sep.setEndX(245);
        sep.setStroke(Color.DARKGREY);
        return sep;

    }

}
