package edu.ohio.ise.ise6900.fxPractice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * Class content copied from 
 * http://docs.oracle.com/javafx/2/ui_controls/list-view.htm
 * 
 */
public class ListViewTest01 extends Main {
	public static final ObservableList<String> names = FXCollections.observableArrayList();
	public static final ObservableList<String> data = FXCollections.observableArrayList();

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("List View Sample");        
        
        final ListView<String> listView = new ListView<String>(data);
        listView.setPrefSize(200, 20);
        listView.setEditable(true);
        
        names.addAll(
             "Adam", "Alex", "Alfred", "Albert",
             "Brenda", "Connie", "Derek", "Donny", 
             "Lynne", "Myrtle", "Rose", "Rudolph", 
             "Tony", "Trudy", "Williams", "Zach"
        );
         
        for (int i = 0; i < 18; i++) {
            data.add("anonym");
        }
          
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(names));              
               
        StackPane root = new StackPane();
        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}

}
