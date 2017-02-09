/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.Geometry;

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
		this (0, 0);
	}

	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public Rectangle(double xCoordinate, double yCoordinate) {
		super(xCoordinate, yCoordinate);
		this.width = 0;
		this.height= 0;
	}

	@Override
	public double getArea() {
		return this.width * this.height;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rectangle [Height=" + height + ", Width=" + width + ", Area=" + getArea() 
				+ ", X-Coordinate=" + getxCoordinate() + ", Y-Coordinate=" + getyCoordinate() + "]";
	}
	@Override
	public void printout() {
		System.out.println(this.toString());
	}

}
