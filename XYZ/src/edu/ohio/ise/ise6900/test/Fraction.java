package edu.ohio.ise.ise6900.test;

public class Fraction {

	private int numerator;
	private int denominator;

	public Fraction(int n, int d) {
		int gcd = this.gcd(n, d);
		if(gcd != d){
			n /= gcd;
			d /= gcd;
		}
		numerator = n;
		denominator = d;
	}

	public double toDouble() {
		return ((double) numerator) / ((double) denominator);
	}

	private int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	public static Fraction add(Fraction a, Fraction b) {
		if (a.denominator != b.denominator) {
			int aTop = b.denominator * a.numerator;
			int bTop = a.denominator * b.numerator;
			return new Fraction(aTop + bTop, a.denominator * b.denominator);
		} else {
			return new Fraction(a.numerator + b.numerator, a.denominator);
		}
	}

	public static Fraction divide(Fraction a, Fraction b) {
		return new Fraction(a.numerator * b.denominator, a.denominator * b.numerator);
	}

	public static Fraction multiply(Fraction a, Fraction b) {
		return new Fraction(a.numerator * b.numerator, a.denominator * b.denominator);
	}

	public static Fraction subtract(Fraction a, Fraction b) {
		if (a.denominator != b.denominator) {
			int aTop = b.denominator * a.numerator;
			int bTop = a.denominator * b.numerator;
			return new Fraction(aTop - bTop, a.denominator * b.denominator);
		} else {
			return new Fraction(a.numerator - b.numerator, a.denominator);
		}
	}
	
	public String toString(){
		return this.numerator + "/" + this.denominator;
	}
	
	public static void main(String args[]){
		Fraction f1 = new Fraction(2, 3);
		Fraction f2 = new Fraction(1, 2);
		Fraction f3 = Fraction.multiply(f1, f2);
		System.out.println(f3);
	}

}
