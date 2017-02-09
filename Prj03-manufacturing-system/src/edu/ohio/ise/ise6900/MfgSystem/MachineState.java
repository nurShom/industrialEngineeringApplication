package edu.ohio.ise.ise6900.MfgSystem;

public class MachineState extends AbstractState
{


	private StateType type;

	public MachineState(){
		super();
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

}

