package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Date;

public class Activity extends AbstractState
{
	
	private Job job;
	private MfgFeature feature;

	public Activity(String name, Machine machine, Job job, MfgFeature feature){
		this(name, machine, job, feature, new Date(), new Date());
	}
	
	public Activity(String name, Machine machine, Job job, MfgFeature feature, Date startTime, Date endTime){
		super(name, machine, startTime, endTime);
		this.setType(StateType.BUSY);
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
		return "Activity [name=" + getName() + ", job=" + job + ", feature=" + feature + ", ]";
	}

	

}

