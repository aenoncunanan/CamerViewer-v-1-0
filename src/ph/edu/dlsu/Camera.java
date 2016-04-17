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
import ph.edu.dlsu.vision.ObjectDetector;

import java.io.*;

public class Camera extends BaseCameraScene {

    private ObjectDetector faceDetector = new ObjectDetector();

    private boolean takePicture = false;

    private VideoWriter videoWriter;
    private int frames;

    Boolean detectFace = null;
    Boolean motionDetect = false;

    int imageCount = imageCount();
    int videoCount = videoCount();

    public int videoCount(){                                                    //Video Clips count
        String fileName = "Shots/VidClips/videoCount.txt";

        String line = null;

        int videoCount = 1;

        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                videoCount = Integer.parseInt(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }
        return videoCount;
    }

    public int imageCount() {
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
    }                                                //Image count

    @Override
    public Parent createCameraContent(){                                        //Initialize capture
        try {
            initializeCapture();
        } catch (IOException e) {
            e.printStackTrace();
        }

        checkBoxReader();

        ScreenSize screen = new ScreenSize();                                   //Screen Size display
        displayWidth = screen.getDisplayWidth();
        displayHeight = screen.getDisplayHeight();

        frameWidth = 0.8 * displayWidth;
        frameHeight = displayHeight;

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Center.png", displayWidth, displayHeight);
        if(imgBackground != null){
            rootNode.getChildren().add(imgBackground);                      //Appearance of the Screen Bakcground
        }

        currentFrame = Utils.loadImage2View("res//images//Green-Screen-Center.png", frameWidth, frameHeight);
        currentFrame.setTranslateX((displayWidth - frameWidth)/2.0);        //Load green screen image in the menu
        currentFrame.setTranslateY(0);
        rootNode.getChildren().add(currentFrame);
        startCamera();
        createHMenu();

        rootNode.getChildren().add(menuBox);

        return rootNode;
    }

    private void initializeCapture() throws IOException{
        frameHeight = 0;
        frameWidth = 0;
        int fps = 20; //Lower value means slow motion; Higher value means fast motion; value is equal to minute in real time

        String outputFile = "Shots/VidClips/vidClip" + videoCount + ".avi";
        int fourCC = VideoWriter.fourcc('i', 'y', 'u', 'v');

        videoWriter = new VideoWriter(outputFile, fourCC, fps, new Size(frameWidth, frameHeight), true);

        if (!videoWriter.isOpened()){                                       //Alert the user that the record video is unable to work!
            System.out.println("Unable to record a video!");
        }

        System.out.println("videoCount: " + videoCount);

        frames = 0;
        videoCount++;

        String videoCount = Integer.toString(this.videoCount);
        File file = new File("Shots/VidClips/videoCount.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(videoCount);
        out.close();
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

        menuBox.setTranslateX((displayWidth - 4 * menuWidth) / 2.0);
        menuBox.setTranslateY(0);

    }

    @Override
    public void onCameraFrame(Mat frame, Boolean motionDetected) throws IOException {



        if (detectFace) {
            faceDetector.detectAndDisplay(frame);
        }

        if (frames >= 0 && frames < 1200) {                                 //frame 0 to frame n, where n is equal to fps declared at initCapture * 60 * desired duration in minutes
//            System.out.println(frames);
            videoWriter.write(frame);
            frames++;
        } else {
            videoWriter.release();
            if (videoCount > 10) {
                videoCount = 1;
            }
            initializeCapture();
        }

        if (takePicture || (motionDetected && motionDetect)) {                                          //capture photo and saved in a folder 'Snaps'
            String fileName = "snap" + imageCount + ".png";

            Imgcodecs.imwrite("Shots/Snaps/" + fileName, frame);

            takePicture = false;

            if (imageCount <= 20) {
                if (imageCount == 20)
                    imageCount = 1;
                else
                    imageCount++;
            } else
                imageCount = 1;

            String imageCount = Integer.toString(this.imageCount);
            File file = new File("Shots/Snaps/imageCount.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(imageCount);
            out.close();

        }
    }

    @Override                                                               //Stop Camera and release the video
    public void stopCamera() {
        if (videoWriter != null)
            videoWriter.release();
        super.stopCamera();
    }

    public void checkBoxReader() {
        //Check the setting for the face detection
        String faceDetect = "setting/faceDetect.txt";
        String lineF = null;

        BufferedReader bufferedReaderF = null;
        FileReader fileReaderF = null;
        try {
            fileReaderF = new FileReader(faceDetect);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReaderF = new BufferedReader(fileReaderF);
        try {
            while ((lineF = (bufferedReaderF.readLine())) != null) {
                if (lineF.equals("1")) {
                    detectFace = true;
                } else if (lineF.equals("0")) {
                    detectFace = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedReaderF != null) {
            try {
                bufferedReaderF.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Check the setting for the motion detection
        String motion = "setting/motion.txt";                                     //Check the setting for the motion detection
        String lineM = null;

        BufferedReader bufferedReaderM = null;
        FileReader fileReaderM = null;
        try {
            fileReaderM = new FileReader(motion);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReaderM = new BufferedReader(fileReaderM);
        try {
            while ((lineM = (bufferedReaderM.readLine())) != null) {
                if (lineM.equals("1")){
                    motionDetect = true;
                } else if (lineM.equals("0")){
                    motionDetect = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedReaderM != null) {
            try {
                bufferedReaderM.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}