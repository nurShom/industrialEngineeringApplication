package edu.ohio.ise.ise6900.MfgSystem.gui.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MultiTextInputDialog extends Dialog<HashMap<String, String>> {

	private Map<String, String> fields;
	
	public MultiTextInputDialog(){
		this(new HashMap<String, String>());
	}
	
	public MultiTextInputDialog(HashMap<String, String> fields){
		this.fields = fields;
		this.makeDialog();
	}
	
	public void addField(String name, String label){
		fields.put(name, label);
	}
	
	public void addAllFields(HashMap<String, String> fields){
		this.fields.putAll(fields);
	}
	
	public void setFields(HashMap<String, String> fields) {
		this.fields = fields;
	}
	
	public void clearFields(){
		this.fields.clear();
	}

	public void makeDialog(){
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		int rowCount = 0;
		Map<String, TextField> texts = new LinkedHashMap<>();
		for(Entry<String, String> field : fields.entrySet()){
			grid.add(new Label(field.getValue() + ":"), 0, rowCount);
			TextField tf = new TextField();
			tf.setPromptText(field.getValue());
			grid.add(tf, 1, rowCount); 
			texts.put(field.getKey(), tf);
			rowCount++;
		}
		TextField firstBox = (TextField) texts.values().toArray()[0];
        GridPane.setHgrow(firstBox, Priority.ALWAYS);
        GridPane.setFillWidth(firstBox, true);

		this.getDialogPane().setContent(grid);
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Node okButton = this.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);
		
		// Add listener to all textfields to ensure ok button 
		// disabled before all of them have values 
		for(TextField text : texts.values()){
			text.textProperty().addListener((observable, oldValue, newValue) -> {
				boolean disable = newValue.trim().isEmpty();
				for(TextField t : texts.values()){
					if(t != text){
						disable = disable || t.getText().trim().isEmpty();
					}
				}
			    okButton.setDisable(disable);
			});
		}
		
		// Request focus on the first field by default.
		Platform.runLater(() -> firstBox.requestFocus());
		
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		    	HashMap<String, String> results = new HashMap<String, String>();
		    	for(Entry<String, TextField> text : texts.entrySet()){
		    		results.put(text.getKey(), text.getValue().getText());
		    	}
		        return results;
		    }
		    return null;
		});
	
	}
}
