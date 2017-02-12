package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Collection;

public class Machine extends MfgObject
{
	
	public Collection<AbstractState> machineStates;
	public MachineState machineState;

	public Machine(String name){
		super(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine [name=" + this.getName() + ", Machine State=" + machineState 
				+ ", machineStates=" + machineStates + "]";
	}

}

