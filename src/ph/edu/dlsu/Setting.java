package ph.edu.dlsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import ph.edu.dlsu.utils.ScreenSize;
import ph.edu.dlsu.utils.Utils;

import java.io.*;

/**
 * Created by ${AenonCunanan} on 05/04/2016.
 */

public class Setting {

    ScreenSize screen = new ScreenSize();
    double displayWidth = screen.getDisplayWidth();
    double displayHeight = screen.getDisplayHeight();

    MenuHBox menuBox;

    final double menuWidth = 220;
    final double menuHeight = 40;

    String currentName = null;
    String currentPass = null;

    public void userFile(){
        String fileName = "setting/userFile.txt";

        String line = null;
        int counter = 0;

        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (counter == 0) {
                    currentName = line;
                } else if (counter == 1) {
                    currentPass = line;
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


    public Parent main(){

        Pane rootNode = new Pane();
        rootNode.setPrefSize(displayWidth, displayHeight);

        ImageView imgBackground = Utils.loadImage2View("res//images//Green-Screen-Right.png", displayWidth, displayHeight);
        if (imgBackground != null) {
            rootNode.getChildren().add(imgBackground);
        }

        userFile();

        //Set the grid-pane's properties for the contents
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);                              //Set the grid pane at the center
        grid.setHgap(10);                                           //Set the horizontal gap between the rows
        grid.setVgap(10);                                           //Set the vertical gap between the columns
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button btn = new Button("Confirm");                         //Set the text in the box
        HBox hbBtn = new HBox(10);                                  //Create a horizontal box
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);                       //Place it at the bottom right part
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);                                      //Set it at Column 1, Row 6

        //Create new text for welcome
        Text scenetitle = new Text("Setup account: ");
        scenetitle.setFont(Font.font("Asimov", FontWeight.NORMAL, 55));
        scenetitle.setFill(Color.web("#009fe0"));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Create a new label for the username
        Text userName = new Text("Current username: ");
        userName.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        userName.setFill(Color.web("#2c3e50"));
        grid.add(userName, 0, 1);                                           //Set it at Column 0, Row 1

        //Create a new Text field for the user's input
        TextField user = new TextField();
        user.setPromptText("Enter your current username");                  //Set a message in the text field
        grid.add(user, 1, 1);                                               //Set it at Column 1, Row 1

        //Create a new label for the password
        Text pw = new Text("Current password: ");
        pw.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        pw.setFill(Color.web("#2c3e50"));
        grid.add(pw, 0, 2);

        //Create a new Text field for the user's input
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your current password");                  //Set a message in the text field
        grid.add(pass, 1, 2);                                               //Set it at Column 1, Row 2

        //Create a new label for the username
        Text userNameNew = new Text("New username: ");
        userNameNew.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        userNameNew.setFill(Color.web("#2c3e50"));
        grid.add(userNameNew, 0, 3);                                        //Set it at Column 0, Row 3

        //Create a new Text field for the user's input
        TextField userNew = new TextField();
        userNew.setPromptText("Enter your new username");                  //Set a message in the text field
        grid.add(userNew, 1, 3);                                           //Set it at Column 1, Row 3

        //Create a new label for the password
        Text pwNew = new Text("New password: ");
        pwNew.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        pwNew.setFill(Color.web("#2c3e50"));
        grid.add(pwNew, 0, 4);

        //Create a new Text field for the user's input
        PasswordField passNew = new PasswordField();
        passNew.setPromptText("Enter your new password");                  //Set a message in the text field
        grid.add(passNew, 1, 4);                                           //Set it at Column 1, Row 4

        final Text message = new Text();                            //Create a message when sign in button is pressed
        grid.add(message, 1, 6);                                    //Set it at Column 1, Row 7

        btn.setOnAction(event -> {
            message.setFill(Color.FIREBRICK);                       //Set the color of the message

            //Check if the text field for the username and password is not empty
            if ((user.getText() != null && !user.getText().isEmpty()) && (pass.getText() != null && !pass.getText().isEmpty())
                    && (userNew.getText() != null && !userNew.getText().isEmpty()) && (passNew.getText() != null && !passNew.getText().isEmpty())
                    ) {

                //Check if the username and password inputted by the user matched the  program defined username and password
                if (user.getText().equals(currentName) && pass.getText().equals(currentPass)) {

                    File userFile = new File("setting/userFile.txt");
                    BufferedWriter outFile = null;
                    try {
                        outFile = new BufferedWriter(new FileWriter(userFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outFile.write(userNew.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outFile.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outFile.write(passNew.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    message.setText("Username and Password\nwas successfully changed!");                   //Prompt a message if the inputs are correct
                    user.clear();                                                   //Empty the username text field
                    pass.clear();                                                   //Empty the password text field
                    userNew.clear();                                                //Empty the new username text field
                    passNew.clear();                                                //Empty the new password text field
                } else {
                    message.setText("Username or Password\nmismatched!");           //Prompt a message if the inputs are incorrect
                    pass.clear();                                                   //Empty the password text field
                    userNew.clear();                                                //Empty the new username text field
                    passNew.clear();                                                //Empty the new password text field
                }

            } else {
                message.setText("Fields cannot be empty!");   //Prompt a message if the text fields are empty
                pass.clear();                                                   //Empty the password text field
                passNew.clear();                                                //Empty the new password text field
            }

        });

        grid.setTranslateX(150);
        grid.setTranslateY(200);

        createHMenu();

        rootNode.getChildren().addAll(grid, menuBox);

        return rootNode;
    }

    public void createHMenu() {

        final CustomMenuItem home = new CustomMenuItem("home", menuWidth, menuHeight);
        final CustomMenuItem logout = new CustomMenuItem("logout", menuWidth, menuHeight);
        final CustomMenuItem exit = new CustomMenuItem("exit", menuWidth, menuHeight);

        home.setOnMouseClicked(e -> {
            Main.onHome();
        });

        logout.setOnMouseClicked(e -> {
            Main.onLogIn();
        });

        exit.setOnMouseClicked(e -> {
            Main.onExit();
        });

        menuBox = new MenuHBox(home, logout, exit);
        menuBox.setTranslateX((displayWidth - 3 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

}
