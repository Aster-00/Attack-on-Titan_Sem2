package game.gui;

import java.io.IOException;

import com.sun.javafx.scene.input.DragboardHelper;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
	@FXML Label lane1Label;
	@FXML Label lane2Label;
	@FXML Label lane3Label;
	
	

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
		updateScreen();
	}

	public void updateScreen(){
		resourcesLabel.setText("Resources: "+battle.getResourcesGathered());
		lane1Label.setText("Danger Level: "+battle.getOriginalLanes().get(0).getDangerLevel());
		lane2Label.setText("Danger Level: "+battle.getOriginalLanes().get(1).getDangerLevel());
		lane3Label.setText("Danger Level: "+battle.getOriginalLanes().get(2).getDangerLevel());
		
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
	

	public void p1(MouseEvent event)
	{
		Dragboard db = PiercingButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(PiercingCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
		System.out.println(content.getString());
	}
	public void p2(MouseEvent event)
	{
		Dragboard db = sniperButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(SniperCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		System.out.println(content.getString());
			
	}
	public void p3(MouseEvent event)
	{
		Dragboard db = volleySpreadButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(VolleySpreadCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
		System.out.println(content.getString());
	}
	
	public void p4(MouseEvent event)
	{
		Dragboard db = wallTrapButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(WallTrap.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
		System.out.println(content.getString());
	}
	
	public void l0(DragEvent event)
	{	
		if(event.getDragboard().hasString()){
			int p = Integer.parseInt(event.getDragboard().getString());
			System.out.println(event.getDragboard().getString());
			initiatePurchase(0, p);
			System.out.println("lane 0");
			updateScreen();
		}
		else{
			System.out.println("failed");
		}
		
	}
	public void l1(DragEvent event)
	{
		if(event.getDragboard().hasString()){
			int p = Integer.parseInt(event.getDragboard().getString());
			System.out.println(event.getDragboard().getString());
			initiatePurchase(0, p);
			System.out.println("lane 1");
			updateScreen();
		}
		else{
			System.out.println("failed");
		}
			}
	public void l2(DragEvent event)
	{
		if(event.getDragboard().hasString()){
			int p = Integer.parseInt(event.getDragboard().getString());
			System.out.println(event.getDragboard().getString());
			initiatePurchase(0, p);
			System.out.println("lane 0");
			updateScreen();
		}
		else{
			System.out.println("failed");
		}
		
	}
	
	
	private void initiatePurchase(int laneNo,int weaponCode){
		try {
			battle.purchaseWeapon(weaponCode, battle.getOriginalLanes().get(laneNo));
		} catch (InsufficientResourcesException | InvalidLaneException e) {
			//TODO generate flying red text
		}
	}

}
