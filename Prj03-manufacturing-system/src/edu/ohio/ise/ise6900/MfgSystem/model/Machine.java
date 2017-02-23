package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.OverlappingStateException;

public class Machine extends MfgObject
{
	
	private ArrayList<AbstractState> machineStates;

	public Machine(String name){
		super(name);
		this.machineStates = new ArrayList<AbstractState>();
	}

	public void addState(AbstractState ms) throws AlreadyMemberException, OverlappingStateException {
		Date start = ms.getStartTime();
		Date end = ms.getEndTime();
		// Checks if this state overlaps with existing states
		for (AbstractState state : this.machineStates) {
			if ((start.after(state.getStartTime()) && start.before(state.getEndTime()))
					|| (end.after(state.getStartTime()) && end.before(state.getEndTime()))
					|| start.equals(state.getStartTime()) || end.equals(state.getStartTime())
					|| start.equals(state.getEndTime()) || end.equals(state.getEndTime())) {
				throw new OverlappingStateException(ms.getName() + " (" + ms.getStartTime().getTime() / 1000 + ", "
						+ ms.getEndTime().getTime() / 1000 + ") overlaps with " + state.getName() + " ("
						+ state.getStartTime().getTime() / 1000 + ", " + state.getEndTime().getTime() / 1000 + ")");
			}
		}
		machineStates.add(ms);

	}
	
	public boolean deleteState(AbstractState state) {
		for(AbstractState st : machineStates){
			if(st.equals(state)){
				return machineStates.remove(st);
			}
		}
		return false;
	}
	
	public void listStates(){
		this.printout();
		MfgObject.io.println("=>States:");
		for(AbstractState ms : this.machineStates){
			ms.printout();
		}
	}
	
	public ArrayList<AbstractState> getMachineStatesSortedByStartTime(){
		ArrayList<AbstractState> sortedStates = new ArrayList<AbstractState>(this.machineStates);
		Collections.sort(sortedStates);
		return sortedStates;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine " + this.getName();
	}

}

