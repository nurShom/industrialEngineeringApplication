package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.ohio.ise.ise6900.MfgSystem.gui.draw.DrawPanel;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.Drawable;
import edu.ohio.ise.ise6900.MfgSystem.io.FileIO;
import edu.ohio.ise.ise6900.MfgSystem.model.Activity;
import edu.ohio.ise.ise6900.MfgSystem.model.Job;
import edu.ohio.ise.ise6900.MfgSystem.model.Machine;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgFeature;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgSystem;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MfgSystemController implements Initializable  {
	public MfgSystem ms;
	private static Stage stage;
	private static String title;
	@FXML
	private AnchorPane root;
	@FXML
	private Label machineListLabel;
	@FXML
	private ListView<String> machineList;
	ObservableList<String> machineNames;
	@FXML
	private TreeView<String> jobTree;
	@FXML
	private Group ganttChart;

	public MfgSystemController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void handleOpenFile(ActionEvent event){
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Mfg File");
		File currentDir = new File(".");
		if(currentDir.exists()){
			fc.setInitialDirectory(currentDir);
		} else{
			fc.setInitialDirectory(new File("."));
		}
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Mfg File", "*.mfg"),
				new ExtensionFilter("All Files", "*.*"));
		File inFile = fc.showOpenDialog(new Stage());
		if (inFile != null) {
			System.out.println("Selected file: " + inFile);
			String sysName = inFile.getName().substring(0, inFile.getName().length()-4);
            ms = new MfgSystem(sysName);
            try {
				MfgSystem.setIO(new FileIO(inFile));
	    		ms.read();
	    		stage.setTitle(MfgSystemController.title + " - " + sysName);
	            this.updateMachineList();
	        	this.updateJobTree();
	        	this.updateGanttChart(ms);
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				this.alertError(e.getMessage());
			}
        }
	}
	
	private void updateMachineList(){
		machineListLabel.setText("Machines");
		machineListLabel.setTextAlignment(TextAlignment.CENTER);
		machineListLabel.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DOTTED, null, null)));
        machineNames = FXCollections.observableArrayList();
        for(String mName : ms.getMachines().keySet()){
        	machineNames.add(mName);
        }
        machineList.setItems(machineNames);
//        machineList.getItems().addListener<>({
//        	
//        });
	}
	private void updateJobTree(){
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
	}
	
	@FXML
	private void updateGanttChart(Drawable d){
		ganttChart.getChildren().clear();
		ganttChart.getChildren().addAll(d.makeShapes());
	}
	
	@FXML
	private void handleCloseFile(ActionEvent event){
        ms = new MfgSystem(null);
		stage.setTitle(MfgSystemController.title);
        this.updateMachineList();
    	this.updateJobTree();
        ganttChart.getChildren().clear();
	}
	
	@FXML
	private void handleExit(ActionEvent event){
		System.exit(0);
		Platform.exit();
	}
	
	@FXML
	private void handleAddMachine(ActionEvent event){
		TextInputDialog dia = new TextInputDialog();
		dia.setTitle("Add Machine");
		dia.setHeaderText("Add a new machine.");
		dia.setContentText("Please enter machine name:");
		dia.showAndWait().ifPresent(mName -> {
			try {
				Machine m = new Machine(mName);
				ms.addMachine(m);
		    	this.updateGanttChart(m);
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
		this.updateMachineList();
	}
	
	private void alertError(String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		MfgSystemController.stage = stage;
		MfgSystemController.title = stage.getTitle();
	}

}
