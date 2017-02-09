package edu.ohio.ise.ise6900.MfgSystem.Geometry;

public class RightTriangle extends DrawObject {
	
	private double height;
	private double base;

	public RightTriangle() {
		this(0, 0);
	}
	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public RightTriangle(double xCoordinate, double yCoordinate) {
		super(xCoordinate, yCoordinate);
		height = 0;
		base = 0;
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
	/**
	 * @return the base
	 */
	public double getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(double base) {
		this.base = base;
	}
	@Override
	public double getArea() {
		return base * height * 0.5;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RightTriangle [height=" + height + ", base=" + base + ", Area=" + getArea() 
				+ ", X-Coordinate=" + getxCoordinate() + ", Y-Coordinate=" + getyCoordinate() + "]";
	}
	@Override
	public void printout() {
		System.out.println(this.toString());
	}

}
