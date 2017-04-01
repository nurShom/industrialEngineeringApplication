/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.geometry;

import java.util.LinkedList;

import com.sun.corba.se.impl.io.TypeMismatchException;

import javafx.scene.shape.Shape;

/**
 * @author na551411
 *
 */
public class Rectangle extends DrawObject {

	private double width;
	private double height;
	
	/**
	 * 
	 */
	public Rectangle() {
		this (0, 0, 0, 0);
	}

	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public Rectangle(double xCoordinate, double yCoordinate) {
		this(xCoordinate, yCoordinate, 0, 0);
	}
	
	public Rectangle(double xCoordinate, double yCoordinate, double height, double width) {
		super(xCoordinate, yCoordinate);
		this.height = height;
		this.width = width;
	}

	@Override
	public double getArea() {
		return this.width * this.height;
	}
	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + ", height=" + height + ", width=" + width + ", area=" + getArea();
	}
	@Override
	public void printout() {
		System.out.println(this.toString());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.getxCoordinate());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.getyCoordinate());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Rectangle)) {
			return false;
		}
		Rectangle other = (Rectangle) obj;
		if (Double.doubleToLongBits(this.getxCoordinate()) != Double.doubleToLongBits(other.getxCoordinate())){
			return false;
		}
		if (Double.doubleToLongBits(this.getyCoordinate()) != Double.doubleToLongBits(other.getyCoordinate())){
			return false;
		}
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height)) {
			return false;
		}
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(DrawObject o) {
		if(this.equals(o)){
			return 0;
		}
		if (!(o instanceof Rectangle)) {
			throw new TypeMismatchException(o.getClass().getName() 
						+ " is not a " + this.getClass().getName());
		}
		if(this.getArea() < o.getArea()){
			return -1;
		}
		if(this.getArea() > o.getArea()){
			return 1;
		}
		return 0;
	}

	@Override
	public LinkedList<Shape> makeShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void display(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String[] args, String title, double width, double height) {
		// TODO Auto-generated method stub
		
	}

}
