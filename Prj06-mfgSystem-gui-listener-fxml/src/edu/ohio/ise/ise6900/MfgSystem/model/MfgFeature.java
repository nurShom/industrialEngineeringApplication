package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javafx.scene.shape.Shape;

public class MfgFeature extends MfgObject
{
	private Job job;
	private Collection<Activity> activities;

	public MfgFeature(String name, Job job){
		super(name);
		this.job = job;
		this.activities = new ArrayList<Activity>();
	}
	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}
	public Collection<Activity> getActivities() {
		return activities;
	}
	public void addActivity(Activity act) {
		this.activities.add(act);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Feature (name="+ getName() + ", job=" + job.getName() + ")";
	}
	
	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		for(Activity a : this.job.activities){
			if(a.getFeature() == this){
				shapes.addAll(a.makeShapes());
			}
		}
		return shapes;
	}
	public void deleteActivity(Activity a) {
		this.activities.remove(a);
	}
	@Override
	public void write() {
		io.println("feature " + this.getName() + " " + this.job.getName());
	}

}

