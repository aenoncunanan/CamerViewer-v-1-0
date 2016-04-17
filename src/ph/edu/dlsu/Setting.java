package ph.edu.dlsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

public class Setting {

    ScreenSize screen = new ScreenSize();
    double displayWidth = screen.getDisplayWidth();
    double displayHeight = screen.getDisplayHeight();

    MenuHBox menuBox;

    final double menuWidth = 220;
    final double menuHeight = 40;

    String currentName = null;
    String currentPass = null;

    CheckBox box = new CheckBox();          //Checkbox for the face detection
    CheckBox box2 = new CheckBox();         //Checkbox for the guest user
    CheckBox box3 = new CheckBox();         //Checkbox for the motion detection

    Boolean boxBool = false;
    Boolean box2Bool = true;
    Boolean box3Bool = false;

    public void checkBoxReader(){
        //For Face Detection
        String faceDetect = "setting/faceDetect.txt";                           //Check the setting for the face detection
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
                if (lineF.equals("1")){
                    boxBool = true;
                } else if (lineF.equals("0")){
                    boxBool = false;
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

        //For Guest User
        String guest = "setting/guest.txt";                                     //Check the setting for the guest user
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
                    box2Bool = true;
                } else if (lineG.equals("0")){
                    box2Bool = false;
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


        //For Motion Detection
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
                    box3Bool = true;
                } else if (lineM.equals("0")){
                    box3Bool = false;
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

        checkBoxReader();
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

        box.setText("Enable Face Detection");
        box.setSelected(boxBool);
        grid.add(box, 0, 7);

        box2.setText("Allow guest users");
        box2.setSelected(box2Bool);
        grid.add(box2, 0, 8);

        box3.setText("Automatically take a photo\nwhen motion is detected");
        box3.setSelected(box3Bool);
        grid.add(box3, 0, 9);

        Text scenetitle = new Text("Setup account: ");                      //Create new text for welcome
        scenetitle.setFont(Font.font("Asimov", FontWeight.NORMAL, 55));
        scenetitle.setFill(Color.web("#009fe0"));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text userName = new Text("Current username: ");                     //Create a new label for the username
        userName.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        userName.setFill(Color.web("#2c3e50"));
        grid.add(userName, 0, 1);                                           //Set it at Column 0, Row 1


        TextField user = new TextField();                                   //Create a new Text field for the user's input
        user.setPromptText("Enter your current username");                  //Set a message in the text field
        grid.add(user, 1, 1);                                               //Set it at Column 1, Row 1


        Text pw = new Text("Current password: ");                           //Create a new label for the password
        pw.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        pw.setFill(Color.web("#2c3e50"));
        grid.add(pw, 0, 2);


        PasswordField pass = new PasswordField();                           //Create a new Text field for the user's input
        pass.setPromptText("Enter your current password");                  //Set a message in the text field
        grid.add(pass, 1, 2);                                               //Set it at Column 1, Row 2


        Text userNameNew = new Text("New username: ");                      //Create a new label for the username
        userNameNew.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        userNameNew.setFill(Color.web("#2c3e50"));
        grid.add(userNameNew, 0, 3);                                        //Set it at Column 0, Row 3


        TextField userNew = new TextField();                                //Create a new Text field for the user's input
        userNew.setPromptText("Enter your new username");                   //Set a message in the text field
        grid.add(userNew, 1, 3);                                            //Set it at Column 1, Row 3


        Text pwNew = new Text("New password: ");                            //Create a new label for the password
        pwNew.setFont(Font.font("Asimov", FontWeight.NORMAL, 15));
        pwNew.setFill(Color.web("#2c3e50"));
        grid.add(pwNew, 0, 4);


        PasswordField passNew = new PasswordField();                        //Create a new Text field for the user's input
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
            CheckBoxWriter();
            Main.onHome();
        });

        logout.setOnMouseClicked(e -> {
            CheckBoxWriter();
            Main.onLogIn();
        });

        exit.setOnMouseClicked(e -> {
            CheckBoxWriter();
            Main.onExit();
        });

        menuBox = new MenuHBox(home, logout, exit);
        menuBox.setTranslateX((displayWidth - 3 * menuWidth)/2.0);
        menuBox.setTranslateY(0);

    }

    public void CheckBoxWriter(){
        //For Face Detection
        if (box.isSelected()){
            File faceDetect = new File("setting/faceDetect.txt");
            BufferedWriter outFace = null;
            try {
                outFace = new BufferedWriter(new FileWriter(faceDetect));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outFace.write("1");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outFace.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else {
            File faceDetect = new File("setting/faceDetect.txt");
            BufferedWriter outFace = null;
            try {
                outFace = new BufferedWriter(new FileWriter(faceDetect));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outFace.write("0");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outFace.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //For Guest User
        if (box2.isSelected()){
            File guest = new File("setting/guest.txt");
            BufferedWriter outGuest = null;
            try {
                outGuest = new BufferedWriter(new FileWriter(guest));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outGuest.write("1");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outGuest.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else {
            File guest = new File("setting/guest.txt");
            BufferedWriter outGuest = null;
            try {
                outGuest = new BufferedWriter(new FileWriter(guest));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outGuest.write("0");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outGuest.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //For Motion Detection
        if (box3.isSelected()){
            File motion = new File("setting/motion.txt");
            BufferedWriter outMotion = null;
            try {
                outMotion = new BufferedWriter(new FileWriter(motion));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outMotion.write("1");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outMotion.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else {
            File motion = new File("setting/motion.txt");
            BufferedWriter outMotion = null;
            try {
                outMotion = new BufferedWriter(new FileWriter(motion));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outMotion.write("0");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                outMotion.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
