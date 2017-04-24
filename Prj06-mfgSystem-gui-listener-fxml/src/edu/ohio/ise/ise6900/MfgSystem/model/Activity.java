package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Date;
import java.util.LinkedList;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;
import javafx.scene.shape.Shape;

public class Activity extends AbstractState
{
	
	private Job job;
	private MfgFeature feature;
	
	public Activity(String name, Machine machine, Job job, MfgFeature feature, Date startTime, Date endTime) throws InvalidStateException{
		super(name, machine, StateType.BUSY, startTime, endTime);
		this.job = job;
		this.feature = feature;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * @return the feature
	 */
	public MfgFeature getFeature() {
		return feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(MfgFeature feature) {
		this.feature = feature;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Activity " + getName()  
				+ "(startTime=" + (getStartTime().getTime()/1000) 
				+ ", endTime=" + (getEndTime().getTime()/1000)
				+ ", job=" + job.getName() + ", feature=" + feature.getName() 
				+ ", machine=" + getMachine().getName() 
				+ ", stateType=" + getStateType().name() + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
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
		if (!(obj instanceof Activity)) {
			return false;
		}
		Activity other = (Activity) obj;
		if (feature == null) {
			if (other.feature != null) {
				return false;
			}
		} else if (!feature.equals(other.feature)) {
			return false;
		}
		if (job == null) {
			if (other.job != null) {
				return false;
			}
		} else if (!job.equals(other.job)) {
			return false;
		}
		return true;
	}


	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = super.makeShapes();
		for(Shape shape : shapes){
			shape.setStroke(this.job.color);
			shape.setStrokeWidth(2);
		}
		return shapes;
	}

	@Override
	public void write() {
		// activity machineName jobName featureName startTime endTime
		io.println("activity" 
				+ " " + this.getMachine().getName() 
				+ " " + this.job.getName() 
				+ " " + this.feature.getName()
				+ " " + (this.getStartTime().getTime()/1000) 
				+ " " + (this.getEndTime().getTime()/1000));
	}
}

