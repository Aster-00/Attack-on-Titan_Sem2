package game.gui;

import java.io.IOException;

import game.engine.Battle;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EasyModeController {
	Stage stage;
	Scene scene;
	Parent root;
	
	Battle battle;
	
	@FXML
	Button wallTrapButton;
	@FXML
	Label resourcesLabel;
	@FXML
	GridPane battleGp;
	@FXML
	Rectangle2D lan1Rect;
	@FXML
	Rectangle2D lan2Rect;
	@FXML
	Rectangle2D lan3Rect;
	@FXML
	ProgressBar lane1Health;
	@FXML
	ProgressBar lane2Health;
	@FXML
	ProgressBar lane3Health;
	
	
	public void initialize(){
		try {
			battle=new Battle(0, 0, 100, 3, 250);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void buyWallTrap(){
		//battleGp.set
	}
	
	public void updateScreen(){
		//TODO changeResource label
		//TODO health bars
		//TODO Rectangle black
		//TODO update number of rounds
	}
	
}
