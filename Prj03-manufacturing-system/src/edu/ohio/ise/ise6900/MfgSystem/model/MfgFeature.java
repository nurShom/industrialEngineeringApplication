package edu.ohio.ise.ise6900.MfgSystem.model;

public class MfgFeature extends MfgObject
{
	
	private String memberName;
	private Job job;

	public MfgFeature(String name){
		super(name);
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Feature [name="+ getName() + ", memberName=" + memberName + ", job=" + job + "]";
	}

}

