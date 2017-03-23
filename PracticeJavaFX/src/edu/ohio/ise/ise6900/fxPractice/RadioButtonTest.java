package edu.ohio.ise.ise6900.fxPractice;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RadioButtonTest extends Main {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			super.start(primaryStage);
			primaryStage.setTitle("RadioButton Test");
			BorderPane root = new BorderPane();
			root.setPrefSize(800, 600);
			root.setBackground(new Background (new BackgroundFill(Color.BEIGE, null, null)));
			Scene scene = new Scene(root, 800, 200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			FlowPane pane = new FlowPane();
			pane.setPrefWidth(800);
			root.getChildren().add(pane);
			
			Label label = new Label("The quick brown fox jumps over the lazy dog.");
			label.setFont(Font.font("Serif", 24));
			label.setPrefHeight(50);
			String groupText = label.getText();
			
			HBox box = new HBox(2);
			final ToggleGroup group = new ToggleGroup();
			RadioButton small = new RadioButton("Small");
			small.setUserData("Small");
			small.setToggleGroup(group);
			RadioButton medium = new RadioButton("Medium");
			medium.setUserData("Medium");
			medium.setToggleGroup(group);
			RadioButton large = new RadioButton("Large");
			large.setUserData("Large");
			large.setToggleGroup(group);
			RadioButton extraLarge = new RadioButton("Extra Large");
			extraLarge.setUserData("Extra Large");
			extraLarge.setToggleGroup(group);
			box.getChildren().addAll(small, medium, large, extraLarge);
			
			HBox box2 = new HBox(2);
			box2.setPadding(new Insets(10));
			RadioButton free1 = new RadioButton("Free 1");
			RadioButton free2 = new RadioButton("Free 2");
			box2.getChildren().addAll(free1, free2);
			Border border = new Border(new BorderStroke(Color.BROWN,  BorderStrokeStyle.SOLID,
									CornerRadii.EMPTY, BorderWidths.DEFAULT));
						
			pane.getChildren().addAll(label, box, box2);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
				@Override
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					double size = 14;
					if (small.isSelected()) size = 12;
					if (medium.isSelected()) size = 16;
					if (large.isSelected()) size = 22;
					if (extraLarge.isSelected()) size = 30;
					label.setFont(Font.font("Serif", size));
					label.setText(group.getSelectedToggle().getUserData().toString() + ": " + groupText);
				}		
			});
						
			EventHandler<ActionEvent> freeHandler = new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
				   if(free1.isSelected()){
					   free1.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 14));
				   } else{
					   free1.setFont(Font.font("Serif", FontWeight.NORMAL, 14));
				   }
				   if(free2.isSelected()){
					   free2.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 14));
				   } else{
					   free2.setFont(Font.font("Serif", FontWeight.NORMAL, 14));
				   }
				   box2.setBorder(border);
				}
			};
			free1.setOnAction(freeHandler);
			free2.setOnAction(freeHandler);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
