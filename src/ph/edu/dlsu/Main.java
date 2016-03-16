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
import ph.edu.dlsu.utils.ImageBox;
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

    //To access the drive of the screen and to get the input of the screen size
    private void initializeScreenSize(){
        ScreenSize screen = new ScreenSize();
        displayWidth = screen.getDisplayWidth();
        displayHeight = screen.getDisplayHeight();
    }

    private Parent createHomeContent(){
        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Right.png", displayWidth, displayHeight);
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
        //To create the menu and their functions
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

    //It is the Main Menu/Home Screen
    public static void onHome(){
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(menuScene);
    }

    //To view the camera or access camera and live stream
    public static void onCamera(){
        Camera camera = new Camera();
        stage.setTitle("Live Stream");
        stage.setScene(
                new Scene(camera.createCameraContent(),
                        displayWidth, displayHeight)
        );
    }

    //This function serves the use to login the username and password
    public static void onLogin(){
        LogIn login = new LogIn();
        stage.setTitle("LogIn");

//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));

        stage.setScene(
                new Scene(login.main(), displayWidth, displayHeight)
               //new Scene(grid, displayWidth, displayHeight)
        );

//        Button btn = new Button("Sign in");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 4);
//
//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 1, 6);
//
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                actiontarget.setFill(Color.FIREBRICK);
//                actiontarget.setText("Sign in button pressed");
//            }
//        });
//
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
//        stage.show();
    }

    //This function serves the user to access the 5 minute VideoCLips that has been saved throughout the stream
    public static void onVideoClips(){

    }

    //This function serves the user to access screenshots or pictures
    public static void onSnapShots(){
        ImageBox.show();
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