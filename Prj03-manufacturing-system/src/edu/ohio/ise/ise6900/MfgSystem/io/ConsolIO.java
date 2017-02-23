package edu.ohio.ise.ise6900.MfgSystem.io;

import java.util.Scanner;

public class ConsolIO extends AbstractIO {
	private Scanner scanner;
	
	public ConsolIO() {
		scanner = new Scanner(System.in);
	}
	
	public void print(String text) {
		System.out.print(text);
	}

	public void println(String text) {
		System.out.println(text);
	}

	public void printErr(String text) {
		System.err.println(text);
	}
	
	public String readLine(){
		return scanner.nextLine();
	}

}
