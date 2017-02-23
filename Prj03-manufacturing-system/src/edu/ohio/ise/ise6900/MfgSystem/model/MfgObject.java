package edu.ohio.ise.ise6900.MfgSystem.model;

import edu.ohio.ise.ise6900.MfgSystem.io.*;

public abstract class MfgObject
{
	
	private String name;
	protected static AbstractIO io;
	static{
		MfgObject.io = new ConsolIO();
	}

	public MfgObject(String name){
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public static void setIO(AbstractIO io) {
		MfgObject.io = io;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MfgObject [name=" + name + "]";
	}
	
	public void printout(){
		//System.out.println(this.toString());
		MfgObject.io.println(this.toString());
	}

}

