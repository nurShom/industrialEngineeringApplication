package edu.ohio.ise.ise6900.MfgSystem.gui;

import edu.ohio.ise.ise6900.MfgSystem.gui.draw.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class DrawApplication extends Application {
	public static DrawPanel canvas;
	static {
		canvas = new DrawPanel();
	}
	
	public static DrawPanel getCanvas() {
		return canvas;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 1080, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			root.getChildren().add(canvas);
			canvas.makeShapes();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}


	public void show() {
		
	}
}
