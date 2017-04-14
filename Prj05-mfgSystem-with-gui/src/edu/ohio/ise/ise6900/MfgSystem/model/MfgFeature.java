package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.LinkedList;

import javafx.scene.shape.Shape;

public class MfgFeature extends MfgObject
{
	private Job job;

	public MfgFeature(String name, Job job){
		super(name);
		this.job = job;
	}
	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
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

}

