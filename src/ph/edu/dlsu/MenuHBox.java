package ph.edu.dlsu;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuHBox extends HBox{                                         //Extended class for Horizontal Box

    public MenuHBox(CustomMenuItem... items){

        getChildren().add(createSeparator());

        for(CustomMenuItem item : items){
            getChildren().addAll(item, createSeparator());
        }

    }

    private Line createSeparator(){                                         //line separates the item menu

        Line sep = new Line();
        sep.setEndY(40);
        sep.setStroke(Color.DARKGREY);

        return sep;

    }

}
