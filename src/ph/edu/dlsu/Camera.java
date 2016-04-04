package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoWriter;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Sound;
import ph.edu.dlsu.utils.Utils;

import java.io.*;

public class Camera extends BaseCameraScene{

    private boolean takePicture = false;

    private VideoWriter videoWriter;
    private int frames;

    int count = fileCount();

    public int fileCount(){
        String fileName = "Shots/Snaps/imageCount.txt";

        String line = null;

        int imageCount = 1;

        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                imageCount = Integer.parseInt(line);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file!");
        } catch (IOException e) {
            System.out.println("Error reading file!");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {

            }
        }
        return imageCount;
    }

    @Override
    public Parent createCameraContent(){
        initializeCapture();

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

    private void initializeCapture() {
        final String outputFile="Shots/VidClips/vidClip.avi";
        int fourCC = VideoWriter.fourcc('i','y','u','v');
        videoWriter = new VideoWriter(outputFile,fourCC,20,new Size(frameWidth, frameHeight),true);
        frames = 0;
    }

    @Override
    public void createHMenu() {
        final CustomMenuItem home = new CustomMenuItem("home", menuWidth, menuHeight);
        final CustomMenuItem capture = new CustomMenuItem("capture", menuWidth, menuHeight);
        final CustomMenuItem logout = new CustomMenuItem("logout", menuWidth, menuHeight);
        final CustomMenuItem exit = new CustomMenuItem("exit", menuWidth, menuHeight);

        home.setOnMouseClicked(e -> {
            stopCamera();
            Main.onHome();
        });

        capture.setOnMouseClicked(event -> {

            (new Sound("file:res/sounds/camera.mp3")).play();
            takePicture = true;

        });

        logout.setOnMouseClicked(e -> {
            stopCamera();
            Main.onLogIn();
        });

        exit.setOnMouseClicked(e -> {
            Boolean confirmQuit = Main.onExit();
            if(confirmQuit){
                stopCamera();
            }
        });

        menuBox = new MenuHBox(home, capture, logout, exit);

        menuBox.setTranslateX((displayWidth - 4 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

    @Override
    public void onCameraFrame(Mat frame) throws IOException {
//        final long startTime = System.currentTimeMillis();
        System.out.println("number of frames: " + frames);
//        System.out.println("start time: " + startTime);

        videoWriter.write(frame);
        frames++;

//        final long estimatedTime = System.currentTimeMillis() - startTime;
//        System.out.println("estimated time: " + estimatedTime);


        if (takePicture){
            String fileName = "snap" + count + ".png";

            Imgcodecs.imwrite("Shots/Snaps/" + fileName, frame);

            takePicture = false;

            if(count <= 20) {
                if (count == 20)
                    count = 1;
                else
                    count++;
            }
            else
                count = 1;

            String imageCount = Integer.toString(count);
            File file = new File("Shots/Snaps/imageCount.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(imageCount);
            out.close();

        }

    }

    @Override
    public void stopCamera(){
        if (videoWriter != null)
            videoWriter.release();
        super.stopCamera();
    }

}