/**
 * 
 */
package com.horstmann.corejava.demostration;

import java.util.Date;

/**
 * @author
 *
 */
public class ObjectInitDemonstrator {

	private static int counter = 0;
	static {
		counter = readDatabase();
		System.out.println("-----\nin static init block\ncounter:" + counter + "\n-----\n");
	}

	String name;
	Date timestamp;
	private int id;
	
	{
		timestamp = new Date();
		System.out.println("in init block of " + this);
	}

	public ObjectInitDemonstrator() {
		this("Noname " + counter);
		System.out.println("in default constructor of " + this);
	}

	public ObjectInitDemonstrator(String name) {
		this.name = name;
		counter++;
		id = counter;
		System.out.println("in parametric constructor of " + this);
	}

	private static int readDatabase() {
		return 100000;
	}

	public String toString() {
		return name + ":" + id + "->" + timestamp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String names[] = { "dusan", "rasel", "mohammed", "najat", "shomik", "sanam", "can", "chris", "naga", "mandvi",
				"alec", "alejandro", "azadeh", "mahnoush", "xiaodan", "roohollah" };
		for (int i = 0; i < names.length; i++) {
			ObjectInitDemonstrator oid = new ObjectInitDemonstrator(names[i]);
			System.out.println("Object " + oid + "has been initiated.\n\n");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		for (int i = 0; i < 5; i++) {
			ObjectInitDemonstrator oid = new ObjectInitDemonstrator();
			System.out.println("Object " + oid + "has been initiated.\n\n");

			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

	}

}
