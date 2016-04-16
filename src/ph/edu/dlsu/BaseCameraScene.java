package ph.edu.dlsu;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import ph.edu.dlsu.utils.Utils;

import java.io.IOException;
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

    public abstract void onCameraFrame(Mat frame) throws IOException;

    public void startCamera(){

//        //Accessing ipCameras
////        this.capture.open("http://121.96.255.156:1024/?dummy=video.mjpeg");
////        this.capture.open("http://208.42.203.54:8588/view/viewer_index.shtml?id=722?dummy=video.mjpg");
//        this.capture.open("http://115.42.155.199/axis-cgi/mjpg/video.cgi?camera=4&resolution=352x288&1459247480335");
//
//        if(this.capture.isOpened()){
//            Runnable frameGrabber = () -> {
//                Image imageToShow = grabFrame();
//                currentFrame.setImage(imageToShow);
//            };
//             this.timer = Executors.newSingleThreadScheduledExecutor();
//            this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
//        }
//        else {
//            System.err.println("Failed to open the ipCamera");
//        }

        //Search for external cameras!
        int maxCam = 5;

        for (int index = 1; index <= maxCam; index++){

            this.capture.open(index);

            if(this.capture.isOpened()){
                Runnable frameGrabber = () -> {
                    Image imageToShow = grabFrame();
                    currentFrame.setImage(imageToShow);
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                System.out.println("External camera at port " + index + " opened");

                break;
            }
            else {
                System.err.println("Failed to open the external camera at port " + index);
            }

                                                            //Open the built-in camera if external camera was not found!
            if (index == maxCam){
                this.capture.open(0);

                if(this.capture.isOpened()){
                    Runnable frameGrabber = () -> {
                        Image imageToShow = grabFrame();
                        currentFrame.setImage(imageToShow);
                    };

                    this.timer = Executors.newSingleThreadScheduledExecutor();
                    this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                    System.out.println("Built-in camera opened");

                    break;
                }
                else {
                    System.err.println("Failed to open the built-in camera at port 0");
                }
            }

        }

    }

    public void stopCamera(){                               //Open to stopCamera
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

    public Image grabFrame(){                               //Grab image or capture image
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