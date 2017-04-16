package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.ohio.ise.ise6900.MfgSystem.gui.draw.DrawPanel;
import edu.ohio.ise.ise6900.MfgSystem.io.FileIO;
import edu.ohio.ise.ise6900.MfgSystem.model.Activity;
import edu.ohio.ise.ise6900.MfgSystem.model.Job;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgFeature;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgSystem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MfgSystemController implements Initializable  {
	public MfgSystem ms;
	private static Stage stage;
	private static String title;
	@FXML
	private AnchorPane root;
	private FileChooser fileChooser = new FileChooser();
	@FXML
	private ListView<String> machineList;
	ObservableList<String> machineNames;
	@FXML
	private TreeView<String> jobTree;
	@FXML
	private AnchorPane ganttChart;

	public MfgSystemController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void handleOpenFile(ActionEvent event){
		File inFile = fileChooser.showOpenDialog(new Stage());
		String sysName = "";
        if (inFile != null && inFile.getName().endsWith(".mfg")) {
        	sysName = inFile.getName().substring(0, inFile.getName().length()-4);
            ms = new MfgSystem(sysName);
            try {
				MfgSystem.setIO(new FileIO(inFile));
	    		ms.read();
			} catch (FileNotFoundException e) {
				System.out.flush();
				System.err.println("\nFileNotFoundException:");
				System.err.println(e.getMessage());
			}
        }else{
			System.err.println("Input file name: "
					+ inFile
					+ "\nPlease only enter file names with extension '.mfg'!");
			return;
		}
        
        stage.setTitle(MfgSystemController.title + " - " + sysName);
        
        machineNames = FXCollections.observableArrayList();
        for(String mName : ms.getMachines().keySet()){
        	machineNames.add(mName);
        }
        machineList.setItems(machineNames);
        
    	TreeItem<String> rootItem = new TreeItem<>("Jobs");
        //rootItem.getChildren().clear();
        //rootItem.setValue("Jobs");
        for(Job job : ms.getJobs().values()){
        	TreeItem<String> jobItem = new TreeItem<>(job.getName());
        	for(MfgFeature feature : job.getFeatures().values()){
        		TreeItem<String> featureItem = new TreeItem<>(feature.getName());
        		for(Activity act : feature.getActivities()){
            		TreeItem<String> activityItem = new TreeItem<>(act.getName());
            		featureItem.getChildren().add(activityItem);
            	}
        		jobItem.getChildren().add(featureItem);
        	}
        	rootItem.getChildren().add(jobItem);
        }
        jobTree.setRoot(rootItem);
        
        ganttChart.getChildren().addAll(ms.makeShapes());
	}
	
	@FXML
	private void handleCloseFile(ActionEvent event){
		stage.setTitle(MfgSystemController.title);
        machineList.setItems(null);
        jobTree.setRoot(null);
        ganttChart.getChildren().clear();
	}
	
	@FXML
	private void handleExit(ActionEvent event){
		System.exit(0);
		Platform.exit();
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		MfgSystemController.stage = stage;
		MfgSystemController.title = stage.getTitle();
	}

}
