package edu.ohio.ise.ise6900.MfgSystem;

import java.util.Collection;

public class Machine extends MfgObject
{
	
	public Collection<AbstractState> machineStates;
	public MfgSystem mfgSystem;
	public MachineState machineState;

	public Machine(){
		super();
	}

}

