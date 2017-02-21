package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.HashMap;
import java.util.Map;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.*;

public class Job extends MfgObject
{
	public int batchSize;
	public Map<String, MfgFeature> features;
	public Map<String, Activity> activities;

	public Job(String name, int batchSize){
		super(name);
		this.batchSize = batchSize;
		this.features = new HashMap<String, MfgFeature>();
		this.activities = new HashMap<String, Activity>();
	}

	public void addFeature(MfgFeature f) throws AlreadyMemberException {
		try{
			this.findFeature(f.getName());
			throw new AlreadyMemberException("Feature '" + f.getName() 
								+ "' is already in job '" + this.getName() + "'.");
		}catch(UnknownObjectException uoe){
			features.put(f.getName(), f);
		}		
	}
	
	public MfgFeature findFeature(String featureName) throws UnknownObjectException {
		MfgFeature feature = features.get(featureName);
		if(feature == null){
			throw new UnknownObjectException("Feature with name '" + featureName 
								+ "' does not exist in job '" + this.getName() + "'.");
		}
		return feature;
	}
	

	public void listFeatures(){
		this.printout();
		System.out.println("=>Features:");
		for(MfgFeature f : this.features.values()){
			f.printout();
		}
	}
	

	public void addActivity(Activity a) throws AlreadyMemberException {
		try{
			this.findActivity(a.getName());
			throw new AlreadyMemberException("Activity '" + a.getName() 
								+ "' is already in job '" + this.getName() + "'.");
		}catch(UnknownObjectException uoe){
			activities.put(a.getName(), a);
		}		
	}
	
	public Activity deleteActivity(String activityName) {
		return activities.remove(activityName);
	}
	
	public Activity findActivity(String activityName) throws UnknownObjectException {
		Activity activity = activities.get(activityName);
		if(activity == null){
			throw new UnknownObjectException("Activity with name '" + activityName 
								+ "' does not exist in job '" + this.getName() + "'.");
		}
		return activity;
	}
	
	public void listActivities(){
		this.printout();
		System.out.println("=> Activities:");
		for(Activity a : this.activities.values()){
			a.printout();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Job " + this.getName() + ", batchSize=" + batchSize;
	}


}

