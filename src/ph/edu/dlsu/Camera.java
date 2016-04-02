package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Sound;
import ph.edu.dlsu.utils.Utils;

import java.io.*;

public class Camera extends BaseCameraScene{

    private boolean takePicture = false;

    int count = fileCount();

    public int fileCount(){
        String fileName = "imageCount.txt";

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

        String url = null;

        final String outputFile="vidClip.avi";

        url = String.valueOf(frame);

        VideoCapture videoCapture = new VideoCapture(url);
        final Size frameSize = new Size((int)videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH),(int)videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        final FourCC fourCC = new FourCC("XVID");
        VideoWriter videoWriter=new VideoWriter(outputFile,fourCC.toInt(),videoCapture.get(Videoio.CAP_PROP_FPS),frameSize,true);
        final Mat mat = new Mat();
        int frames = 0;
//        final long startTime = System.currentTimeMillis();
        System.out.println("number of frames: " + frames);
//        System.out.println("start time: " + startTime);

        while (videoCapture.read(mat)) {
            videoWriter.write(mat);
            frames++;
        }

//        final long estimatedTime = System.currentTimeMillis() - startTime;
//        System.out.println("estimated time: " + estimatedTime);
        videoCapture.release();
        videoWriter.release();
        mat.release();

        if (takePicture){
            String fileName = "snap" + count + ".png";

            Imgcodecs.imwrite(fileName, frame);

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
            File file = new File("imageCount.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(imageCount);
            out.close();

        }

    }

}