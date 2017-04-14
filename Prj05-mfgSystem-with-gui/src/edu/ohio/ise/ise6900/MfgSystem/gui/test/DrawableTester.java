package edu.ohio.ise.ise6900.MfgSystem.gui.test;

import java.util.LinkedList;

import edu.ohio.ise.ise6900.MfgSystem.gui.DrawApplication;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class DrawableTester implements Drawable {

	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		
		Circle sun = new Circle(650, 80, 60);
		sun.setAccessibleText("SUN");
		sun.setStroke(Color.rgb(230, 230, 100));
		sun.setFill(Color.rgb(255, 255, 30));
		shapes.add(sun);
		
		Line ray01 = new Line(570, 89, 540, 100);
		ray01.setStrokeWidth(5);
		ray01.setStroke(Color.DARKVIOLET);
		shapes.add(ray01);
		
		Line ray02 = new Line(580, 115, 565, 124);
		ray02.setStrokeWidth(5);
		ray02.setStroke(Color.BLUE);
		shapes.add(ray02);
		
		Line ray03 = new Line(590, 125, 575, 137);
		ray03.setStrokeWidth(5);
		ray03.setStroke(Color.GREEN);
		shapes.add(ray03);
		
		Line ray04 = new Line(600, 135, 585, 153);
		ray04.setStrokeWidth(7);
		ray04.setStroke(Color.YELLOW);
		shapes.add(ray04);
		
		Line ray05 = new Line(610, 145, 595, 168);
		ray05.setStrokeWidth(5);
		ray05.setStroke(Color.ORANGE);
		shapes.add(ray05);
		
		Line ray06 = new Line(625, 155, 610, 185);
		ray06.setStrokeWidth(5);
		ray06.setStroke(Color.RED);
		shapes.add(ray06);
		
		return shapes;
	}

	@Override
	public void display(String[] args) {
		this.display(args, "Mfg System with GUI TEST", 800, 600);
	}

	@Override
	public void display(String[] args, String title, double width, double height) {
		DrawApplication da = new DrawApplication();
		DrawPanel dp = da.getCanvas(); 
		dp.addTarget(this);
		da.main(args);
	}

	public static void main(String[] args) {
		DrawableTester dt = new DrawableTester();
		dt.display(args);
	}

}
