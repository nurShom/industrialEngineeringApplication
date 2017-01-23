package edu.ohio.ise.ise6900.math;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
	protected static enum Options {
		SIN(1),
		COS(2),
		TAN(3),
		ASIN(4),
		ACOS(5),
		ATAN(6),
		LN(7),
		SQRT(8),
		POW(9),
		FACT(10);
		private int option;
		private ArrayList options;
		Options(int choice){
			this.setOption(choice);
			Options[] optSet = Options.values();
			ArrayList<Options> optionSet = new ArrayList<Options>(Arrays.asList(optSet));
			this.options = optionSet;
		}
		public int getOption() {
			return option;
		}
		public void setOption(int choice) {
			this.option = choice;
		}
		public boolean contains(int opt){
			if(this.options.contains("")){
				//TODO how to compare this??
			}
			return false;
		}
	}
	private String errMsg, outputMsg;
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
			try {
				choice = Integer.parseInt(option);
//				if(choice){
//					
//				}
				System.out.print("  Enter input:");
				input = Double.parseDouble(scan.nextLine());
			} catch (NumberFormatException nfe) {
//				nfe.printStackTrace();
				mf.setErrMsg("Please enter a valid number");
				continue;
			}

			Options.SIN.getOption();
			
			switch (choice) {
			case MathFunctions.SIN:
				mf.setOutputMsg("sin(" + input + ") = " + mf.getSinResult(input));
				break;
			case MathFunctions.COS:
				mf.setOutputMsg("cos(" + input + ") = " + mf.getCosResult(input));
				break;
			case MathFunctions.TAN:
				mf.setOutputMsg("tan(" + input + ") = " + mf.getTanResult(input));
				break;
			case MathFunctions.ASIN:
				if (mf.isAsinParameterValid(input)) {
					mf.setOutputMsg("asin(" + input + ") = " + mf.getAsinResult(input) + " degrees");
				} else {
					mf.setErrMsg("Please enter a number between -1.0 and 1.0 for arcsin");
				}
				break;
			case MathFunctions.ACOS:
				if (mf.isAcosParameterValid(input)) {
					mf.setOutputMsg("acos(" + input + ") = " + mf.getAcosResult(input) + " degrees");
				} else {
					mf.setErrMsg("Please enter a number between -1.0 and 1.0 for arccos");
				}
				break;
			case MathFunctions.ATAN:
				mf.setOutputMsg("atan(" + input + ") = " + mf.getAtanResult(input) + " degrees");
				break;
			case MathFunctions.LN:
				if (mf.isLnParameterValid(input)) {
					mf.setOutputMsg("ln(" + input + ") = " + mf.getLnResult(input));
				} else {
					mf.setErrMsg("Please enter a positive number");
				}
				break;
			case MathFunctions.SQRT:
				if (mf.isSqrtParameterValid(input)) {
					mf.setOutputMsg("sqrt(" + input + ") = " + mf.getSqrtResult(input));
				} else {
					mf.setErrMsg("Please enter a positive number or zero");
				}
				break;
			case MathFunctions.POW:
				mf.setOutputMsg("pow(" + input + ", 2) = " + mf.getPowResult(input, 2));
				break;
			case MathFunctions.FACT:
				String out = "";
				if (mf.isFactParameterValid(input)) {
					out += "factorial(" + input + ") = ";
					if (input < 13) {
						out += mf.factorialInt((int) input);
					} else if (input < 21) {
						out += mf.factorialLong((long) input);
					} else {
						out += mf.factorialBig(BigInteger.valueOf((long) input)).toString();
					}
					mf.setOutputMsg(out);
				} else {
					mf.setErrMsg("Please enter a positive integer or zero");
				}
				break;
			default:
				mf.setErrMsg("Please choose a number from the menu");
				break;

			}

		}
		scan.close();

	}
	
	private double getSinResult(double input) {
		return Math.sin(Math.toRadians(input));
	}
	
	private double getCosResult(double input) {
		return Math.cos(Math.toRadians(input));
	}
	
	private double getTanResult(double input) {
		return Math.tan(Math.toRadians(input));
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
		
	private double getAcosResult(double input) {
		return Math.toDegrees((Math.acos(input)));
	}

	private boolean isAcosParameterValid(double input) {
		if(input>=-1 && input<=1){
			return true;
		}
		return false;
	}	
	
	private double getAtanResult(double input) {
		return Math.toDegrees((Math.atan(input)));
	}

	private double getLnResult(double input) {
		return Math.log(input);
	}
	
	private boolean isLnParameterValid(double input) {
		if(input > 0){
			return true;
		}
		return false;
	}

	private double getSqrtResult(double input) {
		return Math.sqrt(input);
	}
	
	private boolean isSqrtParameterValid(double input) {
		if(input >= 0){
			return true;
		}
		return false;
	}

	private double getPowResult(double base, double power) {
		return Math.pow(base, power);
	}
	
	private boolean isFactParameterValid(double input) {
		if(input < 0){
			return false;
		}
		
		if(input>Math.floor(input)){
			return false;
		}
		
		return true;
	}

	private int factorialInt(int input) {
		if(input==0 || input==1){
			return 1;
		}
		return factorialInt(input-1)*input;
	}
	
	private long factorialLong(long input) {
		if(input==0 || input==1){
			return 1;
		}
		return factorialLong(input-1) * input;
	}
	
	private BigInteger factorialBig(BigInteger num){
		if(num.equals(BigInteger.ZERO) || num.equals(BigInteger.ONE) ){
			return BigInteger.ONE;
		}
		return (factorialBig(num.subtract(BigInteger.ONE))).multiply(num);
	}

	protected void promptUser(){
		System.out.println(
				"\n  ----------------------------------------------"
//				+ "\n  Selection of any of the math functions is required to proceed."
				+ "\n  (Enter 'exit' or 'quit' to exit the program)\n"
				+ "\n  Enter any of the following numbers:" 
				+ "\n  ->sin      :  " + MathFunctions.SIN
				+ "\n  ->cos      :  " + MathFunctions.COS
				+ "\n  ->tan      :  " + MathFunctions.TAN
				+ "\n  ->asin     :  " + MathFunctions.ASIN
				+ "\n  ->acos     :  " + MathFunctions.ACOS
				+ "\n  ->atan     :  " + MathFunctions.ATAN
				+ "\n  ->ln       :  " + MathFunctions.LN
				+ "\n  ->sqrt     :  " + MathFunctions.SQRT
				+ "\n  ->pow      :  " + MathFunctions.POW
				+ "\n  ->factorial: "  + MathFunctions.FACT  
				+ "\n");
		
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
		
		if(this.getOutputMsg() != null){
			System.out.println(" LAST RESULT:\n"
					+ " ##> " + this.getOutputMsg() + "\n");
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
	public String getOutputMsg() {
		return outputMsg;
	}

	/**
	 * @param outMsg the outMsg to set
	 */
	public void setOutputMsg(String outMsg) {
		this.outputMsg = outMsg;
	}

}
