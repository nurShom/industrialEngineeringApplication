package edu.ohio.ise.ise6900.MfgSystem.model;

public class Activity extends AbstractState
{
	
	public Job job;
	public MfgFeature feature;

	public Activity(String name, Machine m, Job job, MfgFeature feature){
		super(name);
		this.setType(StateType.BUSY);
	}

}

