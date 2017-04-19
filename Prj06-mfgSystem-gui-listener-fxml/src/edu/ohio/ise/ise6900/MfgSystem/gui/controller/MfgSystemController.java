package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import edu.ohio.ise.ise6900.MfgSystem.gui.draw.Drawable;
import edu.ohio.ise.ise6900.MfgSystem.io.FileIO;
import edu.ohio.ise.ise6900.MfgSystem.model.AbstractState;
import edu.ohio.ise.ise6900.MfgSystem.model.Activity;
import edu.ohio.ise.ise6900.MfgSystem.model.Job;
import edu.ohio.ise.ise6900.MfgSystem.model.Machine;
import edu.ohio.ise.ise6900.MfgSystem.model.MachineState;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgFeature;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgObject;
import edu.ohio.ise.ise6900.MfgSystem.model.MfgSystem;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MfgSystemController implements Initializable {
	public MfgSystem ms;
	private static Stage stage;
	private static String title;
	@FXML
	private AnchorPane root;
	@FXML
	private Label machineListLabel;
	@FXML
	private ListView<Machine> machineList;
	@FXML
	private TreeView<MfgObject> jobTree;
	@FXML
	private Group ganttChart;
	private Drawable chartContent;
	private MfgObject selected;

	public MfgSystemController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		machineList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Machine>() {
			@Override
			public void changed(ObservableValue<? extends Machine> observable, Machine oldValue, Machine newValue) {
				if(newValue != null){
					updateGanttChart(newValue);
					selected = newValue;
				}
			}
		});
		
		jobTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MfgObject>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<MfgObject>> observable, TreeItem<MfgObject> oldValue,
					TreeItem<MfgObject> newValue) {
				if(newValue != null){
					updateGanttChart(newValue.getValue());
					selected = newValue.getValue();
				}
			}
		});

	}

	@FXML
	private void handleOpenFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Mfg File");
		File currentDir = new File(".");
		if (currentDir.exists()) {
			fc.setInitialDirectory(currentDir);
		} else {
			fc.setInitialDirectory(new File("."));
		}
		fc.getExtensionFilters().addAll(new ExtensionFilter("Mfg File", "*.mfg"),
				new ExtensionFilter("All Files", "*.*"));
		File inFile = fc.showOpenDialog(new Stage());
		if (inFile != null) {
			System.out.println("Selected file: " + inFile);
			String sysName = inFile.getName().substring(0, inFile.getName().length() - 4);
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

	private void updateMachineList() {
		machineListLabel.setText("Machines");
		machineListLabel.setTextAlignment(TextAlignment.CENTER);
		machineListLabel.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DOTTED, null, null)));
		ObservableList<Machine> machines = FXCollections.observableArrayList();
		for (Machine m : ms.getMachines().values()) {
			machines.add(m);
		}
		machineList.setItems(machines);
	}

	private void updateJobTree() {
		TreeItem<MfgObject> rootItem = new TreeItem<>(ms);
		// rootItem.getChildren().clear();
		// rootItem.setValue("Jobs");
		for (Job job : ms.getJobs().values()) {
			TreeItem<MfgObject> jobItem = new TreeItem<>(job);
			for (MfgFeature feature : job.getFeatures().values()) {
				TreeItem<MfgObject> featureItem = new TreeItem<>(feature);
				for (Activity act : feature.getActivities()) {
					TreeItem<MfgObject> activityItem = new TreeItem<>(act);
					featureItem.getChildren().add(activityItem);
				}
				jobItem.getChildren().add(featureItem);
			}
			rootItem.getChildren().add(jobItem);
		}
		jobTree.setRoot(rootItem);
	}

	private void updateGanttChart(Drawable d) {
		ganttChart.getChildren().clear();
		this.chartContent = d;
		ganttChart.getChildren().addAll(this.chartContent.makeShapes());
		System.out.println("Displaying chart for " + d.toString());
	}

	
	@FXML
	private void handleSaveFile(ActionEvent event) {
		actionNotImplemented("Save File");
	}
	
	@FXML
	private void handleCloseFile(ActionEvent event) {
		ms = new MfgSystem("Empty");
		stage.setTitle(MfgSystemController.title);
		this.updateMachineList();
		this.updateJobTree();
		ganttChart.getChildren().clear();
		if(this.chartContent != null){
			this.chartContent = null;
		}
		System.out.println("Closing mfg system file");
	}

	@FXML
	private void handleExit(ActionEvent event) {
		System.exit(0);
		Platform.exit();
	}

	@FXML
	private void handleAddMachine(ActionEvent event) {
		TextInputDialog dia = new TextInputDialog();
		dia.setTitle("Add Machine");
		dia.setHeaderText("Add a new machine.");
		dia.setContentText("Please enter machine name:");
		dia.showAndWait().ifPresent(mName -> {
			if(ms == null)
				return;
			try {
				Machine m = new Machine(mName);
				ms.addMachine(m);
				this.updateGanttChart(m);
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
			this.updateMachineList();
		});
	}
	
	@FXML
	private void handleAddJob(ActionEvent event) {
		this.actionNotImplemented("Add Job");
	}
	
	@FXML
	private void handleAddActivity(ActionEvent event) {
		this.actionNotImplemented("Add Activity");
	}
	
	@FXML
	private void handleAddState(ActionEvent event) {
		this.actionNotImplemented("Add State");
	}
	
	@FXML
	private void handleDelete(ActionEvent event) {
//		this.actionNotImplemented("Delete");
		if(this.selected instanceof Machine){
			deleteMachine();
		} else if(this.selected instanceof Job){
			deleteJob();
		} else if(this.selected instanceof MfgFeature){
			deleteFeature();
		} else if(this.selected instanceof Activity){
			deleteActivity();
		} else if(this.selected instanceof MachineState){
			deleteState();
		}
		this.updateMachineList();
		this.updateJobTree();
		this.updateGanttChart(ms);
	}
	
	private void deleteMachine(){
		Machine m = (Machine) selected;
		ms.deleteMachine(m.getName());
		for(AbstractState as : m.getMachineStates()){
			if(as instanceof Activity){
				Activity a = (Activity) as;
				a.getFeature().deleteActivity(a);
				a.getJob().deleteActivity(a);
			}
		}
	}
	
	private void deleteJob(){
		Job j = (Job) selected;
		ms.deleteJob(j.getName());
		for(Activity a : j.getActivities()){
			a.getMachine().deleteState(a);
		}
	}
	
	private void deleteFeature(){
		MfgFeature f = (MfgFeature) selected;
		Job j = f.getJob();
		ArrayList<Activity> acts = (ArrayList<Activity>) f.getActivities();
		j.deleteFeature(f.getName());
		for(Activity a : acts){
			j.deleteActivity(a);
			a.getMachine().deleteState(a);
		}
	}
	
	private void deleteActivity(){
		Activity a = (Activity) selected;
		Job j = a.getJob();
		Machine m = a.getMachine();
		MfgFeature f = a.getFeature();
		f.deleteActivity(a);
		j.deleteActivity(a);
		m.deleteState(a);
	}
	
	private void deleteState(){
		MachineState s = (MachineState) selected;
		Machine m = s.getMachine();
		m.deleteState(s);
	}
	
	@FXML
	private void handleZoomIn(ActionEvent event) {
		if(this.chartContent != null){
			MfgObject.setSCALE(MfgObject.getSCALE() * 1.25);
			MfgObject.setHEIGHT(MfgObject.getHEIGHT() * 1.25);
			ganttChart.getChildren().clear();
			ganttChart.getChildren().addAll(this.chartContent.makeShapes());
			System.out.println("Zooming in. SCALE: " + MfgObject.getSCALE());
		}
	}
	
	@FXML
	private void handleZoomOut(ActionEvent event) {
		if(this.chartContent != null){
			MfgObject.setSCALE(MfgObject.getSCALE() * 0.80);
			MfgObject.setHEIGHT(MfgObject.getHEIGHT() * 0.80);
			ganttChart.getChildren().clear();
			ganttChart.getChildren().addAll(this.chartContent.makeShapes());
			System.out.println("Zooming out. SCALE: " + MfgObject.getSCALE());
		}
	}

	private void alertError(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	private void actionNotImplemented(String msg){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("FYI");
		alert.setContentText(msg + " : this option is not available at this moment: ");
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
