package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Date;

public class MachineState extends AbstractState
{
	
	public MachineState(String name, Machine machine, Date startTime, Date endTime){
		super(name, machine, startTime, endTime);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MachineState [Name = " + getName() + "]";
	}
	
}

