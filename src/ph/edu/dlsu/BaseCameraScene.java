package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import ph.edu.dlsu.utils.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class BaseCameraScene {

    MenuHBox menuBox;

    double displayWidth;
    double displayHeight;

    double frameWidth;
    double frameHeight;

    final double menuWidth = 220;
    final double menuHeight = 40;

    ImageView currentFrame;

    ScheduledExecutorService timer;

    VideoCapture capture = new VideoCapture();

    public abstract Parent createCameraContent();

    public abstract void createHMenu();

    public abstract void onCameraFrame(Mat frame);

    public void startCamera(){

        this.capture.open(0);

        if(this.capture.isOpened()){
            Runnable frameGrabber = () -> {
                Image imageToShow = grabFrame();
                currentFrame.setImage(imageToShow);
            };

            this.timer = Executors.newSingleThreadScheduledExecutor();
            this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
        }
        else {
            System.err.println("Failed to open the camera.");
        }

    }

    public void stopCamera(){
        try{
            this.timer.shutdown();
            this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e){
            System.err.println("Exception in stopping the frame capture, trying to release the camera now." + e);
        }

        this.capture.release();
        if(currentFrame != null){
            this.currentFrame.setImage(null);
        }

    }

    public Image grabFrame(){
        Image imageToShow = null;
        Mat frame = new Mat();

        if(this.capture.isOpened()){
            try{
                this.capture.read(frame);

                if(!frame.empty()){
                    onCameraFrame(frame);

                    imageToShow = Utils.mat2Image(frame);
                }
            } catch (Exception e){
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return imageToShow;
    }

}