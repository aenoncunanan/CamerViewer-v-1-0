package ph.edu.dlsu.utils;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageBox {

    private static final String photoViewerStyle = "src//ph//edu//dlsu//css//photo-viewer.css";
    //    private static final String testImagePath = "http://mikecann.co.uk/wp-content/uploads/2009/12/javafx_logo_color_1.jpg"; // For Loading Online Images

    private static final String testImagePath = "Shots//Snaps//snap1.png";  // For Loading Local Images
    private static final String test2ImagePath = "Shots//Snaps//snap2.png";  // For Loading Local Images
    private static final String test3ImagePath = "Shots//Snaps//snap3.png";  // For Loading Local Images
    private static final String test4ImagePath = "Shots//Snaps//snap4.png";  // For Loading Local Images
    private static final String test5ImagePath = "Shots//Snaps//snap5.png";  // For Loading Local Images
    private static final String test6ImagePath = "Shots//Snaps//snap6.png";  // For Loading Local Images
    private static final String test7ImagePath = "Shots//Snaps//snap7.png";  // For Loading Local Images
    private static final String test8ImagePath = "Shots//Snaps//snap8.png";  // For Loading Local Images
    private static final String test9ImagePath = "Shots//Snaps//snap9.png";  // For Loading Local Images
    private static final String test10ImagePath = "Shots//Snaps//snap10.png";  // For Loading Local Images
    private static final String test11ImagePath = "Shots//Snaps//snap11.png";  // For Loading Local Images
    private static final String test12ImagePath = "Shots//Snaps//snap12.png";  // For Loading Local Images
    private static final String test13ImagePath = "Shots//Snaps//snap13.png";  // For Loading Local Images
    private static final String test14ImagePath = "Shots//Snaps//snap14.png";  // For Loading Local Images
    private static final String test15ImagePath = "Shots//Snaps//snap15.png";  // For Loading Local Images
    private static final String test16ImagePath = "Shots//Snaps//snap16.png";  // For Loading Local Images
    private static final String test17ImagePath = "Shots//Snaps//snap17.png";  // For Loading Local Images
    private static final String test18ImagePath = "Shots//Snaps//snap18.png";  // For Loading Local Images
    private static final String test19ImagePath = "Shots//Snaps//snap19.png";  // For Loading Local Images
    private static final String test20ImagePath = "Shots//Snaps//snap20.png";  // For Loading Local Images

    private static final String CLOSE_BUTTON_ID = "close-button";

    private static final List<String> imageFiles = new ArrayList<>();

    private static int currentIndex = -1;

    public enum ButtonMove {
        NEXT, PREV
    };

    private static Group caption;

    private static ImageView currentImageView;

    private static AtomicBoolean loading = new AtomicBoolean();

    private static Stage stage;

    private static Point2D anchorPt;
    private static Point2D previousLocation;

    private static double sceneWidth;                       // To define the Width of the scene
    private static double sceneHeight;                      // To define the Height of the scene

    public static void show() {

        stage = new Stage();                                // To set the new Stage
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(400);
        stage.setY(120);

        double scale = 1.5;                                 //
        sceneWidth = 640.0 * scale;                         // To know the Scale of Width
        sceneHeight = 360.0 * scale;                        // To know the Scale of Height

        Group root = new Group();                           // To define new Group

        Scene scene = new Scene(root, sceneWidth, sceneHeight);   // Set the scene from Width and Height
        scene.setFill(null);

        try {
            scene.getStylesheets().add(Paths.get(photoViewerStyle).toUri().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);                              // Set the stage scene

        initFullScreenMode();                               // To initialize FullScreenMode
        initMovableWindow();                                // To initialize MovableWindow
        initializeImages();                                 // To initialize Images

        currentImageView = createImageView(scene.heightProperty());

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(sceneWidth, sceneHeight);
        imagePane.getChildren().add(currentImageView);

        imagePane.maxWidthProperty().bind(scene.widthProperty());
        imagePane.minWidthProperty().bind(scene.widthProperty());

        imagePane.maxHeightProperty().bind(scene.heightProperty());
        imagePane.minHeightProperty().bind(scene.heightProperty());

        imagePane.setAlignment(Pos.CENTER);                 // To align the screen to center

        Group buttonGroup = createButtonPanel(scene);

        caption = createTickerControl(stage, 85);

        Node closeButton = createCloseButton();             // To create close button

        root.getChildren().addAll(                          // To get the functions of all the children
                imagePane,
                buttonGroup,
                caption,
                closeButton
        );

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");                    // Set the message when going in fullscreen mode
        stage.show();

    }

    private static void initFullScreenMode() {                  // Function which set the screen to fullscreen
        Scene scene = stage.getScene();                         // To set the stage.scene

        scene.setOnMouseClicked((MouseEvent event) -> {         // To allow the user to click the set event
            if (event.getClickCount() == 2) {                   // To set the fullscreen method
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }

    private static void initMovableWindow() {                           // Function that simplifies the changes of the windows
        Scene scene = stage.getScene();

        scene.setOnMousePressed(mouseEvent                              // To allow the user to click Point2D
                -> anchorPt = new Point2D(mouseEvent.getScreenX(),
                mouseEvent.getScreenY())
        );

        scene.setOnMouseDragged(mouseEvent -> {                         // To allow the user to click anchorPT
            if (anchorPt != null && previousLocation != null) {
                stage.setX(previousLocation.getX()
                        + mouseEvent.getScreenX()
                        - anchorPt.getX());
                stage.setY(previousLocation.getY()
                        + mouseEvent.getScreenY()
                        - anchorPt.getY());
            }
        });

        scene.setOnMouseReleased(mouseEvent                             // To allow the user to click the previousLocation
                -> previousLocation = new Point2D(stage.getX(),
                stage.getY())
        );

        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                (WindowEvent t) -> previousLocation = new Point2D(stage.getX(),
                        stage.getY()));
    }

    private static void initializeImages() {                                 // To allow the function to load images
        try {
            //addImage(testImagePath);    //For Loading Online Images
            addImage(Paths.get(test20ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test19ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test18ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test17ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test16ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test15ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test14ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test13ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test12ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test11ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test10ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test9ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test8ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test7ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test6ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test5ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test4ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test3ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(test2ImagePath).toUri().toURL().toString()); //For Loading Local Images
            addImage(Paths.get(testImagePath).toUri().toURL().toString()); //For Loading Local Images
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (currentIndex > -1) {
            loadImage(imageFiles.get(currentIndex));
        }
    }

    private static Group createButtonPanel(Scene scene) {                   // To create button
        Group buttonGroup = new Group();
        Rectangle buttonArea = new Rectangle(0, 0, 65, 30);
        buttonArea.getStyleClass().add("button-panel");
        buttonGroup.getChildren().add(buttonArea);

        Arc leftButton = new Arc(12, 16, 15, 15, -30, 60);                  // To click left arrow
        leftButton.setType(ArcType.ROUND);
        leftButton.getStyleClass().add("left-arrow");

        leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED,                // To know the currentIndex
                (mouseEvent) -> {
                    if (currentIndex == 0 || loading.get()) return;
                    int indx = gotoImageIndex(ButtonMove.PREV);
                    if (indx > -1) {
                        loadImage(imageFiles.get(indx));
                    }
                });

        Arc rightButton = new Arc(12, 16, 15, 15, 180 - 30, 60);            // To click right arrow
        rightButton.setType(ArcType.ROUND);
        rightButton.getStyleClass().add("right-arrow");

        rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED,               // To allow the user to view images
                (mouseEvent) -> {

                    if (currentIndex == imageFiles.size() - 1|| loading.get())
                        return;
                    int indx = gotoImageIndex(ButtonMove.NEXT);
                    if (indx > -1) {
                        loadImage(imageFiles.get(indx));
                    }
                });

        buttonGroup.getChildren().addAll(leftButton, rightButton);          // To allow the user to click left ot right button

        buttonGroup.translateXProperty()
                .bind(scene.widthProperty()
                        .subtract(buttonArea.getWidth()).divide(2));
        buttonGroup.translateYProperty()
                .bind(scene.heightProperty()
                        .subtract(buttonArea.getHeight() + 6));

        scene.setOnMouseEntered((MouseEvent me) -> {                        //To allow the user to click
            FadeTransition fadeButtons =
                    new FadeTransition(Duration.millis(500), buttonGroup);
            fadeButtons.setFromValue(0.0);
            fadeButtons.setToValue(1.0);
            fadeButtons.play();
        });

        scene.setOnMouseExited((MouseEvent me) -> {                         //To allow the user to click
            FadeTransition fadeButtons =
                    new FadeTransition(Duration.millis(500), buttonGroup);
            fadeButtons.setFromValue(1);
            fadeButtons.setToValue(0);
            fadeButtons.play();
        });

        return buttonGroup;
    }

    private static Node createCloseButton() {                               // This function is used to close the stage
        Scene scene = stage.getScene();
        Group closeButton = new Group();
        closeButton.setId(CLOSE_BUTTON_ID);
        Node closeBackground = new Circle(6, 0, 8);
        closeBackground.setId("close-circle");
        Node closeXmark = new Text(2, 4, "X");
        closeButton.translateXProperty()
                .bind(scene.widthProperty()
                        .subtract(140));
        closeButton.setTranslateY(15);
        closeButton.getChildren()
                .addAll(closeBackground, closeXmark);

        closeButton.setOnMouseClicked(mouseEvent -> stage.close());
        return closeButton;
    }

    private static ImageView createImageView(ReadOnlyDoubleProperty heightProperty) {       // This function serves the user to view image
        Scene scene = stage.getScene();
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(heightProperty);
        return imageView;
    }

    private static boolean isValidImageFile(String url) {                                   // This function is to validate which kind of format are ready to view
        List<String> imgTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp");
        return imgTypes.stream()
                .anyMatch(url::endsWith);
    }

    private static void addImage(String url) {                                              // This function adds image
        if (isValidImageFile(url)) {
            currentIndex += 1;
            imageFiles.add(currentIndex, url);
        }
    }

    private static int gotoImageIndex(ButtonMove direction) {                               // This function will validate the direction of the images saved
        int size = imageFiles.size();
        if (size == 0) {
            currentIndex = -1;
        } else if (direction == ButtonMove.NEXT
                && size > 1
                && currentIndex < size - 1) {
            currentIndex += 1;
        } else if (direction == ButtonMove.PREV
                && size > 1
                && currentIndex > 0) {
            currentIndex -= 1;
        }
        return currentIndex;
    }

    private static Task createWorker(final String url) {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                Image image = new Image(url, false);
                Platform.runLater(() -> {

                    currentImageView.setImage(image);
                    loading.set(false);
                });
                return true;
            }
        };
    }

    private static void loadImage(String url) {                                             // To load image itself
        if (!loading.getAndSet(true)) {
            Task loadImage = createWorker(url);
            new Thread(loadImage).start();
        }
    }

    private static Group createTickerControl(Stage stage, double rightPadding) {
        Scene scene = stage.getScene();

        Group tickerArea = new Group();
        Rectangle tickerRect = new Rectangle(scene.getWidth(), 38);
        tickerRect.getStyleClass().add("ticker-border");
        tickerRect.setFill(Color.BLACK);
        tickerRect.setVisible(false);

        Rectangle clipRegion = new Rectangle(scene.getWidth(), 38);
        clipRegion.getStyleClass().add("ticker-clip-region");
        tickerArea.setClip(clipRegion);

        tickerArea.setTranslateX(6);
        tickerArea.translateYProperty()
                .bind(scene.heightProperty()
                        .subtract(tickerRect.getHeight() + 6));
        tickerRect.widthProperty()
                .bind(scene.widthProperty()
                        .subtract(rightPadding));
        clipRegion.widthProperty()
                .bind(scene.widthProperty()
                        .subtract(rightPadding));
        tickerArea.getChildren().add(tickerRect);

        return tickerArea;
    }


}
