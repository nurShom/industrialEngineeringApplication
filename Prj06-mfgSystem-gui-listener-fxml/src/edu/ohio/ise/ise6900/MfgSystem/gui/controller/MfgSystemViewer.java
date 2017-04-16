package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import edu.ohio.ise.ise6900.MfgSystem.model.MfgSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MfgSystemViewer extends Application {
//	private 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = 
				FXMLLoader.load(MfgSystemController.class.getResource("../view/MfgSystemView.fxml"));
			Scene scene = new Scene(root, 1080, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Manufacturing System Dashboard");
			MfgSystemController.setStage(primaryStage);
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
