package edu.ohio.ise.ise6900.fxPractice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TextAreaTest extends Main {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			super.start(primaryStage);
			primaryStage.setTitle("TextArea Test");
			BorderPane root = new BorderPane();
			root.setPrefSize(800, 600);
			root.setBackground(new Background (new BackgroundFill(Color.BEIGE, null, null)));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FlowPane pane = new FlowPane();
			pane.setPrefWidth(400);
			root.getChildren().add(pane);
			
			TextArea textArea = new TextArea("Enter something.");
			textArea.setPrefColumnCount(20);
			textArea.setPrefRowCount(4);
			textArea.setFont(Font.font("Verdana", 24));
			pane.getChildren().add(textArea);
			
			CheckBox change = new CheckBox("Change Text");
			pane.getChildren().add(change);
			CheckBox wrap = new CheckBox("Wrap Text");
			pane.getChildren().add(wrap);
			Button append = new Button("Append Text");
			pane.getChildren().add(append);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			EventHandler<ActionEvent> checkHandler = new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					if (change.isSelected()){
						textArea.setText("The quick brown fox jumps over the lazy dog.");
					} 
					//String text = textArea.getText();
					if(wrap.isSelected()){
						textArea.setWrapText(true);
					} else{
						textArea.setWrapText(false);
					}
					//textArea.setText(text);
				}
			};
			
			EventHandler<ActionEvent> appendBtnHandler = new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					textArea.appendText(" The quick brown fox jumps over the lazy dog.");
				}		
			};

			change.setOnAction(checkHandler);
			append.setOnAction(appendBtnHandler);
			wrap.setOnAction(checkHandler);
			change.setOnAction(checkHandler);
			change.setOnAction(checkHandler);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
