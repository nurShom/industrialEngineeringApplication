package edu.ohio.ise.ise6900.MfgSystem;

import java.util.Collection;

public class Job extends MfgObject
{
	
	public Collection<MfgFeature> features;
	public int batchSize;
	public Collection<Activity> activities;

	public Job(){
		super();
	}

}

