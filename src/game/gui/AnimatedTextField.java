package game.gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AnimatedTextField {

    public static void showMessage(Pane root, String message) {
        Label messageLabel = new Label(message);
        messageLabel.setTextFill(Color.RED);
        // Adjust font size and positioning as needed
        messageLabel.setStyle("-fx-font-size: 15px");

        // Calculate initial position
        
        messageLabel.setLayoutY(375);
        messageLabel.setLayoutX(250);

        // Create a TranslateTransition to move the label upwards
        TranslateTransition tt = new TranslateTransition(Duration.seconds(7), messageLabel);
        tt.setToY(-160);

        // Create a FadeTransition to fade out the label
        FadeTransition ft = new FadeTransition(Duration.seconds(5), messageLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        // Play both transitions simultaneously
        tt.play();
        ft.play();

        // Add the label to the root pane
        root.getChildren().add(messageLabel);
    }
}