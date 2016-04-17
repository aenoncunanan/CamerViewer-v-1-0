package ph.edu.dlsu.utils;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public class VideoBox {

    private static final int vidCount = videoCount();

    private static final String buttonPanelStyle = "src//ph//edu//dlsu//css//playing-video.css";
    private static  final String filePath = "Shots//vidclips//vidClip" + vidCount + ".avi";
//    private static  final String filePath = "Shots//vidclips//GameofThronesTheme.avi";
    private static String url = null;
    static {
        try {
            url = Paths.get(filePath).toUri().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Couldn't load video file");
        }
    }

    private static final String MEDIA_VIEW_ID = "media-view";
    private static final String STOP_BUTTON_ID = "stop-button";
    private static final String PLAY_BUTTON_ID = "play-button";
    private static final String PAUSE_BUTTON_ID = "pause-button";
    private static final String CLOSE_BUTTON_ID = "close-button";
    private static final String SEEK_POS_SLIDER_ID = "seek-position-slider";
    private static Point2D anchorPt;
    private static Point2D previousLocation;
    private static MediaPlayer mediaPlayer;
    private static ChangeListener<Duration> progressListener;
    private static Stage stage;

    public static int videoCount(){
        String fileName = "Shots/VidClips/videoCount.txt";

        String line = null;

        int videoCount = 1;

        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if ((Integer.parseInt(line)) == 1) {
                    videoCount = 10;
                } else {
                    videoCount = Integer.parseInt(line)-1 ;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }


        return videoCount;
    }

    public static void show() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setFullScreen(true);
        stage.setX(400);
        stage.setY(120);

        double scale = 1.5;

        Group root = new Group();
        Scene scene = new Scene(root, 640.0 * scale, 360.0 * scale, Color.rgb(0, 0, 0, 0));


        try {                                                               // Load JavaFX CSS style
            scene.getStylesheets().add(Paths.get(buttonPanelStyle).toUri().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);


        initMovableWindow();                                                // Initialize stage to be movable via mouse


        initFullScreenMode();                                               // Initialize stage to have fullscreen ability


        MediaView mediaView = createMediaView();                            // Create a media view to display video


        Node applicationArea = createApplicationArea();                     // application area


        Node buttonPanel = createButtonPanel();                             // Create the button panel


        Slider progressSlider = createSlider();                             // Progress and seek position slider


        progressListener = (observable, oldValue, newValue) ->              // Update slider as video is progressing
                progressSlider.setValue(newValue.toSeconds());



        Node closeButton = createCloseButton();                             // Create the close button


        root.getChildren()                                                  // Add Nodes to the Group
                .addAll(applicationArea,
                        mediaView,
                        buttonPanel,
                        progressSlider,
                        closeButton);


        try {                                                               // Play video file
            playMedia(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        stage.setFullScreen(true);
        stage.show();
    }

    private static void initFullScreenMode() {                              //
        Scene scene = stage.getScene();

        scene.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }



    private static void initMovableWindow() {                               //
        Scene scene = stage.getScene();

        scene.setOnMousePressed(mouseEvent                                  // starting initial anchor point
                -> anchorPt = new Point2D(mouseEvent.getScreenX(),
                mouseEvent.getScreenY())
        );

        scene.setOnMouseDragged(mouseEvent -> {                             // dragging the entire stage
            if (anchorPt != null && previousLocation != null) {
                stage.setX(previousLocation.getX()
                        + mouseEvent.getScreenX()
                        - anchorPt.getX());
                stage.setY(previousLocation.getY()
                        + mouseEvent.getScreenY()
                        - anchorPt.getY());
            }
        });

        scene.setOnMouseReleased(mouseEvent                                 // set the current location
                -> previousLocation = new Point2D(stage.getX(),
                stage.getY())
        );

        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,                     // Initialize previousLocation after Stage is shown
                (WindowEvent t) -> previousLocation = new Point2D(stage.getX(),
                        stage.getY()));
    }


    private static Node createApplicationArea() {
        Scene scene = stage.getScene();
        Rectangle applicationArea = new Rectangle();

        applicationArea.setId("app-area");                                  // add selector to style app-area

        applicationArea.widthProperty()                                     // make the app area rectangle the size of the scene.
                .bind(scene.widthProperty());
        applicationArea.heightProperty()
                .bind(scene.heightProperty());
        return applicationArea;
    }

    private static Node createButtonPanel() {
        Scene scene = stage.getScene();

        Group buttonGroup = new Group();                                    // create button control panel

        Rectangle buttonArea = new Rectangle(60, 30);                       // Button area
        buttonArea.setId("button-area");
        buttonGroup.getChildren()
                .add(buttonArea);

        Node stopButton = new Rectangle(10, 10);                            // stop button control
        stopButton.setId(STOP_BUTTON_ID);
        stopButton.setOnMousePressed(mouseEvent -> {
            if (mediaPlayer != null) {
                updatePlayAndPauseButtons(true);
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
            }
        });                                                                 // setOnMousePressed()

                                                                            // play button
        Arc playButton = new Arc(12, // center x
                16, // center y
                15, // radius x
                15, // radius y
                150, // start angle
                60); // length
        playButton.setId(PLAY_BUTTON_ID);
        playButton.setType(ArcType.ROUND);
        playButton.setOnMousePressed(mouseEvent -> mediaPlayer.play());
        // pause control
        Group pauseButton = new Group();
        pauseButton.setId(PAUSE_BUTTON_ID);
        Node pauseBackground = new Circle(12, 16, 10);
        pauseBackground.getStyleClass().add("pause-circle");
        Node firstLine = new Line(6, // start x
                6, // start y
                6, // end x
                14); // end y
        firstLine.getStyleClass()
                .add("pause-line");
        firstLine.setStyle("-fx-translate-x: 34;");
        Node secondLine = new Line(6, // start x
                6, // start y
                6, // end x
                14); // end y
        secondLine.getStyleClass().add("pause-line");
        secondLine.setStyle("-fx-translate-x: 38;");
        pauseButton.getChildren()
                .addAll(pauseBackground, firstLine, secondLine);
        pauseButton.setOnMousePressed(mouseEvent -> {
            if (mediaPlayer != null) {
                updatePlayAndPauseButtons(true);
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                }
            }
        });

        playButton.setOnMousePressed(mouseEvent -> {                        // setOnMousePressed()
            if (mediaPlayer != null) {
                updatePlayAndPauseButtons(false);
                mediaPlayer.play();
            }
        });

        buttonGroup.getChildren()                                           // setOnMousePressed()
                .addAll(stopButton,
                        playButton,
                        pauseButton);


        buttonGroup.translateXProperty()                                    // move button group when scene is resized
                .bind(scene.widthProperty()
                        .subtract(buttonArea.getWidth() + 6));
        buttonGroup.translateYProperty()
                .bind(scene.heightProperty()
                        .subtract(buttonArea.getHeight() + 6));
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

        closeButton.setOnMouseClicked(mouseEvent -> stage.close());         // go to home menu
        closeButton.setOnMouseClicked(mouseEvent -> {
            stage.close();
            mediaPlayer.stop();
            mediaPlayer = null;
        });
        return closeButton;
    }

    private static void playMedia(String url) {
        Scene scene = stage.getScene();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.setOnPaused(null);
            mediaPlayer.setOnPlaying(null);
            mediaPlayer.setOnReady(null);
            mediaPlayer.currentTimeProperty()
                    .removeListener(progressListener);
            mediaPlayer.setAudioSpectrumListener(null);
        }

        Media media = new Media(url);


        for (String s : media.getMetadata().keySet()) {                     // display media's metadata
            System.out.println(s);
        }

        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.currentTimeProperty()                                   // as the media is playing move the slider for progress
                .addListener(progressListener);
        mediaPlayer.setOnReady(() -> {
            updatePlayAndPauseButtons(false);
            Slider progressSlider =
                    (Slider) scene.lookup("#" + SEEK_POS_SLIDER_ID);
            progressSlider.setValue(0);
            progressSlider.setMax(mediaPlayer.getMedia()
                    .getDuration()
                    .toSeconds());
            mediaPlayer.play();
        }); // setOnReady()

        mediaPlayer.setOnEndOfMedia(() -> {                                 // back to the beginning
            updatePlayAndPauseButtons(true);

            mediaPlayer.stop();                                             // change buttons to play and rewind
        }); // setOnEndOfMedia()


        MediaView mediaView = (MediaView) scene.lookup("#" + MEDIA_VIEW_ID);// set the media player to display video
        mediaView.setMediaPlayer(mediaPlayer);
    }


    /**
     * Create a MediaView node.
     *
     * @return MediaView
     */
    private static MediaView createMediaView() {
        MediaView mediaView = new MediaView();
        mediaView.setId(MEDIA_VIEW_ID);

        mediaView.setSmooth(true);                                          //mediaView.setPreserveRatio(true);

        mediaView.fitWidthProperty()
                .bind(stage.getScene()
                        .widthProperty());
        mediaView.fitHeightProperty()
                .bind(stage.getScene()
                        .heightProperty());

        mediaView.setOnError(mediaErrorEvent -> mediaErrorEvent.getMediaError()
                .printStackTrace());
        return mediaView;
    }


    /**
     * Sets play button visible and pause button not visible when
     * playVisible is true otherwise the opposite.
     * <p>
     * * @param playVisible - value of true the play becomes visible
     * and pause non visible, otherwise the opposite.
     */
    private static void updatePlayAndPauseButtons(boolean playVisible) {
        Scene scene = stage.getScene();
        Node playButton = scene.lookup("#" + PLAY_BUTTON_ID);
        Node pauseButton = scene.lookup("#" + PAUSE_BUTTON_ID);

        playButton.setVisible(playVisible);                                 // hide or show buttons
        pauseButton.setVisible(!playVisible);
        if (playVisible) {

            playButton.toFront();                                           // show play button
            pauseButton.toBack();
        } else {

            pauseButton.toFront();                                          // show pause button
            playButton.toBack();
        }
    }

    /**
     * A position slider to seek backward and forward
     * that is bound to a media player control.
     *
     * @return Slider control bound to media player.
     */
    private static Slider createSlider() {
        Slider slider = new Slider(0, 100, 1);
        slider.setId(SEEK_POS_SLIDER_ID);
        slider.valueProperty()
                .addListener((observable) -> {
                    if (slider.isValueChanging()) {
                        // must check if media is paused before seeking
                        if (mediaPlayer != null &&
                                mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                            // convert seconds to millis
                            double dur = slider.getValue() * 1000;
                            mediaPlayer.seek(Duration.millis(dur));
                        }
                    }
                }); //addListener()
        Scene scene = stage.getScene();
        slider.setTranslateX(10);
        slider.translateYProperty()
                .bind(scene.heightProperty()
                        .subtract(30));
        return slider;
    }
}