package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by Dipen on 21/04/2017. This class is a will only have static method used for dialog box.
 */
public class DialogBox {
    public static boolean tempReturn = false;


    /**
     * this method will open a dialog box given the title and the massage.
     *
     * @param title
     * @param message
     */
    public static void displayMsg(String title, String message) {
        Stage window = new Stage();
        // this makes it so you can't click on the window other then this one
        window.initModality(Modality.APPLICATION_MODAL);
        // sets the title
        window.setTitle(title);
        Image iconImage = new Image(GUI.class.getResourceAsStream("/img/KPS.png"));
        window.getIcons().add(iconImage);
        // sets the weight and height
        window.setMinHeight(200);
        window.setMinWidth(300);
        Label label = new Label();
        // set the massage
        label.setText(message);
        label.setFont(new Font("System", 16));
        // creates the button
        Button ok = new Button("Ok");
        ok.setPrefWidth(90);
        ok.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    /**
     * This method will display a dialog box for logging out, and if the user selects Logout it will log them out else
     * it will close the dialog box.
     *
     * @param title
     * @param message
     * @param events
     */
    public static void LogoutMsg(String title, String message, ActionEvent events) {
        Stage window = new Stage();
        tempReturn = false;
        // this makes it so you can't click on the window other then this one
        window.initModality(Modality.APPLICATION_MODAL);
        // sets the title
        window.setTitle(title);
        Image iconImage = new Image(GUI.class.getResourceAsStream("/img/KPS.png"));
        window.getIcons().add(iconImage);
        // sets the weight and height
        window.setMinHeight(200);
        window.setMinWidth(300);
        Label label = new Label();
        // set the massage
        label.setText(message);
        // creates the button
        Button ok = new Button("Log Out");
        Button close = new Button("Close");
        //used to log the user out of the kps system
        ok.setOnAction(event -> {
            try {
                Parent loginScreen = FXMLLoader.load(DialogBox.class.getResource("/fxml/LoginScreen.fxml"));
                Scene loginScene = new Scene(loginScreen);
                Stage tempStage = (Stage) ((Node) events.getSource()).getScene().getWindow();
                tempStage.setScene(loginScene);
                tempStage.show();
            } catch (Exception e) {

            }

            window.close();
        });
        close.setOnAction(event -> {
            tempReturn = false;
            window.close();
        });


        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(ok, close);
        hBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, hBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }


}
