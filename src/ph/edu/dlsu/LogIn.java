package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

        ImageView imgBackground = Utils.loadImage2View("res//images//Arch's-Eye-Right.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);
        }

//        MenuTitle title = new MenuTitle("Username");
//        title.setTranslateX(200);
//        title.setTranslateY(200);

        createHMenu();

        rootNode.getChildren().add(menuBox);

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