package game.gui;


import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuController {

	Stage stage;
	Scene scene;
	Parent root;
	@FXML AnchorPane anchor;
	@FXML ImageView menuBackground;
	@FXML Label AOTLabel;
	@FXML Button singlePlayerButton;
	@FXML Button multiPlayerButton;
	@FXML Button onlineButton;
	@FXML Button localButton;
	@FXML Button easyButton;
	@FXML Button hardButton;
	@FXML Button gameInstructionsButton;
	@FXML Button SettingsButton;
	Media media1 = new Media(new File("C:/Users/Ammar/Desktop/Projects Repos/Attack-on-Titan-Sem2/src/game/gui/tools/SelectSoundEffect.mp3").toURI().toString());
	MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
	Media media2 = new Media(new File("C:/Users/Ammar/Desktop/Projects Repos/Attack-on-Titan-Sem2/src/game/gui/tools/menuMusic.mp3").toURI().toString());
	MediaPlayer mediaPlayer2 = new MediaPlayer(media2);

	Cursor cursor = new ImageCursor(new Image("/game/gui/tools/mouse.png"));
	


	//Font font = Font.loadFont(getClass().getResource("C:/Users/Ammar/Desktop/Projects Repos/Attack-on-Titan-Sem2/src/game/gui/tools/Ditty.ttf").toExternalForm(), 150);

	public void initialize(){
		//scene.setUserAgentStylesheet(url); css
		//AOTLabel.setFont(font);
		mediaPlayer1.setVolume(80);

		mediaPlayer2.setVolume(50);
		mediaPlayer2.play();
		
	}
	public void switchToMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setFullScreen(true);
		stage.setResizable(false);
		stage.show();

	}

	public void switchToEasyMode(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("EasyMode.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.setCursor(cursor);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setResizable(true);
		stage.show();
	}
	@FXML
	public void singleButtonsPress(){
		hardButton.setVisible(!hardButton.isVisible());
		easyButton.setVisible(!easyButton.isVisible());
	}
	@FXML
	public void MultiButtonsPress(){
		onlineButton.setVisible(!onlineButton.isVisible());
		localButton.setVisible(!localButton.isVisible());
	}
	public void playButtonHover(){
		mediaPlayer1.play();
	}
	public void stopButtonHover(){
		mediaPlayer1.stop();
	}
	public void stopMenuMusic(){mediaPlayer1.pause();}

}
