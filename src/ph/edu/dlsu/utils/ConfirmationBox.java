package ph.edu.dlsu.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationBox{

    static Stage stage;
    static boolean yesButtonClicked;

    public static boolean show(String message, String textYes, String textNo){

        yesButtonClicked = false;

        stage = new Stage();                                                    //Set up new stage style
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(250);

        Label label = new Label();                                              //Label the font anf textfill color
        label.setFont(Font.font("Asimov", FontWeight.SEMI_BOLD, 30));
        label.setTextFill(Color.BLACK);
        label.setText(message);

        Button yesButton = new Button();                                        //No Button font
        yesButton.setText(textYes);
        yesButton.setFont(Font.font("Asimov", FontWeight.SEMI_BOLD, 25));
        yesButton.setMaxWidth(Double.MAX_VALUE);
        yesButton.setOnAction(e -> onClickYes());

        Button noButton = new Button();                                         //Yes Button font
        noButton.setText(textNo);
        noButton.setFont(Font.font("Asimov", FontWeight.SEMI_BOLD, 25));
        noButton.setMaxWidth(Double.MAX_VALUE);
        noButton.setOnAction(e -> onClickNo());

        HBox paneBtn = new HBox(20);                                            //Horizontal Box position
        paneBtn.getChildren().addAll(yesButton, noButton);
        paneBtn.setPadding(new Insets(20));
        paneBtn.setAlignment(Pos.CENTER);

        VBox pane = new VBox(20);                                               //Vertical Box position
        pane.getChildren().addAll(label, paneBtn);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane);                                          //SetFill to color black
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.showAndWait();

        return yesButtonClicked;

    }

    private static void onClickNo(){                                            //Click Yes Button
        stage.close();
        yesButtonClicked = false;
    }

    private static void onClickYes(){                                           //Click No Button
        stage.close();
        yesButtonClicked = true;
    }

}
