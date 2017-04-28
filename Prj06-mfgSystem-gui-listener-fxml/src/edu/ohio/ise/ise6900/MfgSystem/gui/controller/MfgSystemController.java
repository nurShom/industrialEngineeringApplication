package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.Drawable;
import edu.ohio.ise.ise6900.MfgSystem.io.FileIO;
import edu.ohio.ise.ise6900.MfgSystem.model.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.*;
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
import javafx.scene.control.ButtonType;
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

public class MfgSystemController implements Initializable {
	public MfgSystem ms;
	private static Stage stage;
	private static String title;
	private FileChooser fc = new FileChooser();
	private File currentDir = new File(".");
	private boolean fileModified = false;
	@FXML
	private AnchorPane root;
	//@FXML
	//private Label machineListLabel;WSS
	@FXML
	private ListView<Machine> machineList;
	@FXML
	private TreeView<MfgObject> jobTree;
	@FXML
	private Group ganttChart;
	private Drawable chartContent;
	private MfgObject selected;

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
					selected = newValue.getValue();
					updateGanttChart(selected);
				}
			}
		});

	}
	
	@FXML
	private void handleNewFile(ActionEvent event) {
		if(this.fileModified){
			String prompt = "If you continue, any unsaved work will be lost. Do you want to continue with creating a new file?";
			if(!this.promptFileSave(prompt))
				return;
		}
		stage.setTitle(MfgSystemController.title + " - New Mfg System");
		ms = new MfgSystem("New Mfg System");
		this.updateMachineList();
		this.updateJobTree();
		this.updateGanttChart(ms);
		this.chartContent = null;
		this.fileModified = false;
		System.out.println("Creating new mfg system file");
	}

	@FXML
	private void handleOpenFile(ActionEvent event) {
		if(this.fileModified){
			String prompt = "If you continue, any unsaved work will be lost. Do you want to continue with opening another file?";
			if(!this.promptFileSave(prompt))
				return;
		}
		fc.setTitle("Open Mfg File");
		if (currentDir.exists()) {
			fc.setInitialDirectory(currentDir);
		} else {
			fc.setInitialDirectory(new File("."));
		}
		fc.getExtensionFilters().addAll(new ExtensionFilter("Mfg File", "*.mfg"),
				new ExtensionFilter("All Files", "*.*"));
		File inFile = fc.showOpenDialog(new Stage());
		if(inFile == null){//file was not selected
			return;
		}
		currentDir = inFile.getParentFile();
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
				this.fileModified = false;
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				this.alertError(e.getMessage());
			}
		}
	}

	private void updateMachineList() {
		if(ms == null){
			machineList.setItems(null);
			return;
		}
		//machineListLabel.setText("Machines");
		//machineListLabel.setTextAlignment(TextAlignment.CENTER);
		//machineListLabel.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DOTTED, null, null)));
		ObservableList<Machine> machines = FXCollections.observableArrayList();
		for (Machine m : ms.getMachines().values()) {
			machines.add(m);
		}
		machineList.setItems(machines);
	}

	private void updateJobTree() {
		if(ms == null){
			jobTree.setRoot(null);
			return;
		}
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
		if(d==null || ms == null){
			return;
		}
		this.chartContent = d;
		ganttChart.getChildren().addAll(this.chartContent.makeShapes());
		System.out.println("Displaying chart for " + d.toString());
	}

	
	@FXML
	private void handleSaveFile(ActionEvent event) {
		if(ms == null){
			this.fileModified = false;
			return;
		}
		if(!this.fileModified){
			return;
		}

		fc.setTitle("Save Mfg File");
		if (currentDir.exists()) {
			fc.setInitialDirectory(currentDir);
		} else {
			fc.setInitialDirectory(new File("."));
		}
		fc.getExtensionFilters().addAll(new ExtensionFilter("Mfg File", "*.mfg"),
				new ExtensionFilter("All Files", "*.*"));
		File outFile = fc.showSaveDialog(new Stage());
		if(outFile != null){
			try {
				outFile.createNewFile();
				MfgSystem.setIO(new FileIO(outFile, outFile));
				ms.write();
				((FileIO)MfgSystem.getIO()).flush();
				this.fileModified = false;
				System.out.println("Saved mfg system file");
			} catch (IOException e) {
				this.alertError(e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	private boolean continueWoSaving;
	private boolean promptFileSave(String msg){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		continueWoSaving = false;
		alert.showAndWait().ifPresent(result -> {
			if(result == ButtonType.OK){
				//this.handleSaveFile(new ActionEvent());
				continueWoSaving = true;
			}
		});
		return continueWoSaving;
	}
	
	@FXML
	private void handleCloseFile(ActionEvent event) {
		if(ms == null){
			this.fileModified = false;
			return;
		}
		if(this.fileModified){
			String prompt = "If you continue, any unsaved work will be lost. Do you want to continue with closing the file?";
			if(!this.promptFileSave(prompt))
				return;
		}
		stage.setTitle(MfgSystemController.title);
		this.ms = null;
		this.updateMachineList();
		this.updateJobTree();
		this.updateGanttChart(null);
		this.chartContent = null;
		this.fileModified = false;
		System.out.println("Closing mfg system file");
	}

	@FXML
	private void handleExit(ActionEvent event) {
		if(this.fileModified){
			String prompt = "If you continue, any unsaved work will be lost. Do you want to continue with exiting the application?";
			if(!this.promptFileSave(prompt))
				return;
		}
		System.out.println("Exiting mfg system application");
		System.exit(0);
		Platform.exit();
	}
	
	private double findAvailableMachineLevel(){
		double offset = MfgSystem.getOFFSET();
		LinkedList<Double> levels = new LinkedList<Double>(); 
		for(Machine m : ms.getMachines().values()){
			levels.add(m.getLevel());
		}
		double l=offset;
		for(; l< 10000; l+=offset){
			if(!levels.contains(l)){
				return l;
			}
		}
		return l+offset;
	}

	@FXML
	private void handleAddMachine(ActionEvent event) {
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
		TextInputDialog dia = new TextInputDialog();
		dia.setTitle("Add Machine");
		dia.setHeaderText(null);
		dia.setContentText("Machine name:");
		dia.showAndWait().ifPresent(mName -> {
			try {
				Machine m = new Machine(mName);
				m.setLevel(this.findAvailableMachineLevel());
				System.err.println(m.toString() + " level " + m.getLevel());
				ms.addMachine(m);
				this.updateGanttChart(m);
				this.updateMachineList();
				this.updateJobTree();
				this.fileModified = true;
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
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
		HashMap<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("job", "Job name");
		fields.put("batch", "Batch size");
		MultiTextInputDialog dialog = new MultiTextInputDialog(fields);
		dialog.setTitle("Add Activity");
		dialog.setHeaderText(null);
		
		dialog.showAndWait().ifPresent(results -> {
			try {
				String jName = results.get("job");
				int bSize = Integer.parseInt(results.get("batch"));
				Job j = new Job(jName, bSize);
				ms.addJob(j);
				this.updateGanttChart(j);
				this.updateJobTree();
				this.fileModified = true;
			} catch (AlreadyMemberException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
	}
	
	@FXML
	private void handleAddFeature(ActionEvent event) {
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
		HashMap<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("feature", "Feature name");
		fields.put("job", "Job name");
		MultiTextInputDialog dialog = new MultiTextInputDialog(fields);
		dialog.setTitle("Add Feature");
		dialog.setHeaderText(null);
		
		dialog.showAndWait().ifPresent(results -> {
			try {
				String jName = results.get("job");
				Job j = ms.findJob(jName);
				String fName = results.get("feature");
				j.addFeature(new MfgFeature(fName, j));
				this.updateGanttChart(j);
				this.updateJobTree();
				this.fileModified = true;
			} catch (AlreadyMemberException | UnknownObjectException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
	}
	
	@FXML
	private void handleAddActivity(ActionEvent event) {
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
		HashMap<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("machine", "Machine name");
		fields.put("job", "Job name");
		fields.put("feature", "Feature name");
		fields.put("start", "Start time");
		fields.put("end", "End time");
		MultiTextInputDialog dialog = new MultiTextInputDialog(fields);
		dialog.setTitle("Add Activity");
		dialog.setHeaderText(null);
		
		dialog.showAndWait().ifPresent(input -> {
			try {
				String mName = input.get("machine");
				Machine m = ms.findMachine(mName);
				String jName = input.get("job");
				Job j = ms.findJob(jName);
				String fName = input.get("feature");
				MfgFeature f = j.findFeature(fName);
				Date startTime = new Date(Long.parseLong(input.get("start")) * 1000);
				Date endTime = new Date(Long.parseLong(input.get("end")) * 1000);
				String actName = mName + "-" + jName 
						+ "-" + fName + "-" + (startTime.getTime()/1000);
				Activity act = new Activity(actName, m, j, f, startTime, endTime);
				m.addState(act);
				j.addActivity(act);
				f.addActivity(act);
				this.updateGanttChart(act);
				this.updateJobTree();
				this.fileModified = true;
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
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
		HashMap<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("machine", "Machine name");
		fields.put("state", "State type");
		fields.put("start", "Start time");
		fields.put("end", "End time");
		MultiTextInputDialog dialog = new MultiTextInputDialog(fields);
		dialog.setTitle("Add State");
		dialog.setHeaderText(null);
		
		dialog.showAndWait().ifPresent(inputs -> {
			try {
				String mName = inputs.get("machine");
				Machine m = ms.findMachine(mName);
				String sType = inputs.get("state");
				StateType st = StateType.findStateType(sType);
				Date startTime = new Date(Long.parseLong(inputs.get("start")) * 1000);
				Date endTime = new Date(Long.parseLong(inputs.get("end")) * 1000);
				String stateName = mName+"-"+st+"-"+(startTime.getTime()/1000);
				MachineState mSt = new MachineState(stateName, m, st, startTime, endTime);
				m.addState(mSt);
				this.updateGanttChart(mSt);
				this.updateJobTree();
				this.fileModified = true;
				System.out.println("New state added at index:" + m.findState(mSt));
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
			} catch (UnknownStateException e) {
				this.alertError(e.getMessage());
				System.err.println(e.getMessage());
			}
		});
	}
	
	@FXML
	private void handleDelete(ActionEvent event) {
		if(ms == null){
			this.alertError("No file available! Create a new file or open an existing file first.");
			return;
		}
//		this.actionNotImplemented("Delete");
		if(this.selected instanceof MfgSystem){
			stage.setTitle(MfgSystemController.title);
			this.ms = null;
			this.updateMachineList();
			this.updateJobTree();
			this.updateGanttChart(null);
			this.chartContent = null;
			this.fileModified = false;
			System.out.println("Deleting mfg system");
		} else if(this.selected instanceof Machine){
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
		this.fileModified = true;
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
		System.out.println("Deleting " + a.getName() + " from " + a.getFeature());
		a.getFeature().deleteActivity(a);
		System.out.println("Deleting " + a.getName() + " from " + a.getJob());
		a.getJob().deleteActivity(a);
		System.out.println("Deleting " + a.getName() + " from " + a.getMachine());
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
	
	@FXML
	private void handleAboutApp(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Manufacturing System Designer");
		alert.setHeaderText(null);
		alert.setContentText("This application is developed by Nur Arafat.\n\n"
					+"Application is used for creating, loading, and/or displaying manufacturing systems.");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(ButtonType.OK);
		alert.showAndWait();
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
