package edu.ohio.ise.ise6900.MfgSystem.model;

public abstract class MfgObject
{
	
	public String name;

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

}

