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
import org.opencv.core.Mat;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

public class LogIn extends BaseCameraScene{


    @Override
    public Parent createCameraContent() {
        ScreenSize screen = new ScreenSize();
        displayWidth = screen.getDisplayWidth();
        displayHeight = screen.getDisplayHeight();

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Arch's-Eye-Right.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);
        }

        createLogin();

        createHMenu();

        rootNode.getChildren().add(menuBox);

        return rootNode;
    }

    public void createLogin(){
        MenuTitle title = new MenuTitle("login");
        title.setTranslateX(200);
        title.setTranslateY(200);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("User Name:");
        grid.add(userName, 0 , 1);
        userName.setAlignment(Pos.CENTER);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
    }

    @Override
    public void createHMenu() {

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
            Boolean confirmQuit = Main.onExit();
            if(confirmQuit){
                stopCamera();
            }
        });

        menuBox = new MenuHBox(home, camera, videoclips, snapshots, exit);

        menuBox.setTranslateX((displayWidth -  5 * menuWidth)/2.0);
        menuBox.setTranslateY(0);
    }

    @Override
    public void onCameraFrame(Mat frame) {
        //nothing goes here
    }
}
