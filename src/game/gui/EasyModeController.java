package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.sun.javafx.scene.input.DragboardHelper;

import game.engine.Battle;
import game.engine.titans.*;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EasyModeController {
	Stage stage;
	Scene scene;
	Parent root;

	Button placeHolderGP = new Button();
	Button placeHolderGP1 = new Button();
	Button placeHolderGP2 = new Button();
	Battle battle;
	Image buttonImage = new Image("/game/gui/tools/WeaponButtonImage.png");

	// Create a BackgroundImage
	BackgroundImage backgroundImage = new BackgroundImage(buttonImage
			,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER,
			BackgroundSize.DEFAULT); 

	@FXML AnchorPane anchor;
	@FXML Label resourcesLabel;
	@FXML GridPane battleGp;
	@FXML Rectangle lan1Rect;
	@FXML Rectangle lan2Rect;
	@FXML Rectangle lan3Rect;
	@FXML ProgressBar lane1Health;
	@FXML ProgressBar lane2Health;
	@FXML ProgressBar lane3Health;
	@FXML ImageView weaopn1ButtonImage;
	@FXML ImageView weaopn2ButtonImage;
	@FXML ImageView weaopn3ButtonImage;
	@FXML ImageView weaopn4ButtonImage;
	@FXML Button PiercingButton;
	@FXML Button sniperButton;
	@FXML Button volleySpreadButton;
	@FXML Button wallTrapButton;
	@FXML Label lane1Label;
	@FXML Label lane2Label;
	@FXML Label lane3Label;
	@FXML Label lane4Label;
	@FXML Label lane5Label;
	ArrayList<Label> laneLabelArray=new  ArrayList<Label>();
	@FXML Label roundLabel;
	@FXML Button skipTurnButton;
	@FXML Rectangle infoBackground;




	public void initialize(){
		//TODO change to transfered data between scenes
		try {
			battle=new Battle(0, 0, 100, 3, 250);
		} catch (IOException e) {
			e.printStackTrace();
		}

		laneLabelArray.add(lane1Label);
		laneLabelArray.add(lane2Label);
		laneLabelArray.add(lane3Label);
		if(battle.getOriginalLanes().size()==5){//TODO check when making hard game
			laneLabelArray.add(lane4Label);
			laneLabelArray.add(lane5Label);

		}

		//anchor.setPrefSize(500, 500);
		sniperButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		PiercingButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		volleySpreadButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		wallTrapButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		updateScreen();

		sniperButton.setOnMouseEntered(e->{
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		PiercingButton.setOnMouseEntered(e->{
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		volleySpreadButton.setOnMouseEntered(e->{
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		wallTrapButton.setOnMouseEntered(e->{
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});

		battleGp.toFront();
		battleGp.setGridLinesVisible(true);

		skipTurnButton.setBackground(new Background(new BackgroundImage(
				new Image("/game/gui/tools/WeaponButtonImage.png"),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT)));
		battleGp.getRowConstraints().add(new RowConstraints((battleGp.getHeight()/battle.getOriginalLanes().size())));

		/*
		placeHolderGP.setVisible(false);
		placeHolderGP1.setVisible(false);
		placeHolderGP2.setVisible(false);
		 */
		/*battleGp.add(placeHolderGP, 105, 0);
		battleGp.add(placeHolderGP1, 105, 1);
		battleGp.add(placeHolderGP2, 105, 2);
		 */
	}



	public void updateScreen(){
		resourcesLabel.setText("Resources: "+battle.getResourcesGathered());
		for(int i =0;i<laneLabelArray.size();i++){
			laneLabelArray.get(i).setText("Danger Level: "+battle.getOriginalLanes().get(i).getDangerLevel());
		}	
		roundLabel.setText(battle.getNumberOfTurns()+"");
		lane1Health.setProgress(1.0*battle.getOriginalLanes().get(0).getLaneWall().getCurrentHealth()/battle.getOriginalLanes().get(0).getLaneWall().getBaseHealth());
		lane2Health.setProgress(1.0*battle.getOriginalLanes().get(1).getLaneWall().getCurrentHealth()/battle.getOriginalLanes().get(1).getLaneWall().getBaseHealth());
		lane3Health.setProgress(1.0*battle.getOriginalLanes().get(2).getLaneWall().getCurrentHealth()/battle.getOriginalLanes().get(2).getLaneWall().getBaseHealth());

		if(battle.getOriginalLanes().get(0).isLaneLost())
			lan1Rect.setOpacity(1.0);
		if(battle.getOriginalLanes().get(1).isLaneLost())
			lan2Rect.setOpacity(1.0);		
		if(battle.getOriginalLanes().get(2).isLaneLost())
			lan3Rect.setOpacity(1.0);				
		moveTitans();	
		battleGp.add(placeHolderGP, 105, 0);
		battleGp.add(placeHolderGP1, 105, 1);
		battleGp.add(placeHolderGP2, 105, 2);

		for(int i =0;i<battle.getOriginalLanes().size();i++){
			if(battle.getOriginalLanes().get(i).isLaneLost())
				removeRow(battleGp, i);
		}
	}

	private void removeRow(GridPane gridPane, int rowToRemove) {
		List<Node> nodesToRemove = new ArrayList<>();

		// Find nodes in the specified row
		for (Node node : gridPane.getChildren()) {
			Integer rowIndex = GridPane.getRowIndex(node);
			if (rowIndex != null && rowIndex == rowToRemove) {
				nodesToRemove.add(node);
			}	
			// Remove the identified nodes
			gridPane.getChildren().removeAll(nodesToRemove);
		}
	}
	public void hoverView(){
		//TODO add elements
	}

	public void moveTitans(){
		battleGp.getChildren().clear();
		for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
			Lane l= battle.getOriginalLanes().get(i);
			for(Titan t :l.getTitans()){
				//if(t.getDistance()==100)//just spawned
				{
					Circle c = new Circle(5);
					if(t instanceof PureTitan)
						//TODO battleGp.add(new GuiTitan(t, ), 100, i); for image
						c.setFill(Color.PALEGREEN);
					if(t instanceof ColossalTitan)c.setFill(Color.CHOCOLATE);
					if(t instanceof AbnormalTitan)c.setFill(Color.ANTIQUEWHITE);
					if(t instanceof ArmoredTitan)c.setFill(Color.BLACK);

					battleGp.add(c, t.getDistance(), i);
				}
			}
		}


		//GP debug TODO remove later
		System.out.println("Round: "+battle.getNumberOfTurns());
		for(int i =0;i<3;i++){
			Lane l = battle.getOriginalLanes().get(i);
			if(!l.isLaneLost() && l.getTitans()!=null){
				if(l.getTitans().peek()!=null)
					System.out.println("Lane :"+i+" 	t pos: "+l.getTitans().peek().getDistance() +"	Health: " +l.getLaneWall().getCurrentHealth());

			}
		}
	}


	public void startPurchaseAnimation(){

	}

	public void endPurchaseAnimation(){
		//TODO brighten weapon image back
	}


	public void p1(MouseEvent event)
	{
		Dragboard db = PiercingButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(PiercingCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
	}
	public void p2(MouseEvent event)
	{
		Dragboard db = sniperButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(SniperCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
	}
	public void p3(MouseEvent event)
	{
		Dragboard db = volleySpreadButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(VolleySpreadCannon.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
	}

	public void p4(MouseEvent event)
	{
		Dragboard db = wallTrapButton.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putString(Integer.toString(WallTrap.WEAPON_CODE)); // Convert int to string
		db.setContent(content);
		startPurchaseAnimation();
	}

	public void skipTurn(){
		battle.passTurn();
		updateScreen();
	}

	public void l0(DragEvent event)
	{	
		if(event.getDragboard().hasString()){
			int p = Integer.parseInt(event.getDragboard().getString());
			initiatePurchase(0, p);
			updateScreen();
			System.out.println("Pruchased 0");
		}
		else{
			System.out.println("failed");
		}

	}
	public void l1(DragEvent event)
	{
		if(event.getDragboard().hasString()){
			int p = Integer.parseInt(event.getDragboard().getString());
			initiatePurchase(1, p);
			updateScreen();
			System.out.println("Pruchased 1");
		}
		else{
			System.out.println("failed");
		}
	}
	public void l2(DragEvent event)
	{
		if(event.getDragboard().hasString()){
			event.acceptTransferModes(TransferMode.ANY);
			int p = Integer.parseInt(event.getDragboard().getString());
			initiatePurchase(2, p);
			updateScreen();
			System.out.println("Pruchased 2");
		}
		else{
			System.out.println("failed");
		}

	}
	public void DragOver(DragEvent event){
		if(event.getDragboard().hasString()){
			event.acceptTransferModes(TransferMode.ANY);
		}
		else System.out.println("Over fail");
	}


	private void initiatePurchase(int laneNo,int weaponCode){
		try {
			battle.purchaseWeapon(weaponCode, battle.getOriginalLanes().get(laneNo));
		} catch (InsufficientResourcesException e ) {
			AnimatedTextField.showMessage(anchor,"Insufficient Resources");
		}
		catch(InvalidLaneException e){
			AnimatedTextField.showMessage(anchor, "Invalid Lane");
		}
	}

}
