package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MfgSystemController implements Initializable  {

	public MfgSystem ms;
	private FileChooser fileChooser = new FileChooser();
	@FXML
	private ListView<String> machineList;
	ObservableList<String> machineNames;
	private TreeItem<String> rootItem = new TreeItem<>("System");
	@FXML
	private TreeView<String> jobTree;

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
        if (inFile != null && inFile.getName().endsWith(".mfg")) {
        	String fName = inFile.getName();
            ms = new MfgSystem(fName.substring(0, fName.length()-4));
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
        
        machineNames = FXCollections.observableArrayList();
        for(String mName : ms.getMachines().keySet()){
        	machineNames.add(mName);
        }
        machineList.setItems(machineNames);
        
        rootItem.setValue("Jobs");
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
        
        
	}
	
	@FXML
	private void handleExit(ActionEvent event){
		System.exit(0);
		Platform.exit();
	}

}
