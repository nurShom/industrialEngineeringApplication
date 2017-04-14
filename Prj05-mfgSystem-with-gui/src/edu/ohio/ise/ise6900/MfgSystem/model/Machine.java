package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.OverlappingStateException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class Machine extends MfgObject
{
	private ArrayList<AbstractState> machineStates;

	double level = Double.parseDouble(super.getProperty(this.getClass().getName() + "." + this.getName(), "100"));
	{
		System.err.println("Machine " + this.getName() + " level: " + this.level);
	}
	
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
					|| (start.equals(state.getStartTime()) 
							&& (end.equals(state.getEndTime()) || end.after(state.getEndTime()))
						)
					|| (end.equals(state.getEndTime()) 
							&& (start.equals(state.getStartTime()) || start.before(state.getStartTime()))
						)
				) {
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
		MfgSystem.io.println("=>States:");
		for(AbstractState ms : this.getMachineStatesSortedByStartTime()){
			ms.printout();
		}
	}
	
	public int findState(AbstractState searchState) throws InvalidStateException {
		for(AbstractState state : machineStates){
			if(state.equals(searchState)){
				return machineStates.indexOf(state);
			}
		}
		return -1;
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

	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		Text machineLabel = new Text(SCALE * 10, 20 + OFFSET + SCALE * this.level, this.getName());
		machineLabel.setStroke(Color.MEDIUMBLUE);
		machineLabel.setFill(Color.BEIGE);
		shapes.add(machineLabel);
		for(AbstractState as : this.getMachineStatesSortedByStartTime()){
			shapes.addAll(as.makeShapes());
		}
		return shapes;
	}
	
	public double getLevel() {
		return level;
	}

}

