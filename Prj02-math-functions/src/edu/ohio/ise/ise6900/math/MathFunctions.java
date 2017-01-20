package edu.ohio.ise.ise6900.math;
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
 * Class ...
 * 
 * @author Nur Shomik Arafat
 *
 */
public class MathFunctions {
	
	protected final int SIN = 1,
			COS = 2,
			TAN = 3,
			ASIN = 4,
			ACOS = 5,
			ATAN = 6,
			LN = 7,
			SQRT = 8,
			POW = 9,
			FACT = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int choice = 0;
		double input = 0;
		Scanner scan = new Scanner(System.in);
		String option = null;
		MathFunctions mf = new MathFunctions();
		
		while (true) {
			mf.promptUser();
			option = scan.nextLine();

			if (option != null && (option.equalsIgnoreCase("exit") || option.equalsIgnoreCase("quit"))) {
				break;
			}
			try{
				choice = Integer.parseInt(option);				
				System.out.print("Enter the number you want to calculate:");
				input = Double.parseDouble(scan.nextLine());
			} catch (NumberFormatException nfe) {
//				nfe.printStackTrace();
				System.err.println("\n  Please enter a valid number!");
				continue;
			}
			
			switch(choice){
				
			}

		}
		scan.close();

	}
	
	protected void promptUser(){
		System.out.println(
				"\n  Selection of any of the math functions is required to proceed."
				+ "\n  (Enter 'exit' or 'quit' to exit the program)\n"
				+ "\n  Enter any of the following numbers:" 
				+ "\n  ->sin      :  1"
				+ "\n  ->cos      :  2" 
				+ "\n  ->tan      :  3" 
				+ "\n  ->asin     :  4" 
				+ "\n  ->acos     :  5" 
				+ "\n  ->atan     :  6"
				+ "\n  ->ln       :  7" 
				+ "\n  ->sqrt     :  8" 
				+ "\n  ->pow      :  9" 
				+ "\n  ->factorial: 10\n");
		System.out.print("  $$ select ->");
	}

}
