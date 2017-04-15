package edu.ohio.ise.ise6900.MfgSystem.io;

import java.util.Scanner;

public class ConsoleIO extends AbstractIO {
	private Scanner scanner;
	
	public ConsoleIO() {
		scanner = new Scanner(System.in);
	}
	
	public void print(String text) {
		System.err.flush();
		System.out.print(text);
	}

	public void println(String text) {
		this.print(text+"\n");
	}

	public void printErr(String text) {
		System.out.flush();
		System.err.println(text);
	}
	
	public String readLine(){
		return scanner.nextLine();
	}

}
