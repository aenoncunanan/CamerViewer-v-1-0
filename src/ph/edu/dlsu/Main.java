package ph.edu.dlsu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import ph.edu.dlsu.utils.ConfirmationBox;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

public class Main extends Application{

    private static final String WINDOW_TITLE = "Camera Viewer";
    public static final String MENU_TITLE = "menu";

    private static double displayWidth;
    private static double displayHeight;

    static Stage stage;

    MenuVBox menuBox;

    static Scene menuScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScreenSize();
        menuScene = new Scene(createHomeContent());
        stage = primaryStage;
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(menuScene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void initializeScreenSize(){
        ScreenSize screen = new ScreenSize();
        displayWidth = screen.getDisplayWidth();
        displayHeight = screen.getDisplayHeight();
    }

    private Parent createHomeContent(){
        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Arch's-Eye-Right.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);
        }

        MenuTitle title = new MenuTitle(MENU_TITLE);
        title.setTranslateX(200);
        title.setTranslateY(200);
        createVMenu();

        rootNode.getChildren().addAll(title, menuBox);

        return rootNode;

    }

    private void createVMenu(){

        final CustomMenuItem camera = new CustomMenuItem("camera");
        final CustomMenuItem login = new CustomMenuItem("login");
        final CustomMenuItem videoclips = new CustomMenuItem("video clips");
        final CustomMenuItem snapshots = new CustomMenuItem("snapshots");
        final CustomMenuItem exit = new CustomMenuItem("exit");

        camera.setOnMouseClicked(event -> {
            onCamera();
        });

        login.setOnMouseClicked(event -> {
            onLogin();
        });

        videoclips.setOnMouseClicked(event -> {
            onVideoClips();
        });

        snapshots.setOnMouseClicked(event -> {
            onSnapShots();
        });

        exit.setOnMouseClicked(event -> {
            onExit();
        });

        menuBox = new MenuVBox(camera, login, videoclips, snapshots, exit);

        menuBox.setTranslateX(200);
        menuBox.setTranslateY(300);

    }

    public static void onHome(){
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(menuScene);
    }

    public static void onCamera(){
        Camera camera = new Camera();
        stage.setTitle("Live Stream");
        stage.setScene(
                new Scene(camera.createCameraContent(),
                        displayWidth, displayHeight)
        );

    }

    public static void onLogin(){
        LogIn login = new LogIn();
        stage.setTitle("LogIn");
        stage.setScene(
                new Scene(login.createCameraContent(),
                        displayWidth, displayHeight)
        );
    }

    public static void onVideoClips(){

    }

    public static void onSnapShots(){

    }

    public static boolean onExit(){

        boolean confirmQuit = ConfirmationBox.show(
                "Are you sure you want to exit?",
                "Yes",
                "No"
        );

        if(confirmQuit) {
            Platform.exit();
        }

        return confirmQuit;
    }

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

}