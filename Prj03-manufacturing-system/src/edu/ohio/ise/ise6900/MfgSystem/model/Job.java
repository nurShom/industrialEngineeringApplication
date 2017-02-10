package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Collection;

public class Job extends MfgObject
{
	
	public Collection<MfgFeature> features;
	public int batchSize;
	public Collection<Activity> activities;

	public Job(String name){
		super(name);
	}

}

