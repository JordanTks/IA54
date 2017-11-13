package fr.utbm.ia54.UI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller implements Initializable {
	
	@FXML 
	private Pane main_pane;
	
	@FXML
	private Button test;
	
	@FXML
	private Rectangle rect1;
	
	@FXML
	private Rectangle rect2;
	
	@FXML
	private Rectangle rect3;
	

	
	@FXML 
	public void go( ActionEvent e ) {
		
		Down(rect1);
		
		Right(rect2);
		
		Down(rect3);
	}
	
	public static void Down(Rectangle r) {
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.7), r );
		animation.setFromY(r.getY());
        animation.setToY(r.getY()+70);
        animation.play();

        // Actualise la position après annimation
        r.setY(r.getY()+70);
	}
	
	public static void Right(Rectangle r) {
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.7), r );
		animation.setFromX(r.getX());
        animation.setToX(r.getX()+154);
        animation.play();

        // Actualise la position après annimation
        r.setX(r.getX()+154);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
