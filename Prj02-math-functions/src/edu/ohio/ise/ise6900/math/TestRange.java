/**
 * 
 */
package edu.ohio.ise.ise6900.math;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author shomik
 *
 */
public class TestRange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestRange tr = new TestRange();
		Scanner scan = new Scanner(System.in);
		// for(int i=-1440; i<=0; i++){
		// System.out.println(i + ": " + Math.toRadians(i) + " : " +
		// Math.atan(Math.toRadians(i)));
		// }

//		for(double i=-100; i<=100; i+=.5){
//			System.out.println("ln("+i+"): " + Math.log(i));
//		}
		
//		int num;
//		for (int i = 0; i < 100; i++) {
//			System.out.print("Enter number:");
//			num = Integer.parseInt(scan.nextLine());
//			System.out.println("int factorial(" + num + "): " + tr.factorial(num));
//			System.out.println("long factorial(" + num + "): " + tr.factLong(num));
//			System.out.println("BigInteger factorial(" + num + "): " + tr.factBig(BigInteger.valueOf(num)).toString());
//		}

		System.out.println(Character.isLetter('$'));
		int x = 36;
		BigInteger fact = tr.bigFactorial(new BigInteger("" + x));
		System.out.println("Big fact of " + x + " with a loop: " + fact);

		System.out.println("Big fact of " + x + " recursively: " + tr.factBig(new BigInteger("" + x)));
		
		scan.close();
	}
	
	public int factorial(int num){
		if(num==0 || num==1){
			return 1;
		}
		return factorial(num-1)*num;
	}
	
	public long factLong(long num){
		if(num==0 || num==1){
			return 1;
		}
		return factLong(num-1)*num;
	}
	
	public BigInteger factBig(BigInteger num){
		if(num.equals(BigInteger.ZERO) || num.equals(BigInteger.ONE) ){
			return BigInteger.ONE;
		}
		return (factBig(num.subtract(BigInteger.ONE))).multiply(num);
	}
	
	public BigInteger bigFactorial(BigInteger p){
		BigInteger result = new BigInteger(p.toString());
		for(BigInteger i = new BigInteger("1"); i.compareTo(p)==-1; i = i.add(new BigInteger("1"))){
			result = result.multiply(i);
			System.out.println("round "+i.toString() + ", result: " + result);
		}
		return result;
	}

}
