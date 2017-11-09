package fr.utbm.ia54.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {		
			
			stage= new Stage();
	        Parent root = FXMLLoader.load(getClass().getResource("/fr/utbm/ia54/Taquin.fxml"));
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setMaxHeight(900);
	        stage.setMaxWidth(1080);
	       // stage.setResizable(false);
	        stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
