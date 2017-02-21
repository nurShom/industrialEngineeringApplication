package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.OverlappingStateException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.UnknownObjectException;

public class Machine extends MfgObject
{
	
	private HashMap<String, AbstractState> machineStates;

	public Machine(String name){
		super(name);
		this.machineStates = new HashMap<String, AbstractState>();
	}

	public void addState(AbstractState ms) throws AlreadyMemberException, OverlappingStateException {
		try{
			this.findState(ms.getName());
			throw new AlreadyMemberException("State '" + ms.getName() 
								+ "' is already in machine '" + this.getName() + "'.");
		}catch(UnknownObjectException uoe){
			Date start = ms.getStartTime();
			Date end = ms.getEndTime();
			//Checks if this state overlaps with existing states
			for(AbstractState state : this.machineStates.values()){
				if((start.after(state.getStartTime()) && start.before(state.getEndTime()))
					|| (end.after(state.getStartTime()) && end.before(state.getEndTime()))
					|| start.equals(state.getStartTime()) || end.equals(state.getStartTime()) 
					||	start.equals(state.getEndTime()) || end.equals(state.getEndTime())
					){
					throw new OverlappingStateException(ms.getName() 
								+ " (" + ms.getStartTime().getTime()/1000 + ", " 
								+ ms.getEndTime().getTime()/1000 
								+ ") overlaps with " + state.getName()
								+ " (" + state.getStartTime().getTime()/1000 
								+ ", " + state.getEndTime().getTime()/1000
								+ ")");
				}				
			}
			machineStates.put(ms.getName(), ms);
		}		
	}
	
	public AbstractState deleteState(String stateName) {
		return machineStates.remove(stateName);
	}
	
	public AbstractState findState(String stateName) throws UnknownObjectException {
		AbstractState state = machineStates.get(stateName);
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
	
	public Map<String, AbstractState> getMachineStatesSortedByStartTime(){
		Comparator<String> valueComparator =  new Comparator<String>() {
	        public int compare(String k1, String k2) {
	            int compare = machineStates.get(k2).getStartTime().compareTo(machineStates.get(k1).getStartTime());
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    Map<String, AbstractState> sortedByValues = new TreeMap<String, AbstractState>(valueComparator);
	    sortedByValues.putAll(machineStates);
	    return sortedByValues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine " + this.getName();
	}

}

