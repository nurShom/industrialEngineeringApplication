package edu.ohio.ise.ise6900.MfgSystem.gui.draw;

import java.util.LinkedList;

import javafx.scene.shape.Shape;

public interface Drawable {

	public LinkedList<Shape> makeShapes();
	
	public void display(String[] args);
	
	public void display(String[] args, String title, double width, double height);
}
