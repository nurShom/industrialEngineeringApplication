package edu.ohio.ise.ise6900.swingpractice.application;

import java.awt.Dimension;
import edu.ohio.ent.cs4500.draw.DrawPanel;
import edu.ohio.ent.cs4500.draw.Drawable;

public class FunctionViewer implements Drawable {
	DrawPanel dp;

	public FunctionViewer() {
		dp = new DrawPanel(this);
		
	}

	@Override
	public void display() {
		dp.display();
	}

	@Override
	public void display(String title, Dimension size, int closeOperation) {
		dp.display(title, size, closeOperation);
	}

	@Override
	public void makeDrawSet(DrawPanel canvas) {
		
	}

	@Override
	public void repaint() {
		
	}

	public static void main(String[] args) {
		Hypotrochoid h = new Hypotrochoid(400, 400, 300, 180, 300);
		h.display();
	}
}
