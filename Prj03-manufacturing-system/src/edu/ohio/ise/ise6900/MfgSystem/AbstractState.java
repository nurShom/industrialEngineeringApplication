package edu.ohio.ise.ise6900.MfgSystem;
import java.util.Date;

public abstract class AbstractState
{
	
	private Machine machine;
	private StateType type;
	private Date startTime;
	private Date endTime;

	public AbstractState(){
		super();
	}

	/**
	 * @return the machine
	 */
	public Machine getMachine() {
		return machine;
	}

	/**
	 * @param machine the machine to set
	 */
	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	/**
	 * @return the type
	 */
	public StateType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(StateType type) {
		this.type = type;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}


