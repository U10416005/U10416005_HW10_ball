//U10416005ªL«Ø¦t
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.security.SecureRandom;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;


public class BounceBallControl extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		BallPane ballPane = new BallPane(); // Create a ball pane
		//button for change color
        Button btChange = new Button("change color");
		//button for speed change
		Button btUp = new Button("speed+");
		Button btDown = new Button("speed-");
		//two hboxes for putting button
		HBox hBox = new HBox(10);
		HBox hBox2 = new HBox(10);
		//add button to hbox
        hBox.getChildren().addAll(btChange);
        hBox.setAlignment(Pos.CENTER);
		hBox2.getChildren().addAll(btUp,btDown);
		hBox2.setAlignment(Pos.CENTER);
		//button listeners
		btChange.setOnAction(e -> ballPane.changeBall());
		btUp.setOnAction(e->ballPane.increaseSpeed());
		btDown.setOnAction(e->ballPane.decreaseSpeed());
		// Pause and resume animation
		ballPane.setOnMousePressed(e -> ballPane.pause());
		ballPane.setOnMouseReleased(e -> ballPane.play());

		// Increase and decrease animation   
		ballPane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.UP) {
				ballPane.increaseSpeed();
			} 
			else if (e.getCode() == KeyCode.DOWN) {
				ballPane.decreaseSpeed();
			}
		});
		//add hbox to pane
		BorderPane pane = new BorderPane();
		pane.setCenter(ballPane);
		pane.setTop(hBox2);
		pane.setBottom(hBox);

    // Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 150);
		primaryStage.setTitle("BounceBallControl"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
    
		// Must request focus after the primary stage is displayed
		ballPane.requestFocus();
	}

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
	public static void main(String[] args) {
    launch(args);
	}
}
class BallPane extends Pane {
	public final double radius = 20;
	private double x = radius, y = radius;
	private double dx = 1, dy = 1;
	private Circle circle = new Circle(x, y, radius);
	private Timeline animation;
	SecureRandom sRandom = new SecureRandom();
	

	public BallPane() {
		Color color = new Color(sRandom.nextDouble(),sRandom.nextDouble(), sRandom.nextDouble(), 1.0);
        circle.setFill(color); // Set ball color
		getChildren().add(circle); // Place a ball into this pane
		// Create an animation for moving the ball
		animation = new Timeline(
		new KeyFrame(Duration.millis(50), e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play(); // Start animation
	}

	public void play() {
		animation.play();
	}

	public void pause() {
		animation.pause();
	}
	public void changeBall(){
		Color color = new Color(sRandom.nextDouble(),sRandom.nextDouble(), sRandom.nextDouble(), 1.0);
        circle.setFill(color); // Set ball color
	}
	public void increaseSpeed() {
		animation.setRate(animation.getRate() + 0.1);
	}

	public void decreaseSpeed() {
		animation.setRate(
		animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
	}

	public DoubleProperty rateProperty() {
		return animation.rateProperty();
	}
  
	protected void moveBall() {
		// Check boundaries
		if (x < radius || x > getWidth() - radius) {
		dx *= -1; // Change ball move direction
		}
		if (y < radius || y > getHeight() - radius) {
		dy *= -1; // Change ball move direction
		}

		// Adjust ball position
		x += dx;
		y += dy;
		circle.setCenterX(x);
		circle.setCenterY(y);
	}
}