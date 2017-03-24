package edu.ohio.ise.ise6900.fxPractice;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ComboBoxTest extends Main {

	@Override
	public void start(Stage primaryStage) {
		try {
			super.start(primaryStage);
			primaryStage.setTitle("CheckBox Test");
			BorderPane root = new BorderPane();
			root.setPrefSize(800, 600);
			root.setBackground(new Background (new BackgroundFill(Color.BEIGE, null, null)));
			Scene scene = new Scene(root, 700, 100);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			FlowPane pane = new FlowPane();
			pane.setPrefWidth(400);
			root.getChildren().add(pane);
			
			Label label = new Label("The quick brown fox jumps over the lazy dog.");
			label.setFont(Font.font("Serif", 24));

			ComboBox<String> faceCombo = new ComboBox<String>();
			faceCombo.getItems().add("Serif");
			faceCombo.getItems().add("SansSerif");
			faceCombo.getItems().addAll("Monospaced", "Dialog", "DialogInput");
			faceCombo.setValue("Dialog");
			faceCombo.setPromptText("Select Font");
			faceCombo.setEditable(true);
			
			faceCombo.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> ov, String old_value, String new_value) {
					label.setFont(Font.font(new_value, 24));
				}
			});

			pane.getChildren().addAll(label, faceCombo);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
