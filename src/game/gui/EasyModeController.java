package game.gui;

import java.io.IOException;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.weapons.SniperCannon;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EasyModeController {
	Stage stage;
	Scene scene;
	Parent root;

	Battle battle;
	Image buttonImage = new Image("/game/gui/tools/WeaponButtonImage.png");

	// Create a BackgroundImage
	BackgroundImage backgroundImage = new BackgroundImage(buttonImage
			,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundPosition.CENTER,
	        BackgroundSize.DEFAULT); 

	@FXML
	AnchorPane anchor;
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
	@FXML
	ImageView weaopn1ButtonImage;
	@FXML
	ImageView weaopn2ButtonImage;
	@FXML
	ImageView weaopn3ButtonImage;
	@FXML
	ImageView weaopn4ButtonImage;
	@FXML
	Button PiercingButton;
	@FXML
	Button sniperButton;
	@FXML
	Button volleySpreadButton;
	@FXML
	Button wallTrapButton;
	
	int purchaseCode=0;

	public void initialize(){
		try {
			battle=new Battle(0, 0, 100, 3, 250);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//anchor.setPrefSize(500, 500);
		sniperButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		PiercingButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		volleySpreadButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		wallTrapButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
	
	}

	public void updateScreen(){
		//TODO changeResource label
		//TODO health bars
		//TODO Rectangle black
		//TODO update number of rounds
	}
	public void startPurchaseAnimation(){
		//TODO clench mouse
		/*switch(purchaseCode){
		//case 1:weaopn1ButtonImage.set
			break;
		}*/
	}
	
	public void endPurchaseAnimation(){
		//TODO unclench mouse
		//TODO brighten weapon image back
	}
	

	public void p1(){purchaseCode=1;startPurchaseAnimation();System.out.println(purchaseCode);}
	public void p2(){purchaseCode=2;startPurchaseAnimation();System.out.println(purchaseCode);}
	public void p3(){purchaseCode=3;startPurchaseAnimation();System.out.println(purchaseCode);}
	public void p4(){purchaseCode=4;startPurchaseAnimation();System.out.println(purchaseCode);}
	
	public void l0(){initiatePurchase(0, purchaseCode);System.out.println("lane 0");}
	public void l1(){initiatePurchase(1, purchaseCode);System.out.println("lane 1");}
	public void l2(){initiatePurchase(2, purchaseCode);System.out.println("lane 2");}
	
	
	private void initiatePurchase(int laneNo,int weaponCode){
		try {
			battle.purchaseWeapon(weaponCode, battle.getOriginalLanes().get(laneNo));
		} catch (InsufficientResourcesException | InvalidLaneException e) {
			//TODO generate flying red text
		}
	}

}
