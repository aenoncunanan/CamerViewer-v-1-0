package ph.edu.dlsu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.opencv.core.Core;
import ph.edu.dlsu.utils.*;

import java.awt.*;
import java.io.*;

public class Main extends Application {

    private static double displayWidth;
    private static double displayHeight;

    static Stage stage;
    static Scene logInScene;

    //Program defined username and password
    static String userDefault = "admin";
    static String passDefault = "123";
    static String guestUser = "guest";
    static String guestPass = "guest";

    static int mark = 0; //1 if user is guest, 1 if admin
    static Boolean guestOpt = null; //mark if guest user is enabled

    public Main() throws IOException, FontFormatException {
    }

    //Create the stage for the LogIn screen
    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScreenSize();                                     //Get the size of the user's screen
        logInScene = new Scene(createLogInContent());               //Create the contents of the Login scene
        stage = primaryStage;                                       //Set the stage as primary
        stage.setTitle("Green Screen: Login");                      //Name the title of the Login stage
        stage.setScene(logInScene);                                 //Set the scene for the Login stage
        stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
//        stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
        stage.show();
    }

    //Get the size of the user's screen
    private void initializeScreenSize() {
        ScreenSize screen = new ScreenSize();
        displayWidth = screen.getDisplayWidth();                    //Set the width of the user's screen
        displayHeight = screen.getDisplayHeight();                  //Set the height of the user's screen
    }

    //Create the contents of the Login scene
    private Parent createLogInContent() throws IOException {
        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        //Get the User's Settings
        getUser();

        //Get the User's option if guest user is enabled
        checkBoxReader();

        //Set the background image
        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Right.png", displayWidth, displayHeight);
        if (imgBackground != null) {
            rootNode.getChildren().add(imgBackground);
        }

        //Set the grid-pane's properties for the contents
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);                              //Set the grid pane at the center
        grid.setHgap(10);                                           //Set the horizontal gap between the rows
        grid.setVgap(10);                                           //Set the vertical gap between the columns
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Create a sign in button
        Button btn = new Button("Sign in");                         //Set the text in the box
        HBox hbBtn = new HBox(10);                                  //Create a horizontal box
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);                       //Place it at the bottom right part
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);                                      //Set it at Column 1, Row 4

        //Create new text for welcome
//        Text scenetitle = new Text("Welcome!");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
        Text scenetitle = new Text("Welcome!");
        scenetitle.setFont(Font.font("Asimov", FontWeight.NORMAL, 55));
//        scenetitle.setFont(Font.loadFont("file:res/fonts/AsimovWid.otf",55));
        scenetitle.setFill(Color.web("#009fe0"));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Create a new label for the username
        Text userName = new Text("Username: ");
        userName.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        userName.setFill(Color.web("#2c3e50"));
        grid.add(userName, 0, 1);                                   //Set it at Column 0, Row 1

        //Create a new Text field for the user's input
        TextField user = new TextField();
        user.setPromptText("Enter your username");                  //Set a message in the text field
        grid.add(user, 1, 1);                                       //Set it at Column 1, Row 1

        //Create a new label for the password
        Text pw = new Text("Password: ");
        pw.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        pw.setFill(Color.web("#2c3e50"));
        grid.add(pw, 0, 2);                                         //Set it at Column 0, Row 2

        //Create a new Text field for the user's input
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your password");                  //Set a message in the text field
        grid.add(pass, 1, 2);                                       //Set it at Column 1, Row 2

        final Text message = new Text();                            //Create a message when sign in button is pressed
        grid.add(message, 1, 5);                                    //Set it at Column 1, Row 5

        //Save user's settings as a text file
        File userFile = new File("setting/userFile.txt");
        BufferedWriter outFile = new BufferedWriter(new FileWriter(userFile));
        outFile.write(userDefault);
        outFile.newLine();
        outFile.write(passDefault);
        outFile.close();

        btn.setOnAction(event -> {
            message.setFill(Color.FIREBRICK);                       //Set the color of the message

            //Check if the text field for the username and password is not empty
            if ((user.getText() != null && !user.getText().isEmpty()) && (pass.getText() != null && !pass.getText().isEmpty())) {

                //Check if the username and password inputted by the user matched the  program defined username and password
                if (user.getText().equals(userDefault) && pass.getText().equals(passDefault)) {
                    message.setText("Welcome " + user.getText());                   //Prompt a message if the inputs are correct
                    onHome();                                                       //Enter the main program
                    user.clear();                                                   //Empty the username text field
                    pass.clear();                                                   //Empty the password text field
                    message.setText(null);                                          //Empty the message
                } else if (guestOpt){
                    if (user.getText().equals(guestUser) && pass.getText().equals(guestPass)) {
                        mark = 1;
                        message.setText("Welcome " + user.getText());                   //Prompt a message if the inputs are correct
                        onHome();                                                       //Enter the main program
                        user.clear();                                                   //Empty the username text field
                        pass.clear();                                                   //Empty the password text field
                        message.setText(null);                                          //Empty the message
                    }else {
                        message.setText("Username or Password\nmismatched!");           //Prompt a message if the inputs are incorrect
                        pass.clear();                                                   //Empty the password text field
                    }
                } else {
                    message.setText("Username or Password\nmismatched!");           //Prompt a message if the inputs are incorrect
                    pass.clear();                                                   //Empty the password text field
                }

            } else {
                message.setText("Username and password\nfield cannot be empty!");   //Prompt a message if the text fields are empty
            }

        });

        grid.setTranslateX(200);
        grid.setTranslateY(200);
        rootNode.getChildren().addAll(grid);

        return rootNode;

    }

    //Create the stage for the Login when Logout button was pressed
    public static void onLogIn(){
        mark = 0;
        getUser();                                                  //Get the User's Settings
        checkBoxReader();                                           //Get the User's Settings if guest user is enabled

        stage.setTitle("Green Screen: Login");                      //Name the title of the Login stage
        stage.setScene(logInScene);                                 //Set the scene for the Login stage
        stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
        stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
    }

    //Create the stage for the Home scene
    public static void onHome(){
        getUser();                                                  //Get the User's Settings
        checkBoxReader();

        if (mark == 1) {
            HomeGuest home = new HomeGuest();
            stage.setTitle("Green Screen: Home");                       //Name the title of the Home stage
            stage.setScene(
                    new Scene(home.main(), displayWidth, displayHeight)
            );                                                          //Set the scene for the Home stage
            stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
            stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
        }
        else{
            Home home = new Home();
            stage.setTitle("Green Screen: Home");                       //Name the title of the Home stage
            stage.setScene(
                    new Scene(home.main(), displayWidth, displayHeight)
            );                                                          //Set the scene for the Home stage
            stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
            stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
        }
    }

    //Create the stage for the Camera scene
    public static void onCamera() {

        if (mark == 1){
            CameraGuest camera = new CameraGuest();
            stage.setTitle("Green Screen: Live Stream");                //Name the title of the Camera stage
            stage.setScene(
                    new Scene(camera.createCameraContent(),
                            displayWidth, displayHeight)
            );                                                          //Set the scene for the Camera stage
            stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
            stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
        } else {
            Camera camera = new Camera();
            stage.setTitle("Green Screen: Live Stream");                //Name the title of the Camera stage
            stage.setScene(
                    new Scene(camera.createCameraContent(),
                            displayWidth, displayHeight)
            );                                                          //Set the scene for the Camera stage
            stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
            stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
        }
    }

    //Method that will show 10 latest video clips recorded by the program
    public static void onVideoClips() {
            VideoBox.show();
    }

    //Method that will show 20 latest snapshots captured by the program
    public static void onSnapShots() {
        ImageBox.show();
    }

    public static void onSetting() {
        Setting setting = new Setting();                            //Name the title of the Camera stage
        stage.setTitle("Green Screen: Setting");
        stage.setScene(
                new Scene(setting.main(), displayWidth, displayHeight)
        );                                                          //Set the scene for the Setting stage
        stage.setFullScreen(true);                                  //Set the stage in fullscreen mode
        stage.setFullScreenExitHint("");                            //Set the message when going in fullscreen mode
    }

    //Exit prompt and action
    public static boolean onExit() {

        boolean confirmQuit = ConfirmationBox.show(
                "Are you sure you want to exit?",
                "Yes",
                "No"
        );                                                            //Confirm if the user really wants to terminate the program

        if (confirmQuit) {
            Platform.exit();                                          //Terminate the program
        }

        return confirmQuit;                                           //Return the boolean value
    }

    //Get User's setting if guest user is enabled
    public static void checkBoxReader(){
        //Check the setting for the guest user
        String guest = "setting/guest.txt";
        String lineG = null;

        BufferedReader bufferedReaderG = null;
        FileReader fileReaderG = null;
        try {
            fileReaderG = new FileReader(guest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReaderG = new BufferedReader(fileReaderG);
        try {
            while ((lineG = (bufferedReaderG.readLine())) != null) {
                if (lineG.equals("1")){
                    guestOpt = true;
                } else if (lineG.equals("0")){
                    guestOpt = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedReaderG != null) {
            try {
                bufferedReaderG.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Get User Settings
    public static void getUser() {
        //Get the User's Settings
        String fileName = "setting/userFile.txt";
        String line = null;
        int counter = 0;
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (counter == 0) {
                    userDefault = line;
                } else if (counter == 1) {
                    passDefault = line;
                }
                counter++;
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
    }

    //Program will will run this first!
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);                 //Load the OpenCV Library
        launch(args);
    }

}