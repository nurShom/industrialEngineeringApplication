package edu.ohio.ise.ise6900.fxpractice.application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
//import javafx.scene.shape.Line;

public class FirstFxApp extends Main {

	public FirstFxApp() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void start(Stage primaryStage) {
		super.start(primaryStage);
		primaryStage.setTitle("Task 1");
		BorderPane root = new BorderPane();
		root.setPrefSize(800, 600);
		Scene scene = new Scene(root);
		
		Circle sun = new Circle(650, 80, 60);
		sun.setAccessibleText("SUN");
		sun.setStroke(Color.rgb(230, 230, 100));
		sun.setFill(Color.rgb(255, 255, 30));
		root.getChildren().add(sun);
		
		Line ray01 = new Line(570, 89, 540, 100);
		ray01.setStrokeWidth(5);
		ray01.setStroke(Color.DARKVIOLET);
		root.getChildren().add(ray01);
		
		Line ray02 = new Line(580, 115, 565, 124);
		ray02.setStrokeWidth(5);
		ray02.setStroke(Color.BLUE);
		root.getChildren().add(ray02);
		
		Line ray03 = new Line(590, 125, 575, 137);
		ray03.setStrokeWidth(5);
		ray03.setStroke(Color.GREEN);
		root.getChildren().add(ray03);
		
		Line ray04 = new Line(600, 135, 585, 153);
		ray04.setStrokeWidth(7);
		ray04.setStroke(Color.YELLOW);
		root.getChildren().add(ray04);
		
		Line ray05 = new Line(610, 145, 595, 168);
		ray05.setStrokeWidth(5);
		ray05.setStroke(Color.ORANGE);
		root.getChildren().add(ray05);
		
		Line ray06 = new Line(625, 155, 610, 185);
		ray06.setStrokeWidth(5);
		ray06.setStroke(Color.RED);
		root.getChildren().add(ray06);
		
		Line horizon = new Line(0, 450, 800, 450);
		horizon.setStrokeWidth(3);
		root.getChildren().add(horizon);
		
		Rectangle houseFront = new Rectangle(175, 340, 220, 140);
		houseFront.setStrokeWidth(2);
		houseFront.setStroke(Color.rgb(240, 190, 40));
		houseFront.setFill(Color.WHEAT);
		root.getChildren().add(houseFront);
		
		Polygon houseSide = new Polygon(175, 340, 175, 480, 140, 450, 140, 310);
		houseSide.setStrokeWidth(2);
		houseSide.setStroke(Color.rgb(240, 190, 40));
		houseSide.setFill(Color.WHEAT);
		root.getChildren().add(houseSide);
		
		Polygon houseRoofSide = new Polygon(170, 345, 125, 320, 150, 250);
		houseRoofSide.setStrokeWidth(2);
		houseRoofSide.setStroke(Color.rgb(240, 190, 40));
		houseRoofSide.setFill(Color.RED);
		root.getChildren().add(houseRoofSide);
		
		Polygon houseRoofFront = new Polygon(170, 345, 150, 250, 375, 250, 405, 345);
		houseRoofFront.setStrokeWidth(2);
		houseRoofFront.setStroke(Color.rgb(240, 190, 40));
		houseRoofFront.setFill(Color.RED);
		root.getChildren().add(houseRoofFront);
		
		Rectangle treeTrunk = new Rectangle(430, 230, 25, 250);
		treeTrunk.setFill(Color.BROWN);
		root.getChildren().add(treeTrunk);
		
		Ellipse treeTop = new Ellipse(445, 180, 130, 105);
		treeTop.setFill(Color.DARKGREEN);
		root.getChildren().add(treeTop);
		
		CubicCurve carFront = new CubicCurve(470, 510, 525, 455, 600, 495, 650, 430);
		carFront.setStroke(Color.TRANSPARENT);
		carFront.setFill(Color.TRANSPARENT);
		Polygon carBlock = new Polygon(650, 430, 771, 430, 770, 510, 470, 510);
		Polygon carTip = new Polygon(525, 470, 525, 550, 450, 510);
		Path carBody = (Path) Path.subtract(Path.subtract(carBlock, carFront), carTip);
		carBody.setStroke(Color.SKYBLUE);
		carBody.setFill(Color.DARKSLATEBLUE);
		root.getChildren().add(carBody);
		
		Circle carWheeel1 = new Circle(615, 512, 23);
		carWheeel1.setFill(Color.DARKBLUE);
		carWheeel1.setStroke(Color.BLACK);
		root.getChildren().add(carWheeel1);
		Circle carWheeel2 = new Circle(730, 512, 23);
		carWheeel2.setStroke(Color.BLACK);
		carWheeel2.setFill(Color.DARKBLUE);
		root.getChildren().add(carWheeel2);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}
