package ph.edu.dlsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

public class LogIn{

    MenuHBox menuBox;
    ScreenSize screen = new ScreenSize();
    double displayWidth = screen.getDisplayWidth();
    double displayHeight = screen.getDisplayHeight();

    public Parent main(){

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Right.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 5);

        btn.setOnAction(event -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
        });

        Text scenetitle = new Text("Welcome!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username: ");
        grid.add(userName, 0, 1);

        TextField user = new TextField();
        user.setPromptText("Enter your username");
        user.getText();
        grid.add(user, 1, 1);

        Label pw = new Label("Password: ");
        grid.add(pw, 0, 2);

        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your password");
        pass.getText();
        grid.add(pass, 1, 2);

        grid.setTranslateX(200);
        grid.setTranslateY(200);

        createHMenu();

        rootNode.getChildren().addAll(grid, menuBox);

        return rootNode;
    }

    public void createHMenu() {

        final double menuWidth = 220;
        final double menuHeight = 40;

        final CustomMenuItem home = new CustomMenuItem("home", menuWidth, menuHeight);
        final CustomMenuItem camera = new CustomMenuItem("camera", menuWidth, menuHeight);
        final CustomMenuItem videoclips = new CustomMenuItem("video clips", menuWidth, menuHeight);
        final CustomMenuItem snapshots = new CustomMenuItem("snapshots", menuWidth, menuHeight);
        final CustomMenuItem exit = new CustomMenuItem("exit", menuWidth, menuHeight);

        home.setOnMouseClicked(e -> {
            Main.onHome();
        });

        camera.setOnMouseClicked(e -> {
            Main.onCamera();
        });

        videoclips.setOnMouseClicked(e -> {
            Main.onVideoClips();
        });

        snapshots.setOnMouseClicked(e -> {
            Main.onSnapShots();
        });

        exit.setOnMouseClicked(e -> {
            Main.onExit();
        });

        menuBox = new MenuHBox(home, camera, videoclips, snapshots, exit);

        menuBox.setTranslateX((displayWidth - 5 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

}