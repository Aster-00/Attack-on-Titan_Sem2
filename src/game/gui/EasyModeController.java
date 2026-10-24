package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.engine.Battle;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
import javafx.scene.layout.ColumnConstraints;
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
	@FXML Tooltip piercingCannonButtonToolTip;
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
	@FXML ImageView battleField;
	@FXML ImageView hudBoarder2;
	@FXML ImageView hudBoarder3;
	FXMLLoader menuSceneLoader = new FXMLLoader(getClass().getResource("/game/gui/Menu.fxml"));

	// click-to-place: the weapon picked from the HUD that is waiting for a lane click (-1 = none)
	int selectedWeapon = -1;
	Button selectedButton;
	Rectangle[] laneRects;
	Label placementHint = new Label();
	static final Color LOST_LANE_FILL = Color.web("#8d0505");
	static final Color TARGET_LANE_FILL = Color.web("#f2c14e");
	static final String SELECTED_BUTTON_STYLE = "-fx-border-color: #f2c14e; -fx-border-width: 2; -fx-border-radius: 4;";




	public void initialize(){
		try {
			root = menuSceneLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		MenuController menuController = menuSceneLoader.getController();

		// make the background and the top HUD bar follow the window size
		battleField.fitWidthProperty().bind(anchor.widthProperty());
		battleField.fitHeightProperty().bind(anchor.heightProperty().add(24));
		infoBackground.widthProperty().bind(anchor.widthProperty().add(20));
		hudBoarder2.setTranslateX(0);
		// pin the right-hand frames with anchors; binding layoutX breaks AnchorPane's layout pass
		AnchorPane.setRightAnchor(hudBoarder2, -6.0);
		AnchorPane.setRightAnchor(hudBoarder3, -1.0);


		
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
			menuController.playButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		PiercingButton.setOnMouseEntered(e->{
			menuController.playButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		volleySpreadButton.setOnMouseEntered(e->{
			menuController.playButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		wallTrapButton.setOnMouseEntered(e->{
			menuController.playButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});

		skipTurnButton.setOnMouseEntered(e->{
			menuController.playButtonHover();
		});
		//mouse exit
		sniperButton.setOnMouseExited(e->{
			menuController.stopButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		PiercingButton.setOnMouseExited(e->{
			menuController.stopButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		volleySpreadButton.setOnMouseExited(e->{
			menuController.stopButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		wallTrapButton.setOnMouseExited(e->{
			menuController.stopButtonHover();
			//TODO wait 3 secs
			//TODO pop up of weapon info
		});
		skipTurnButton.setOnMouseExited(e->{
			menuController.stopButtonHover();
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

		// one fixed-width column per distance unit (0..105) so empty columns don't collapse
		battleGp.getColumnConstraints().clear();
		for(int col = 0; col <= 105; col++){
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100.0/106);
			battleGp.getColumnConstraints().add(cc);
		}

		setUpClickToPlace();

		//TODO fix titan movement
		/*
		placeHolderGP.setVisible(false);
		placeHolderGP1.setVisible(false);
		placeHolderGP2.setVisible(false);
		 */
		/*battleGp.add(placeHolderGP, 105, 0);
		battleGp.add(placeHolderGP1, 105, 1);
		battleGp.add(placeHolderGP2, 105, 2);
		 */
		
		//TODO Fix Menu Music stop and play other music
		//menuController.stopMenuMusic();
		
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
	public void hoverOnTitianView(){
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
		clearWeaponSelection();
		battle.passTurn();
		updateScreen();
	}

	private void setUpClickToPlace(){
		laneRects = new Rectangle[]{lan1Rect, lan2Rect, lan3Rect};
		for (int i = 0; i < laneRects.length; i++) {
			int lane = i;
			Rectangle r = laneRects[i];
			r.widthProperty().bind(anchor.widthProperty().add(20));
			r.setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY && selectedWeapon != -1)
					placeSelectedWeapon(lane);
			});
			r.setOnMouseEntered(e -> refreshLaneHighlights());
			r.setOnMouseExited(e -> refreshLaneHighlights());
		}

		PiercingButton.setOnAction(e -> selectWeapon(PiercingCannon.WEAPON_CODE, PiercingButton));
		sniperButton.setOnAction(e -> selectWeapon(SniperCannon.WEAPON_CODE, sniperButton));
		volleySpreadButton.setOnAction(e -> selectWeapon(VolleySpreadCannon.WEAPON_CODE, volleySpreadButton));
		wallTrapButton.setOnAction(e -> selectWeapon(WallTrap.WEAPON_CODE, wallTrapButton));

		// right-click or Esc cancels the selection
		anchor.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.SECONDARY) clearWeaponSelection();
		});
		anchor.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ESCAPE) clearWeaponSelection();
		});

		placementHint.setStyle("-fx-background-color: rgba(0,0,0,0.75); -fx-text-fill: white; -fx-font-size: 20px;"
				+ " -fx-padding: 8 18; -fx-background-radius: 6; -fx-border-color: #f2c14e; -fx-border-radius: 6;");
		placementHint.setLayoutY(200);
		// translateX, not layoutX: AnchorPane owns layoutX and binding it breaks the layout pass
		placementHint.translateXProperty().bind(anchor.widthProperty().subtract(placementHint.widthProperty()).divide(2));
		placementHint.setMouseTransparent(true);
		placementHint.setVisible(false);
		anchor.getChildren().add(placementHint);
	}

	private void selectWeapon(int weaponCode, Button button){
		if (selectedButton == button) {
			clearWeaponSelection();
			return;
		}
		if (selectedButton != null) selectedButton.setStyle("");
		selectedWeapon = weaponCode;
		selectedButton = button;
		button.setStyle(SELECTED_BUTTON_STYLE);
		placementHint.setText("Click a lane to place the " + button.getText() + "   ·   Right-click or Esc to cancel");
		placementHint.setVisible(true);
		placementHint.toFront();
		refreshLaneHighlights();
	}

	private void clearWeaponSelection(){
		if (selectedButton != null) selectedButton.setStyle("");
		selectedWeapon = -1;
		selectedButton = null;
		placementHint.setVisible(false);
		refreshLaneHighlights();
	}

	private void placeSelectedWeapon(int laneNo){
		int weaponCode = selectedWeapon;
		clearWeaponSelection();
		initiatePurchase(laneNo, weaponCode);
		updateScreen();
	}

	private void refreshLaneHighlights(){
		if (laneRects == null) return;
		for (int i = 0; i < laneRects.length; i++) {
			Rectangle r = laneRects[i];
			if (battle.getOriginalLanes().get(i).isLaneLost()) {
				r.setFill(LOST_LANE_FILL);
				r.setStroke(Color.BLACK);
				r.setStrokeWidth(1);
				r.setOpacity(1.0);
			} else if (selectedWeapon != -1) {
				r.setFill(TARGET_LANE_FILL.deriveColor(0, 1, 1, r.isHover() ? 0.45 : 0.18));
				r.setStroke(TARGET_LANE_FILL);
				r.setStrokeWidth(r.isHover() ? 4 : 2);
				r.setOpacity(1.0);
			} else {
				r.setOpacity(0.0);
			}
		}
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
