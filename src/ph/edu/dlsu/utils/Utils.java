package ph.edu.dlsu.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static Image mat2Image(Mat frame){

        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);

        return new Image(new ByteArrayInputStream(buffer.toArray()));

    }

    public static ImageView loadImage2View(String path, double width, double height){

        ImageView img = null;
        try (InputStream is = Files.newInputStream(Paths.get(path))){
            img = new ImageView(new Image(is));
            img.setFitWidth(width);
            img.setFitHeight(height);
        } catch (IOException e){
            System.out.println("Failed to load image!");
        }

        return img;

    }

}
