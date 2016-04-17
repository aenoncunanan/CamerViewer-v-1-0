package ph.edu.dlsu;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuTitle extends StackPane {                                  //Menu title

    public MenuTitle(String name){

        Text text = new Text(name);
        text.setFill(Color.web("#009fe0"));
        text.setFont(Font.font("Asimov", FontWeight.SEMI_BOLD, 100));

        setAlignment(Pos.CENTER);
        getChildren().addAll(text);

    }

}