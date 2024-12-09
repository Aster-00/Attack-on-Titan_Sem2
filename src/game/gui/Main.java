package game.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	MenuController menuController = new MenuController();
	Cursor cursor = new ImageCursor(new Image("/game/gui/tools/mouse.png"));
	String css = this.getClass().getResource("menuSheet.css").toExternalForm();
	@Override
	public void start(Stage primaryStage) throws Exception {
		try
		{
			Parent root=FXMLLoader.load(getClass().getResource("Menu.fxml"));
			menuController.scene = new Scene(root);
			setUpScene(menuController.scene);
			primaryStage.setScene(menuController.scene);
			primaryStage.setResizable(true);
			primaryStage.centerOnScreen();
			primaryStage.setFullScreen(true);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Attack on Titan");
			Image icon = new Image("/game/gui/tools/icon.jpg");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public static void main(String []args){
		launch(args);
	}

	public void setUpScene(Scene sc){
		sc.setCursor(cursor);	
	}
}
