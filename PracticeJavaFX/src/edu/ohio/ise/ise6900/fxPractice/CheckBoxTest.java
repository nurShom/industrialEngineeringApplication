package edu.ohio.ise.ise6900.fxPractice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CheckBoxTest extends Main {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			super.start(primaryStage);
			primaryStage.setTitle("CheckBox Test");
			BorderPane root = new BorderPane();
			root.setPrefSize(800, 600);
			root.setBackground(new Background (new BackgroundFill(Color.BEIGE, null, null)));
			Scene scene = new Scene(root, 500, 100);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			FlowPane pane = new FlowPane();
			pane.setPrefWidth(400);
			root.getChildren().add(pane);
			
			Label label = new Label("The quick brown fox jumps over the lazy dog.");
			label.setFont(Font.font("Serif", 24));
			pane.getChildren().add(label);
			CheckBox bold = new CheckBox("Bold");
			bold.setMaxWidth(200);
			pane.getChildren().add(bold);
			CheckBox italic = new CheckBox("Italic");
			pane.getChildren().add(italic);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
				   FontWeight fw = FontWeight.NORMAL;
				   FontPosture fp = FontPosture.REGULAR;
				   if (bold.isSelected())  fw = FontWeight.BOLD;
				   if (italic.isSelected()) fp = FontPosture.ITALIC; 
				   label.setFont(Font.font("Serif", fw, fp, 24));
				}		
			};
			bold.setOnAction(handler);
			italic.setOnAction(handler);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
