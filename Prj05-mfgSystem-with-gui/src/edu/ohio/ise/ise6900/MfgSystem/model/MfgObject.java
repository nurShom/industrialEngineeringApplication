package edu.ohio.ise.ise6900.MfgSystem.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.ohio.ise.ise6900.MfgSystem.gui.DrawApplication;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;

public abstract class MfgObject implements Drawable {
	private String name;
	protected static DrawApplication da = new DrawApplication();
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

	protected static String getProperty(String propertyName, String oldValue) {
		return properties.getProperty(propertyName, oldValue); 
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MfgObject [name=" + name + "]";
	}

	public void printout() {
		// System.out.println(this.toString());
		MfgSystem.io.println(this.toString());
	}
	
	@Override
	public void display(String[] args) {
		this.display(args, "Mfg System with GUI TEST", 800, 600);
	}

	@Override
	public void display(String[] args, String title, double width, double height) {		
		da.getCanvas().getChildren().clear();
		da.getCanvas().clear();
		da.getCanvas().addTarget(this);
		
		try{
			da.main(args);
		} catch(Exception e){
			da.show();
		}
		
	}
	
	public static void main(String[] args) throws InvalidStateException {
		Machine m1 = new Machine("m1");
		m1.display(args);

	}

}
