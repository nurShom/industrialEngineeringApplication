package edu.ohio.ise.ise6900.math;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
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
	private final Integer[] optSet = {SIN, COS, TAN, ASIN, ACOS, ATAN, LN, SQRT, POW, FACT};
	protected final ArrayList<Integer> options = new ArrayList<Integer>(Arrays.asList(optSet));
	private String errMsg, outputMsg;
	private boolean doubleOutput;
	private double outputValue=0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Integer choice = 0;
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
				if(mf.options.contains(choice)){
					System.out.print("  Enter input:");
					input = Double.parseDouble(scan.nextLine());
				}
				else{
					mf.setErrMsg("Please choose a number from the menu");
					continue;
				}
			} catch (NumberFormatException nfe) {
//				nfe.printStackTrace();
				mf.setErrMsg("Please enter a valid number");
				continue;
			}

			mf.setDoubleOutput(true);

			switch (choice) {
			case MathFunctions.SIN:
				mf.setOutputMsg("sin(" + input + ") = ");
				mf.setOutputValue(mf.getSinResult(input));
				break;
			case MathFunctions.COS:
				mf.setOutputMsg("cos(" + input + ") = ");
				mf.setOutputValue(mf.getCosResult(input));
				break;
			case MathFunctions.TAN:
				mf.setOutputMsg("tan(" + input + ") = ");
				mf.setOutputValue(mf.getTanResult(input));
				break;
			case MathFunctions.ASIN:
				if (mf.isValidAsinParameter(input)) {
					mf.setOutputMsg("asin(" + input + ") = (degrees) ");
					mf.setOutputValue(mf.getAsinResult(input));
				} else {
					mf.setErrMsg("Please enter a number between -1.0 and 1.0 for arcsin");
				}
				break;
			case MathFunctions.ACOS:
				if (mf.isValidAcosParameter(input)) {
					mf.setOutputMsg("acos(" + input + ") = (degrees) ");
					mf.setOutputValue(mf.getAcosResult(input));
				} else {
					mf.setErrMsg("Please enter a number between -1.0 and 1.0 for arccos");
				}
				break;
			case MathFunctions.ATAN:
				mf.setOutputMsg("atan(" + input + ") = (degrees) ");
				mf.setOutputValue(mf.getAtanResult(input));
				break;
			case MathFunctions.LN:
				if (mf.isValidLnParameter(input)) {
					mf.setOutputMsg("ln(" + input + ") = ");
					mf.setOutputValue(mf.getLnResult(input));
				} else {
					mf.setErrMsg("Please enter a positive number");
				}
				break;
			case MathFunctions.SQRT:
				if (mf.isValidSqrtParameter(input)) {
					mf.setOutputMsg("sqrt(" + input + ") = ");
					mf.setOutputValue(mf.getSqrtResult(input));
				} else {
					mf.setErrMsg("Please enter a positive number or zero");
				}
				break;
			case MathFunctions.POW:
				System.out.print("  Enter power:");
				double power = Double.parseDouble(scan.nextLine());
				mf.setOutputMsg("pow(" + input + ", "+ power+") = ");
				mf.setOutputValue(mf.getPowResult(input, power));
				break;
			case MathFunctions.FACT:
				String out = "";
				if (mf.isValidFactParameter(input)) {
					
					out += "factorial(" + input + ") = ";
					if (input < 13) {
						out += mf.factorialInt((int) input);
					} else if (input < 21) {
						out += mf.factorialLong((long) input);
					} else {
						out += mf.factorialBig(BigInteger.valueOf((long) input)).toString();
					}
					mf.setOutputMsg(out);
					mf.setDoubleOutput(false);
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
	
	public double getSinResult(double input) {
		return Math.sin(Math.toRadians(input));
	}
	
	public double getCosResult(double input) {
		return Math.cos(Math.toRadians(input));
	}
	
	public double getTanResult(double input) {
		return Math.tan(Math.toRadians(input));
	}
	
	public double getAsinResult(double input) {
		return Math.toDegrees((Math.asin(input)));
	}

	public boolean isValidAsinParameter(double input) {
		if(input>=-1 && input<=1){
			return true;
		}
		return false;
	}
		
	public double getAcosResult(double input) {
		return Math.toDegrees((Math.acos(input)));
	}

	public boolean isValidAcosParameter(double input) {
		if(input>=-1 && input<=1){
			return true;
		}
		return false;
	}	
	
	public double getAtanResult(double input) {
		return Math.toDegrees((Math.atan(input)));
	}

	public double getLnResult(double input) {
		return Math.log(input);
	}
	
	public boolean isValidLnParameter(double input) {
		if(input > 0){
			return true;
		}
		return false;
	}

	public double getSqrtResult(double input) {
		return Math.sqrt(input);
	}
	
	public boolean isValidSqrtParameter(double input) {
		if(input >= 0){
			return true;
		}
		return false;
	}

	public double getPowResult(double base, double power) {
		return Math.pow(base, power);
	}
	
	public boolean isValidFactParameter(double input) {
		if(input < 0){
			return false;
		}
		
		if(input>Math.floor(input)){
			return false;
		}
		
		return true;
	}

	public int factorialInt(int input) {
		if(input==0 || input==1){
			return 1;
		}
		return factorialInt(input-1)*input;
	}
	
	public long factorialLong(long input) {
		if(input==0 || input==1){
			return 1;
		}
		return factorialLong(input-1) * input;
	}
	
	public BigInteger factorialBig(BigInteger num){
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
			if(this.isDoubleOutput()){
				DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
				df.setMaximumFractionDigits(340);//DecimalFormat.DOUBLE_FRACTION_DIGITS
				String outv = df.format(this.getOutputValue());
				System.out.println(" LAST RESULT:\n ##> " +this.getOutputMsg() + outv + "\n");
			}
			else{
				System.out.println(" LAST RESULT:\n ##> " +this.getOutputMsg() + "\n");
			}
		}
		
		System.out.print("  $$ select ->");
	}

	/**
	 * @return the errMsg
	 */
	private String getErrMsg() {
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
	private String getOutputMsg() {
		return outputMsg;
	}

	/**
	 * @param outMsg the outMsg to set
	 */
	public void setOutputMsg(String outMsg) {
		this.outputMsg = outMsg;
	}

	/**
	 * @return the outputValue
	 */
	private double getOutputValue() {
		return outputValue;
	}

	/**
	 * @param outputValue the outputValue to set
	 */
	public void setOutputValue(double outputValue) {
		this.outputValue = outputValue;
	}

	/**
	 * @return the doubleOutput
	 */
	private boolean isDoubleOutput() {
		return doubleOutput;
	}

	/**
	 * @param doubleOutput the doubleOutput to set
	 */
	public void setDoubleOutput(boolean doubleOutput) {
		this.doubleOutput = doubleOutput;
	}

}
