package edu.ohio.ise.ise6900.swingpractice.application;

import java.awt.geom.Point2D;

public class Hypocycloid extends Hypotrochoid {

	public Hypocycloid(double outR, double inR) {
		super(outR, inR, inR);
	}

	public Hypocycloid(double x, double y, double outR, double inR) {
		super(x, y, outR, inR, inR);
	}

	public Hypocycloid(Point2D pos, double outR, double inR) {
		super(pos, outR, inR, inR);
	}

	public static void main(String[] args) {
		Hypocycloid h; 
//		h = new Hypocycloid(450, 450, 300, 10);
//		h = new Hypotrochoid(450, 450, 300, 30);
//		h = new Hypocycloid(450, 450, 300, 180);
//		h = new Hypocycloid(450, 450, 300, 180);
//		h = new Hypocycloid(450, 450, 300, 180);
//		h = new Hypocycloid(450, 450, 300, 180);
//		h = new Hypocycloid(450, 450, 300, 100);
//		h = new Hypocycloid(450, 450, 300, 100);
//		h = new Hypocycloid(450, 450, 300, 150);
//		h = new Hypocycloid(450, 450, 300, 150);
		h = new Hypocycloid(450, 450, 250, 90);
		h.display();
	}

}
