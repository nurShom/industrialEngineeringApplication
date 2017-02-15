package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.HashMap;
import java.util.Map;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.UnknownObjectException;

public class Machine extends MfgObject
{
	
	private Map<String, AbstractState> machineStates;

	public Machine(String name){
		super(name);
		this.machineStates = new HashMap<String, AbstractState>();
	}

	public void addState(MachineState ms) throws AlreadyMemberException {
		try{
			this.findState(ms.getName());
			throw new AlreadyMemberException("State '" + ms.getName() 
								+ "' is already in machine '" + this.getName() + "'.");
		}catch(UnknownObjectException uoe){
			machineStates.put(ms.getName(), ms);
		}		
	}
	
	public MachineState deleteState(String stateName) {
		return (MachineState) machineStates.remove(stateName);
	}
	
	public MachineState findState(String stateName) throws UnknownObjectException {
		MachineState state = (MachineState) machineStates.get(stateName);
		if(state == null){
			throw new UnknownObjectException("MachineState with name '" + stateName 
								+ "' does not exist in machine '" + this.getName() + "'.");
		}
		return state;
	}
	

	public void listStates(){
		this.printout();
		System.out.println("=>States:");
		for(AbstractState ms : this.machineStates.values()){
			ms.printout();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine [name=" + this.getName() 
				+ ", machineStates=" + machineStates + "]";
	}

}

