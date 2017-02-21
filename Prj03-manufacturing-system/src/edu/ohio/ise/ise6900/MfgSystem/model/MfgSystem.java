package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ohio.ise.ise6900.MfgSystem.geometry.DrawObject;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.AlreadyMemberException;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.UnknownObjectException;

public class MfgSystem extends MfgObject
{
	private Map<String, Job> jobs;
	private Map<String, Machine> machines;
	private ArrayList<DrawObject> objects;

	public MfgSystem(String name){
		super(name);
		this.jobs = new HashMap<String, Job>();
		this.machines = new HashMap<String, Machine>();
	}
	
	public void addJob(Job j) throws AlreadyMemberException{
		try{
			this.findJob(j.getName());
			throw new AlreadyMemberException("Job '" + j.getName() + "' is already in the mfg system.");
		}catch(UnknownObjectException uoe){
			jobs.put(j.getName(), j);
		}
	}
	
	public Job deleteJob(String jobName) {
		return jobs.remove(jobName);
	}
	
	public Job findJob(String jobName) throws UnknownObjectException {
		Job job = jobs.get(jobName);
		if(job == null){
			throw new UnknownObjectException("Job with name '" + jobName + "' does not exist.");
		}
		return job;
	}
	
	public int countJobs(){
		return this.jobs.size();
	}
	
	public void printJobs(){
		for(Job j : this.jobs.values()){
			j.printout();
		}
	}
	
	public void addMachine(Machine m) throws AlreadyMemberException{
		try{
			this.findMachine(m.getName());
			throw new AlreadyMemberException("Machine '" + m.getName() + "' is already in the mfg system.");
		} catch (UnknownObjectException uoe){
			machines.put(m.getName(), m);
		}
	}
	
	public Machine deleteMachine(String machineName) {
		return machines.remove(machineName);
	}
	
	public Machine findMachine(String machineName) throws UnknownObjectException {
		Machine machine = machines.get(machineName);
		if(machine == null){
			throw new UnknownObjectException("Machine with name '" + machineName + "' does not exist.");
		}
		return machine;
	}
	
	public int countMachines(){
		return this.machines.size();
	}
	
	public void printMachines(){
		for(Machine m : this.machines.values()){
			m.printout();
		}
	}
	

	
	public void addDrawObject(DrawObject o) throws AlreadyMemberException{
		objects.add(o);
	}
	
	public DrawObject deleteDrawObject(DrawObject o) {
		return objects.remove(objects.indexOf(o));
	}
	
	public int countDrawObjects(){
		return this.objects.size();
	}
	
	public void printDrawObjects(){
		for(DrawObject obj : this.objects){
			obj.printout();
		}
	}
	
	@Override
	public String toString() {
		return "MfgSystem '" + this.getName() + "' contains " + jobs.size() + " jobs and " + machines.size() + " machines.";
	}
	
	
	
}

