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

    private static final String photoViewerStyle = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\src\\ph\\edu\\dlsu\\css\\photo-viewer.css";
    //    private static final String testImagePath = "http://mikecann.co.uk/wp-content/uploads/2009/12/javafx_logo_color_1.jpg"; // For Loading Online Images

    private static final String testImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap1.png";  // For Loading Local Images
    private static final String test2ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap2.png";  // For Loading Local Images
    private static final String test3ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap3.png";  // For Loading Local Images
    private static final String test4ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap4.png";  // For Loading Local Images
    private static final String test5ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap5.png";  // For Loading Local Images
    private static final String test6ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap6.png";  // For Loading Local Images
    private static final String test7ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap7.png";  // For Loading Local Images
    private static final String test8ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap8.png";  // For Loading Local Images
    private static final String test9ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap9.png";  // For Loading Local Images
    private static final String test10ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap10.png";  // For Loading Local Images
    private static final String test11ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap11.png";  // For Loading Local Images
    private static final String test12ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap12.png";  // For Loading Local Images
    private static final String test13ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap13.png";  // For Loading Local Images
    private static final String test14ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap14.png";  // For Loading Local Images
    private static final String test15ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap15.png";  // For Loading Local Images
    private static final String test16ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap16.png";  // For Loading Local Images
    private static final String test17ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap17.png";  // For Loading Local Images
    private static final String test18ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap18.png";  // For Loading Local Images
    private static final String test19ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap19.png";  // For Loading Local Images
    private static final String test20ImagePath = "C:\\Users\\User\\OneDrive\\Documents\\DLSU\\5th Year 2nd Term\\ObjectpL\\Project\\CameraViewer-v-1-0\\snap20.png";  // For Loading Local Images

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

    private static double sceneWidth;
    private static double sceneHeight;

    public static void show(){

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(400);
        stage.setY(120);

        double scale = 1.5;
        sceneWidth = 640.0 * scale;
        sceneHeight = 360.0 * scale;

        Group root = new Group();

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.setFill(null);

        try {
            scene.getStylesheets().add(Paths.get(photoViewerStyle).toUri().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);

        initFullScreenMode();
        initMovableWindow();
        initializeImages();

        currentImageView = createImageView(scene.heightProperty());

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(sceneWidth, sceneHeight);
        imagePane.getChildren().add(currentImageView);

        imagePane.maxWidthProperty().bind(scene.widthProperty());
        imagePane.minWidthProperty().bind(scene.widthProperty());

        imagePane.maxHeightProperty().bind(scene.heightProperty());
        imagePane.minHeightProperty().bind(scene.heightProperty());

        imagePane.setAlignment(Pos.CENTER);

        Group buttonGroup = createButtonPanel(scene);

        caption = createTickerControl(stage, 85);

        Node closeButton = createCloseButton();

        root.getChildren().addAll(
                imagePane,
                buttonGroup,
                caption,
                closeButton
        );

        stage.setFullScreen(true);
        stage.show();

    }

    private static void initFullScreenMode() {
        Scene scene = stage.getScene();

        scene.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }

    private static void initMovableWindow() {
        Scene scene = stage.getScene();

        scene.setOnMousePressed(mouseEvent
                -> anchorPt = new Point2D(mouseEvent.getScreenX(),
                mouseEvent.getScreenY())
        );

        scene.setOnMouseDragged(mouseEvent -> {
            if (anchorPt != null && previousLocation != null) {
                stage.setX(previousLocation.getX()
                        + mouseEvent.getScreenX()
                        - anchorPt.getX());
                stage.setY(previousLocation.getY()
                        + mouseEvent.getScreenY()
                        - anchorPt.getY());
            }
        });

        scene.setOnMouseReleased(mouseEvent
                -> previousLocation = new Point2D(stage.getX(),
                stage.getY())
        );

        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                (WindowEvent t) -> previousLocation = new Point2D(stage.getX(),
                        stage.getY()));
    }

    private static void initializeImages(){
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

    private static Group createButtonPanel(Scene scene) {
        Group buttonGroup = new Group();
        Rectangle buttonArea = new Rectangle(0, 0, 65, 30);
        buttonArea.getStyleClass().add("button-panel");
        buttonGroup.getChildren().add(buttonArea);

        Arc leftButton = new Arc(12, 16, 15, 15, -30, 60);
        leftButton.setType(ArcType.ROUND);
        leftButton.getStyleClass().add("left-arrow");

        leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
                (mouseEvent) -> {
                    if (currentIndex == 0 || loading.get()) return;
                    int indx = gotoImageIndex(ButtonMove.PREV);
                    if (indx > -1) {
                        loadImage(imageFiles.get(indx));
                    }
                });

        Arc rightButton = new Arc(12, 16, 15, 15, 180 - 30, 60);
        rightButton.setType(ArcType.ROUND);
        rightButton.getStyleClass().add("right-arrow");

        rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
                (mouseEvent) -> {

                    if (currentIndex == imageFiles.size() - 1
                            || loading.get()) return;
                    int indx = gotoImageIndex(ButtonMove.NEXT);
                    if (indx > -1) {
                        loadImage(imageFiles.get(indx));
                    }
                });

        buttonGroup.getChildren().addAll(leftButton, rightButton);

        buttonGroup.translateXProperty()
                .bind(scene.widthProperty()
                        .subtract(buttonArea.getWidth()).divide(2));
        buttonGroup.translateYProperty()
                .bind(scene.heightProperty()
                        .subtract(buttonArea.getHeight() + 6));

        scene.setOnMouseEntered((MouseEvent me) -> {
            FadeTransition fadeButtons =
                    new FadeTransition(Duration.millis(500), buttonGroup);
            fadeButtons.setFromValue(0.0);
            fadeButtons.setToValue(1.0);
            fadeButtons.play();
        });

        scene.setOnMouseExited((MouseEvent me) -> {
            FadeTransition fadeButtons =
                    new FadeTransition(Duration.millis(500), buttonGroup);
            fadeButtons.setFromValue(1);
            fadeButtons.setToValue(0);
            fadeButtons.play();
        });

        return buttonGroup;
    }

    private static Node createCloseButton() {
        Scene scene = stage.getScene();
        Group closeButton = new Group();
        closeButton.setId(CLOSE_BUTTON_ID);
        Node closeBackground = new Circle(6, 0, 8);
        closeBackground.setId("close-circle");
        Node closeXmark = new Text(2, 4, "X");
        closeButton.translateXProperty()
                .bind(scene.widthProperty()
                        .subtract(15));
        closeButton.setTranslateY(10);
        closeButton.getChildren()
                .addAll(closeBackground, closeXmark);

        closeButton.setOnMouseClicked(mouseEvent -> stage.close());
        return closeButton;
    }

    private static ImageView createImageView(ReadOnlyDoubleProperty heightProperty) {
        Scene scene = stage.getScene();
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(heightProperty);
        return imageView;
    }

    private static boolean isValidImageFile(String url) {
        List<String> imgTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp");
        return imgTypes.stream()
                .anyMatch(url::endsWith);
    }

    private static void addImage(String url) {
        if (isValidImageFile(url)) {
            currentIndex += 1;
            imageFiles.add(currentIndex, url);
        }
    }

    private static int gotoImageIndex(ButtonMove direction) {
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

    private static void loadImage(String url) {
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

//        FlowPane tickerContent = new FlowPane();
//
//        Text news = new Text();
//        news.setText("JavaFX 8.0 News! | 85 and sunny | :)");
//        news.setFill(Color.DARKGREY);
//        news.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
//
//        tickerContent.getChildren().add(news);
//        DoubleProperty centerContentY = new SimpleDoubleProperty();
//        centerContentY.bind(
//                clipRegion.heightProperty()
//                        .divide(2)
//                        .subtract(tickerContent.heightProperty()
//                                .divide(2)));
//        tickerContent.translateYProperty().bind(centerContentY);
//        tickerArea.getChildren().add(tickerContent);
//
//        TranslateTransition tickerScroller = new TranslateTransition();
//        tickerScroller.setNode(tickerContent);
//        tickerScroller.setDuration(
//                Duration.millis(scene.getWidth() * 40));
//        tickerScroller.fromXProperty()
//                .bind(scene.widthProperty());
//        tickerScroller.toXProperty()
//                .bind(tickerContent.widthProperty()
//                        .negate());
//
//
//        tickerScroller.setOnFinished((ActionEvent ae) -> {
//            tickerScroller.stop();
//            tickerScroller.setDuration(
//                    Duration.millis(scene.getWidth() * 40));
//            tickerScroller.playFromStart();
//        });
//
//
//        stage.setOnShown(windowEvent -> tickerScroller.play());
        return tickerArea;
    }


}
