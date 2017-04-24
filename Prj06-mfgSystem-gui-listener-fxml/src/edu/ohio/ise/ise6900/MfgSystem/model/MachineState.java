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
		return "MachineState "+ getName() 
				+ " (startTime=" + (getStartTime().getTime()/1000)
				+ ", endTime=" + (getEndTime().getTime()/1000) 
				+ ", machine=" + getMachine().getName() 
				+ ", stateType=" + getStateType().toString() + ")";
	}

	@Override
	public void write() {
		//state machineName state startTime endTime
		io.println("state"
				+ " " + this.getMachine().getName()
				+ " " + this.getStateType().toString().toUpperCase() 
				+ " " + (this.getStartTime().getTime()/1000) 
				+ " " + (this.getEndTime().getTime()/1000));
	}
	
}

