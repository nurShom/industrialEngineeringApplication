package edu.ohio.ise.ise6900.MfgSystem.model.geometry;

import com.sun.corba.se.impl.io.TypeMismatchException;

import edu.ohio.ise.ise6900.MfgSystem.gui.draw.Drawable;

public abstract class DrawObject implements Comparable<DrawObject>, Drawable {
	
	private double xCoordinate = 0;
	private double yCoordinate = 0;
	
	/**
	 * 
	 */
	public DrawObject() {
		this(0, 0);
	}
	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public DrawObject(double xCoordinate, double yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Object " + this.getClass().getSimpleName() 
				+ ", position: (" + xCoordinate + ", " + yCoordinate + ")";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public abstract boolean equals(Object obj);
	
	/**
	 * @return the xCoordinate
	 */
	public abstract double getArea();
	/**
	 * @return the xCoordinate
	 */
	public double getxCoordinate() {
		return xCoordinate;
	}
	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	/**
	 * @return the yCoordinate
	 */
	public double getyCoordinate() {
		return yCoordinate;
	}
	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public abstract void printout();
	
	public static void main(String[] args) {
		DrawObject rtr01 = new RightTriangle();
		DrawObject rec01 = new Rectangle();
		Rectangle rec02 = new Rectangle(3, 5);
		Rectangle rec03 = new Rectangle(4, 6);
		rec02.setWidth(6);
		rec02.setHeight(10);
		rtr01.printout();
		rec01.printout();
		rec02.printout();
		rec03.printout();
		System.out.println("rec01 is " + rec01.getClass().getName());
		System.out.println("rec02 is " + rec02.getClass().getName());
		
		System.out.println(rec02.compareTo(rec03));
		try{
			System.out.println(rec01.compareTo(rec02));
			System.out.println(rtr01.compareTo(rec01));
		} catch(TypeMismatchException tme){
			tme.printStackTrace();
		}
	}

}
