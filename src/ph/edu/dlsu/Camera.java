package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

public class Camera extends BaseCameraScene{

    private boolean takePicture = false;

    @Override
    public Parent createCameraContent(){
        ScreenSize screen = new ScreenSize();
        displayWidth = screen.getDisplayWidth();
        displayHeight = screen.getDisplayHeight();

        frameWidth = 0.8 * displayWidth;
        frameHeight = displayHeight;

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Center.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);
        }

        currentFrame = Utils.loadImage2View("res//images//Green-Screen-Center.png", frameWidth, frameHeight);
        currentFrame.setTranslateX((displayWidth - frameWidth)/2.0);
        currentFrame.setTranslateY(0);
        rootNode.getChildren().add(currentFrame);
        startCamera();

        createHMenu();

        rootNode.getChildren().add(menuBox);

        return rootNode;
    }

    @Override
    public void createHMenu() {
        final CustomMenuItem home = new CustomMenuItem("home", menuWidth, menuHeight);
        final CustomMenuItem videoclips = new CustomMenuItem("video clips", menuWidth, menuHeight);
        final CustomMenuItem snapshots = new CustomMenuItem("snapshots", menuWidth, menuHeight);
        final CustomMenuItem capture = new CustomMenuItem("capture", menuWidth, menuHeight);
        final CustomMenuItem exit = new CustomMenuItem("exit", menuWidth, menuHeight);

        home.setOnMouseClicked(e -> {
            stopCamera();
            Main.onHome();
        });

        videoclips.setOnMouseClicked(e -> {
            stopCamera();
            Main.onVideoClips();
        });

        snapshots.setOnMouseClicked(e -> {
            stopCamera();
            Main.onSnapShots();
        });

        capture.setOnMouseClicked(event -> {
            stillImage();
        });

        exit.setOnMouseClicked(e -> {
            Boolean confirmQuit = Main.onExit();
            if(confirmQuit){
                stopCamera();
            }
        });

        menuBox = new MenuHBox(home, videoclips, snapshots, capture, exit);

        menuBox.setTranslateX((displayWidth - 5 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

    public void stillImage(){

        takePicture = true;

    }

    @Override
    public void onCameraFrame(Mat frame){
       // Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        if (takePicture){
            Imgcodecs.imwrite("test.png", frame);
            takePicture = false;
        }
    }

}