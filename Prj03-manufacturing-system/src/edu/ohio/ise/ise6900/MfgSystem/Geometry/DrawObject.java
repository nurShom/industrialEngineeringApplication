package edu.ohio.ise.ise6900.MfgSystem.Geometry;

public abstract class DrawObject  implements Comparable<DrawObject> {
	
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
		return "DrawObject [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(xCoordinate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yCoordinate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DrawObject))
			return false;
		DrawObject other = (DrawObject) obj;
		if (Double.doubleToLongBits(xCoordinate) != Double.doubleToLongBits(other.xCoordinate))
			return false;
		if (Double.doubleToLongBits(yCoordinate) != Double.doubleToLongBits(other.yCoordinate))
			return false;
		return true;
	}
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
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DrawObject o) {
		if(this.getArea() < o.getArea()){
			return -1;
		}
		if(this.getArea() > o.getArea()){
			return 1;
		}
		return 0;
	}

	public abstract void printout();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
