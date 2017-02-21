package edu.ohio.ise.ise6900.MfgSystem.model;
import java.util.Date;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;

public abstract class AbstractState extends MfgObject
{
	
	private Machine machine;
	private StateType stateType;
	private Date startTime;
	private Date endTime;

	public AbstractState(String name, Machine machine, StateType state, Date startTime, Date endTime) throws InvalidStateException{
		super(name);
		this.machine = machine;
		this.stateType = state;
		if(endTime.before(startTime)){
			throw new InvalidStateException("State "+name+" start-time must be before end-time!");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * @return the machine
	 */
	public Machine getMachine() {
		return machine;
	}
	/**
	 * @return the type
	 */
	public StateType getStateType() {
		return stateType;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractState "+ getName() + " (machine=" + machine + ", stateType=" + stateType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ")";
	}
}


