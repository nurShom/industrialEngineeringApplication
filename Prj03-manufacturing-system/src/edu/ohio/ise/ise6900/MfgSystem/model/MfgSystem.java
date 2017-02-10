package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Collection;

public class MfgSystem extends MfgObject
{
	public Collection<Job> jobs;
	public Collection<Machine> machines;
	public Job job;
	public Machine machine;

	public MfgSystem(){
		super();
	}

}

