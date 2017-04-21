package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.OverlappingStateException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.UnknownObjectException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Pair;

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
		System.out.println("Exiting mfg system application");
		System.exit(0);
		Platform.exit();
	}

	@FXML
	private void handleAddMachine(ActionEvent event) {
		TextInputDialog dia = new TextInputDialog();
		dia.setTitle("Add Machine");
		//dia.setHeaderText("Add a new machine.");
		dia.setHeaderText(null);
		dia.setContentText("Please enter machine name:");
		dia.showAndWait().ifPresent(mName -> {
			if(ms == null)
				return;
			try {
				Machine m = new Machine(mName);
				ms.addMachine(m);
				this.updateGanttChart(m);
				this.updateMachineList();
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
	}
	
	/**
	 * @author http://code.makery.ch/blog/javafx-dialogs-official/
	 * @param event
	 */
	@FXML
	private void handleAddJob(ActionEvent event) {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add Job");
		dialog.setHeaderText(null);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField job = new TextField();
		job.setPromptText("Job name");
		TextField batchSize = new TextField();
		batchSize.setPromptText("Batch size");

		grid.add(new Label("Job name:"), 0, 0);
		grid.add(job, 1, 0);
		grid.add(new Label("Batch size:"), 0, 1);
		grid.add(batchSize, 1, 1);
		
		//ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);
		//boolean jobNotAdded = true, batchNotAdded = true;
		job.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean batchNotAdded = batchSize.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || batchNotAdded);
		});
		batchSize.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean jobNotAdded = job.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || jobNotAdded);
		});
		
		dialog.getDialogPane().setContent(grid);
		
		// Request focus on the job field by default.
		Platform.runLater(() -> job.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return new Pair<>(job.getText(), batchSize.getText());
		    }
		    return null;
		});
		
		dialog.showAndWait().ifPresent(jInput -> {
			if(ms == null)
				return;
			try {
				String jName = jInput.getKey();
				int bSize = Integer.parseInt(jInput.getValue());
				Job j = new Job(jName, bSize);
				ms.addJob(j);
				this.updateGanttChart(j);
				this.updateJobTree();
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
	}
	
	@FXML
	private void handleAddActivity(ActionEvent event) {
		Dialog<HashMap<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add Activity");
		dialog.setHeaderText(null);
		
		TextField machine = new TextField();
		machine.setPromptText("Machine name");
		TextField job = new TextField();
		job.setPromptText("Job name");
		TextField feature = new TextField();
		feature.setPromptText("Feature name");
		TextField stTime = new TextField();
		stTime.setPromptText("Start time");
		TextField enTime = new TextField();
		enTime.setPromptText("End time");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(20, 150, 10, 10));

		grid.add(new Label("Machine name:"), 0, 0);
		grid.add(machine, 1, 0);
		grid.add(new Label("Job name:"), 0, 1);
		grid.add(job, 1, 1);
		grid.add(new Label("Feature name:"), 0, 2);
		grid.add(feature, 1, 2);
		grid.add(new Label("Starts at:"), 0, 3);
		grid.add(stTime, 1, 3);
		grid.add(new Label("Ends at:"), 0, 4);
		grid.add(enTime, 1, 4);
		
		//ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		ButtonType okButtonType = ButtonType.OK;
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
		okButton.setDisable(true);
		machine.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean jobNotAdded = job.getText().trim().isEmpty();
			boolean featureNotAdded = feature.getText().trim().isEmpty();
			boolean startNotAdded = stTime.getText().trim().isEmpty();
			boolean endNotAdded = enTime.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || jobNotAdded || featureNotAdded || startNotAdded || endNotAdded);
		});
		job.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean machineNotAdded = machine.getText().trim().isEmpty();
			boolean featureNotAdded = feature.getText().trim().isEmpty();
			boolean startNotAdded = stTime.getText().trim().isEmpty();
			boolean endNotAdded = enTime.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || machineNotAdded || featureNotAdded || startNotAdded || endNotAdded);
		});
		feature.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean machineNotAdded = machine.getText().trim().isEmpty();
			boolean jobNotAdded = job.getText().trim().isEmpty();
			boolean startNotAdded = stTime.getText().trim().isEmpty();
			boolean endNotAdded = enTime.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || machineNotAdded || jobNotAdded || startNotAdded || endNotAdded);
		});
		stTime.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean machineNotAdded = machine.getText().trim().isEmpty();
			boolean jobNotAdded = job.getText().trim().isEmpty();
			boolean featureNotAdded = stTime.getText().trim().isEmpty();
			boolean endNotAdded = enTime.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || machineNotAdded || jobNotAdded || featureNotAdded || endNotAdded);
		});
		enTime.textProperty().addListener((observable, oldValue, newValue) -> {
			boolean machineNotAdded = machine.getText().trim().isEmpty();
			boolean jobNotAdded = job.getText().trim().isEmpty();
			boolean featureNotAdded = enTime.getText().trim().isEmpty();
			boolean startNotAdded = stTime.getText().trim().isEmpty();
		    okButton.setDisable(newValue.trim().isEmpty() || machineNotAdded || jobNotAdded || featureNotAdded || startNotAdded);
		});
		
		dialog.getDialogPane().setContent(grid);
		
		// Request focus on the job field by default.
		Platform.runLater(() -> job.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == okButtonType) {
		    	HashMap<String, String> results = new HashMap<String, String>();
		    	results.put("machine", machine.getText());
		    	results.put("job", job.getText());
		    	results.put("feature", feature.getText());
		    	results.put("start", stTime.getText());
		    	results.put("end", enTime.getText());
		        return results;
		    }
		    return null;
		});
		
		dialog.showAndWait().ifPresent(actInput -> {
			if(ms == null)
				return;
			try {
				String mName = actInput.get("machine");
				Machine m = ms.findMachine(mName);
				String jName = actInput.get("job");
				Job j = ms.findJob(jName);
				String fName = actInput.get("feature");
				MfgFeature f = j.findFeature(fName);
				Date startTime = new Date(Long.parseLong(actInput.get("start")) * 1000);
				Date endTime = new Date(Long.parseLong(actInput.get("end")) * 1000);
				String actName = mName + "-" + jName 
						+ "-" + fName + "-" + (startTime.getTime()/1000);
				Activity act = new Activity(actName, m, j, f, startTime, endTime);
				m.addState(act);
				j.addActivity(act);
				f.addActivity(act);
				this.updateGanttChart(act);
				this.updateJobTree();
			} catch (UnknownObjectException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			} catch (InvalidStateException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			} catch (OverlappingStateException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
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
		ArrayList<Activity> acts = (ArrayList<Activity>) f.getActivities();
		f.getJob().deleteFeature(f.getName());
		for(Activity a : acts){
			a.getJob().deleteActivity(a);
			a.getMachine().deleteState(a);
		}
	}
	
	private void deleteActivity(){
		Activity a = (Activity) selected;
		a.getFeature().deleteActivity(a);
		a.getJob().deleteActivity(a);
		a.getMachine().deleteState(a);
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
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	private void actionNotImplemented(String msg){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("FYI");
		alert.setHeaderText(null);
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
