package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.Map;

public class MfgSystem extends MfgObject
{
	public Map<String, Job> jobs;
	public Map<String, Machine> machines;

	public MfgSystem(String name){
		super(name);
	}

}

