package sourse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle(Constants.GetNameApp());
        /*primaryStage.setMaxWidth(260);
        primaryStage.setMaxHeight(215);
        primaryStage.setMaxWidth(260);
        primaryStage.setMinHeight(215);*/
        primaryStage.setResizable(false);
        Scene scene = new Scene(root );
        primaryStage.sizeToScene();
        //scene.getStylesheets().add(0,"sample/1111.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
