package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Date;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.InvalidStateException;

public class Activity extends AbstractState
{
	
	private Job job;
	private MfgFeature feature;
	
	public Activity(String name, Machine machine, Job job, MfgFeature feature, Date startTime, Date endTime) throws InvalidStateException{
		super(name, machine, StateType.BUSY, startTime, endTime);
		this.setJob(job);
		this.setFeature(feature);
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
		return "Activity " + getName() + " (job=" + job.getName() + ", feature=" + feature.getName() 
				+ ", machine=" + getMachine().getName() + ", stateType=" + getStateType().name() 
				+ ", startTime=" + getStartTime() + ", endTime=" + getEndTime() + ")";
	}

	

}

