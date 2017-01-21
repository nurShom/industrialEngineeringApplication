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
	
	public static final String ANSI_RESET = "\u001B[0m",
			ANSI_BLACK = "\u001B[30m",
			ANSI_RED = "\u001B[31m",//"\u001B31;1m"
			ANSI_GREEN = "\u001B[32m",
			ANSI_YELLOW = "\u001B[33m",
			ANSI_BLUE = "\u001B[34m",
			ANSI_PURPLE = "\u001B[35m",
			ANSI_CYAN = "\u001B[36m",
			ANSI_WHITE = "\u001B[37m";
	protected static final int SIN = 1,
			COS = 2,
			TAN = 3,
			ASIN = 4,
			ACOS = 5,
			ATAN = 6,
			LN = 7,
			SQRT = 8,
			POW = 9,
			FACT = 10;
	private String errMsg, outMsg;
	/**
	 * @param args
	 * @throws InterruptedException 
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
				System.out.println("\nThank you for using MathFunctions!");
				break;
			}
			try{
				choice = Integer.parseInt(option);				
				System.out.print("  Enter input:");
				input = Double.parseDouble(scan.nextLine());
			} catch (NumberFormatException nfe) {
//				nfe.printStackTrace();
				mf.setErrMsg("Please enter a valid number");
				continue;
			}
			
			switch(choice) {
			case SIN:
				mf.setOutMsg("sin(" +input + ") = " + mf.getSinResult(input));
				break;
			case ASIN:
				if(mf.isAsinParameterValid(input)){
					mf.setOutMsg("asin(" +input + ") = " + mf.getAsinResult(input) + " degrees");
				}
				else{
					mf.setErrMsg("Please enter a number between -1.0 and 1.0 for arcsin");
				}
				break;
				
			}

		}
		scan.close();

	}

	private double getSinResult(double input) {
		return Math.sin(Math.toRadians(input));
	}
	
	private double getAsinResult(double input) {
		return Math.toDegrees((Math.asin(input)));
	}

	private boolean isAsinParameterValid(double input) {
		if(input>=-1 && input<=1){
			return true;
		}
		return false;
	}

	protected void promptUser(){
		System.out.println(
				"\n  ----------------------------------------------"
//				+ "\n  Selection of any of the math functions is required to proceed."
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
		
		if(this.getErrMsg() != null){
			System.out.flush();
			System.err.flush();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println(" !! " + this.getErrMsg() + " !!\n");
			System.out.flush();
			System.err.flush();
			this.setErrMsg(null);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(this.getOutMsg() != null){
			System.out.println(" LAST RESULT:\n"
					+ " ##> " + this.getOutMsg() + "\n");
		}
		
		System.out.print("  $$ select ->");
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the outMsg
	 */
	public String getOutMsg() {
		return outMsg;
	}

	/**
	 * @param outMsg the outMsg to set
	 */
	public void setOutMsg(String outMsg) {
		this.outMsg = outMsg;
	}

}
