package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

public class Home {

    MenuVBox menuBox;
    ScreenSize screen = new ScreenSize();
    double displayWidth = screen.getDisplayWidth();
    double displayHeight = screen.getDisplayHeight();

    public Parent main(){

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Right.png", displayWidth, displayHeight);
        if (imgBackground != null) {
            rootNode.getChildren().add(imgBackground);                                  //View Green Screen Background
        }

        MenuTitle title = new MenuTitle("menu");                                        //Menu Title
        title.setTranslateX(200);
        title.setTranslateY(200);

        createVMenu();

        rootNode.getChildren().addAll(title, menuBox);

        return rootNode;
    }

    public void createVMenu() {                                                         //Creates menu item

        final CustomMenuItem camera = new CustomMenuItem("camera");
        final CustomMenuItem videoclips = new CustomMenuItem("video clips");
        final CustomMenuItem snapshots = new CustomMenuItem("snapshots");
        final CustomMenuItem logout = new CustomMenuItem("logout");
        final CustomMenuItem setting = new CustomMenuItem("setting");
        final CustomMenuItem exit = new CustomMenuItem("exit");


        camera.setOnMouseClicked(e -> {
            Main.onCamera();
        });

        videoclips.setOnMouseClicked(e -> {
            Main.onVideoClips();
        });

        snapshots.setOnMouseClicked(e -> {
            Main.onSnapShots();
        });

        logout.setOnMouseClicked(e -> {
            Main.onLogIn();
        });

        setting.setOnMouseClicked(e -> {
            Main.onSetting();
        });

        exit.setOnMouseClicked(e -> {
            Main.onExit();
        });

        menuBox = new MenuVBox(camera, videoclips, snapshots, logout, setting, exit);
        menuBox.setTranslateX(200);
        menuBox.setTranslateY(300);


    }
}
