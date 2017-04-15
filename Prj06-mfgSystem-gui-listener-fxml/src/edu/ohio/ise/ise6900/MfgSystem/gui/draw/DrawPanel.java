package edu.ohio.ise.ise6900.MfgSystem.gui.draw;

import java.util.LinkedList;

import javafx.scene.Group;

public class DrawPanel extends Group {
	LinkedList<Drawable> targets;
	 
	public DrawPanel(){
		this(new LinkedList<Drawable>());
	}
	
	public DrawPanel(LinkedList<Drawable> targets){
		this.targets = targets;
	}
	
	public void display(String [] args){
		this.display(args, "Mfg System with GUI", 800, 600);
	}
	
	public void display(String[] args, String title, double width, double height){
		
	}
	
	public void makeShapes(){
		for(Drawable d : this.targets){
			this.getChildren().addAll(d.makeShapes());
		}
	}
	
	public void clear(){
		this.getChildren().clear();
	}
	
	public void addTarget(Drawable target){
		this.targets.add(target);
	}
	
	public void addAllTargets(LinkedList<Drawable> targets){
		this.targets.addAll(targets);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
