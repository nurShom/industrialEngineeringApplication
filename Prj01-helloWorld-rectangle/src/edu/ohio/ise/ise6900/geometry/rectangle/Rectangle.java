package edu.ohio.ise.ise6900.geometry.rectangle;

import java.util.Scanner;

public class Rectangle {
	
	private double width, height;
	
	protected double getArea(){
		return this.width * this.height;
	}
	
	protected double getPerimiter(){
		return 2 * (this.width + this.height);
	}
	
	protected double getDiagonal(){
		return Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
	}
	
	protected double getAreaOfCircumscribedCircle(){
		return Math.PI * Math.pow((this.getDiagonal()/2), 2);
	}

	public static void main(String[] args) {
		
		Rectangle rec = new Rectangle();
		Scanner sin = new Scanner(System.in);
		System.out.print("Please enter height and width.\n Height: ");
		rec.setHeight(Double.parseDouble(sin.nextLine()));
		System.out.print(" Width: ");
		rec.setWidth(Integer.parseInt(sin.nextLine()));
		sin.close();
		
		System.out.println(rec.toString());
		System.out.println("Area of the rectangle: " + rec.getArea());
		System.out.println("Perimeter of the rectangle: " + rec.getPerimiter());
		System.out.println("Length of diagonal of the rectangle: " + rec.getDiagonal());
		System.out.println("Area of the circumscribed circle of the rectangle: " + rec.getAreaOfCircumscribedCircle());
		
	}


	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + height);
		result = (int) (prime * result + width);
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
		if (getClass() != obj.getClass())
			return false;
		Rectangle other = (Rectangle) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
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

}
