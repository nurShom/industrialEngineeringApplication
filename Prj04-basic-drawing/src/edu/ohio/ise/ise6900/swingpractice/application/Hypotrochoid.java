package edu.ohio.ise.ise6900.swingpractice.application;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import edu.ohio.ent.cs4500.draw.DrawObject;
import edu.ohio.ent.cs4500.draw.DrawPanel;

public class Hypotrochoid extends DrawObject {
	private Ellipse2D outer, inner;
	private Point2D outCenter;
	private Point2D inCenter; // Initial position of the inner circle
	private double outRadius;
	private double inRadius;
	private double distance;

	/**
	 * Constructs a Hypotrochoid object, which is created by a moving point with 
	 * reference to two circles, assuming the center of outer circle, thus the 
	 * center of the Hypotrochoid is at (0, 0)  
	 * @param outR Radius of the outer circle
	 * @param inR  Radius of the inner circle
	 * @param d Distance between the inner circle center and the moving point P
	 */
	public Hypotrochoid(double outR, double inR, double d) {
		this(0, 0, outR, inR, d);
	}

	/**
	 * Constructs a Hypotrochoid object, which is created by a moving point with 
	 * reference to two circles  
	 * @param x x-coordinate of the center of the outer circle, thus the center of the Hypotrochoid
	 * @param y y-coordinate of the center of the outer circle, thus the center of the Hypotrochoid
	 * @param outR Radius of the outer circle
	 * @param inR  Radius of the inner circle
	 * @param d Distance between the inner circle center and the moving point P
	 */
	public Hypotrochoid(double x, double y, double outR, double inR, double d) {
		this(new Point2D.Double(x, y), outR, inR, d);
	}
	
	/**
	 * Constructs a Hypotrochoid object, which is created by a moving point with 
	 * reference to two circles  
	 * @param pos Position of the center of the outer circle, thus the center of the Hypotrochoid
	 * @param outR Radius of the outer circle
	 * @param inR  Radius of the inner circle
	 * @param d Distance between the inner circle center and the moving point P
	 */
	public Hypotrochoid(Point2D pos, double outR, double inR, double d) {
		super(pos);
		
		outCenter = pos;
		//Initial position of inner circle:
		inCenter = new Point2D.Double(pos.getX() + outR - inR, pos.getY());
		
		outer = new Ellipse2D.Double(outCenter.getX()-outR, outCenter.getY()-outR, outR*2, outR*2);
		inner = new Ellipse2D.Double(inCenter.getX()-inR, inCenter.getY()-inR, inR*2, inR*2);
		
		outRadius = outR;
		inRadius = inR;
		distance = d;
	}

	@Override
	public void makeDrawSet(DrawPanel canvas) {
		//Draw reference lines
		canvas.addShape(Color.LIGHT_GRAY, new Line2D.Double(0, outCenter.getY(), outCenter.getX()*2, outCenter.getY() ));
		canvas.addShape(Color.LIGHT_GRAY, new Line2D.Double(outCenter.getX(), 0, outCenter.getX(), outCenter.getY()*2 ));
		
		//Draw outer circle with center
		canvas.addShape(Color.BLUE, this.outer);
		Ellipse2D outC = new Ellipse2D.Double(this.outCenter.getX()-1.5, this.outCenter.getY()-1.5, 3, 3);
		canvas.addShape(Color.BLUE, outC);
		
		//Draw inner circle with center
		canvas.addShape(Color.GREEN, this.inner);
		Ellipse2D inC = new Ellipse2D.Double(this.inCenter.getX()-2, this.inCenter.getY()-2, 4, 4);
		canvas.addShape(Color.GREEN, inC);
		
		//Draw initial position of the moving point
		Point2D pos0 = this.getPosition(0);
		Ellipse2D pos0Draw = new Ellipse2D.Double(pos0.getX()-2.5, pos0.getY()-2.5, 5, 5);
		canvas.addShape(Color.MAGENTA, pos0Draw);
		
		//Draw initial distance between the moving point and the center of the inner circle 
		canvas.addShape(Color.DARK_GRAY, new Line2D.Double(this.inCenter, pos0));
		
		//Draw the Hypotrochoid
		for (Shape line : this.getDrawList()) {
			canvas.addShape(Color.RED, line);
		}
	}

	public LinkedList<Shape> getDrawList() {
		LinkedList<Shape> hypotrochoid = new LinkedList<Shape>();
		Point2D p1 = this.getPosition(0);
		for (double a=1; a < 6480; a++) {
			Point2D p2 = this.getPosition(a);
			Line2D l1 = new Line2D.Double(p1, p2);
			hypotrochoid.add(l1);
			p1 = p2;
		}
		return hypotrochoid;
	}

	/**
	 * @param angle The angle (in degree) formed by the horizontal line 
	 * 		  and the center of the rolling circle
	 * @return A Point2D object denoting the position of the moving point at that angle
	 */
	private Point2D getPosition(double angle) {
		double theta = Math.toRadians(angle);
		double deltaR = this.outRadius - this.inRadius;
		double thetaR = (deltaR / this.inRadius) * theta;
		double x = deltaR * Math.cos(theta) + (this.distance * Math.cos(thetaR)) + this.outCenter.getX();
		double y = deltaR * Math.sin(theta) - (this.distance * Math.sin(thetaR)) + this.outCenter.getY();
		return new Point2D.Double(x, y);
	}

	@Override
	public double area() {
		return 0;
	}

	public static void main(String[] args) {
		Hypotrochoid h; 
//		h = new Hypotrochoid(450, 450, 300, 10, 20);
//		h = new Hypotrochoid(450, 450, 300, 30, 20);
//		h = new Hypotrochoid(450, 450, 300, 180, 60);
//		h = new Hypotrochoid(450, 450, 300, 180, 20);
//		h = new Hypotrochoid(450, 450, 300, 180, 200);
//		h = new Hypotrochoid(450, 450, 300, 180, 300);
//		h = new Hypotrochoid(450, 450, 300, 100, 90);
//		h = new Hypotrochoid(450, 450, 300, 100, 100);
//		h = new Hypotrochoid(450, 450, 300, 150, 150);
//		h = new Hypotrochoid(450, 450, 300, 150, 100);
		h = new Hypotrochoid(450, 450, 250, 90, 150);
		h.display();
	}

}