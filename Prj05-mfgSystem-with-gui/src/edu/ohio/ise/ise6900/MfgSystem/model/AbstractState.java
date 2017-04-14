package edu.ohio.ise.ise6900.MfgSystem.model;
import java.util.Date;
import java.util.LinkedList;

import edu.ohio.ise.ise6900.MfgSystem.gui.DrawApplication;
import edu.ohio.ise.ise6900.MfgSystem.gui.draw.DrawPanel;
import edu.ohio.ise.ise6900.MfgSystem.gui.test.DrawableTester;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
//abstract 
public class AbstractState extends MfgObject implements Comparable<AbstractState>
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractState o) {
		return this.startTime.compareTo(o.startTime);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((machine == null) ? 0 : machine.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractState) || !(obj instanceof MachineState)) {
			return false;
		}
		AbstractState other = (AbstractState) obj;
		if (machine == null) {
			if (other.machine != null) {
				return false;
			}
		} else if (!machine.equals(other.machine)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		return true;
	}

	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		Rectangle machineState = 
				new Rectangle(40 + OFFSET + SCALE * (this.startTime.getTime()/1000),
							OFFSET + SCALE * this.machine.getLevel(), 
							SCALE * ((this.endTime.getTime() - this.startTime.getTime())/1000), 
							SCALE * HEIGHT
						);
		machineState.setFill(this.stateType.getColor());
		machineState.setStroke(this.stateType.getColor());
		machineState.setStrokeWidth(2);
		shapes.add(machineState);
		return shapes;
	}
	
	public static void main(String[] args) throws InvalidStateException {
		Machine m1 = new Machine("m1");
		AbstractState as = new AbstractState("state1", m1, StateType.BUSY, new Date(100), new Date(200));
		as.display(args);

	}
}


