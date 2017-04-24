package edu.ohio.ise.ise6900.MfgSystem.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.ohio.ise.ise6900.MfgSystem.gui.controller.MfgSystemViewer;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;

public abstract class MfgObject implements Drawable {
	private String name;
//	protected static MfgSystemViewer da = new MfgSystemViewer();
	protected static double OFFSET = 0.0;
	protected static double SCALE = 1.0;
	protected static double HEIGHT = 10.0;
	protected static Properties properties;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("mfgsystem.properties")));
			SCALE = Double.parseDouble(getProperty("SCALE", Double.toString(SCALE)));
			OFFSET = Double.parseDouble(getProperty("OFFSET", Double.toString(OFFSET)));
			HEIGHT = Double.parseDouble(getProperty("HEIGHT", Double.toString(HEIGHT)));
		} catch (FileNotFoundException fne) {

		} catch (IOException e) {

		}
	}
	
	public MfgObject(String name) {
		super();
		this.name = name;
	}


	public static double getSCALE() {
		return SCALE;
	}
	public static void setSCALE(double sCALE) {
		SCALE = sCALE;
	}
	public static double getHEIGHT() {
		return HEIGHT;
	}
	public static void setHEIGHT(double hEIGHT) {
		HEIGHT = hEIGHT;
	}
	protected static String getProperty(String propertyName, String oldValue) {
		return properties.getProperty(propertyName, oldValue); 
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "MfgObject [name=" + name + "]";
	}

	public void printout() {
		// System.out.println(this.toString());
		MfgSystem.io.println(this.toString());
	}
	
	public static void main(String[] args) {
		
	}

}
