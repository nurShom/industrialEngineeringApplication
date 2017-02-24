package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Date;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;

public class MachineState extends AbstractState
{
	
	public MachineState(String name, Machine machine, StateType state, Date startTime, Date endTime) throws InvalidStateException{
		super(name, machine, state, startTime, endTime);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MachineState "+ getName() + " (machine=" + getMachine().getName() 
				+ ", stateType=" + getStateType().toString() + 
				", startTime=" + (getStartTime().getTime()/1000)
				+ ", endTime=" + (getEndTime().getTime()/1000) + ")";
	}
	
}

