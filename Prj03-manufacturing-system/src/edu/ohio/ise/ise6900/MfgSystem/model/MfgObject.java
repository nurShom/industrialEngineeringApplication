package edu.ohio.ise.ise6900.MfgSystem.model;

public abstract class MfgObject
{
	
	private String name;

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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MfgObject [name=" + name + "]";
	}
	
	public void printout(){
		//System.out.println(this.toString());
		MfgSystem.io.println(this.toString());
	}

}

