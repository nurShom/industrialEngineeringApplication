package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Collection;

public class Machine extends MfgObject
{
	
	public Collection<AbstractState> machineStates;
	public MfgSystem mfgSystem;
	public MachineState machineState;

	public Machine(String name){
		super(name);
	}

}

