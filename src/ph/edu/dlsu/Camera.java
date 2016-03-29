package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Sound;
import ph.edu.dlsu.utils.Utils;

import java.io.*;

public class Camera extends BaseCameraScene{

    private boolean takePicture = false;

    int count = 1;

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
        final CustomMenuItem capture = new CustomMenuItem("capture", menuWidth, menuHeight);
        final CustomMenuItem exit = new CustomMenuItem("exit", menuWidth, menuHeight);

        home.setOnMouseClicked(e -> {
            stopCamera();
            Main.onHome();
        });

        capture.setOnMouseClicked(event -> {

            (new Sound("file:res/sounds/camera.mp3")).play();
            takePicture = true;

        });

        exit.setOnMouseClicked(e -> {
            Boolean confirmQuit = Main.onExit();
            if(confirmQuit){
                stopCamera();
            }
        });

        menuBox = new MenuHBox(home, capture, exit);

        menuBox.setTranslateX((displayWidth - 3 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

    @Override
    public void onCameraFrame(Mat frame) throws IOException {
       // Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

//        if (takePicture){
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//            String currentDateandTime = sdf.format(new Date());
//            String fileName = currentDateandTime + ".png";
//
//            Imgcodecs.imwrite(fileName, frame);
//
//            takePicture = false;
//
//        }
        if (takePicture){
            String fileName = "snap" + count + ".png";

            Imgcodecs.imwrite(fileName, frame);

            takePicture = false;

            if(count <=20)
                count++;
            else
                count = 1;

        }

    }

}