package edu.ohio.ise.ise6900.geometry;
/*
 * ISE6900 Object Oriented Application in Industrial Engineering
 * Programming Project 01 
 * 
 * @author Nur Shomik Arafat
 * Date: 2017-01-14
 * @version 1.0
 * 
 */
import java.util.Scanner;
/**
 * Class Rectangle represents a geometric Rectangle.
 * It has two attributes: width and height. 
 * The class has methods that calculate other measurements
 * for the rectangle using the width and the height. 
 * 
 * @author Nur Shomik Arafat
 *
 */
public class Rectangle {
	
	/**
	 * Class attributes:
	 * Class Rectangle has two attributes:
	 * Width and Height
	 */
	private double width, height;
	
	/**
	 * @return the area of the rectangle
	 */
	protected double getArea(){
		return this.width * this.height;
	}
	
	/**
	 * @return the perimeter of the rectangle
	 */
	protected double getPerimiter(){
		return 2 * (this.width + this.height);
	}
	
	/**
	 * @return the diagonal of the rectangle
	 */
	protected double getDiagonal(){
		return Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
	}
	
	/**
	 * @return the area of the circumscribed circle 
	 */
	protected double getAreaOfCircumscribedCircle(){
		return Math.PI * Math.pow((this.getDiagonal()/2), 2);
	}

	/**
	 * @param args
	 */
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
	 * @return the width of the rectangle
	 */
	public double getWidth() {
		return width;
	}


	/**
	 * @param width the width to set of the rectangle
	 */
	public void setWidth(double width) {
		this.width = width;
	}


	/**
	 * @return the height of the rectangle
	 */
	public double getHeight() {
		return height;
	}


	/**
	 * @param height the height to set of the rectangle
	 */
	public void setHeight(double height) {
		this.height = height;
	}

}
