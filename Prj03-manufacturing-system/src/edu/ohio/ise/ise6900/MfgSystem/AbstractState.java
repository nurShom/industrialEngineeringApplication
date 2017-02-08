package edu.ohio.ise.ise6900.MfgSystem;
import java.util.Date;

public abstract class AbstractState
{
	
	public Machine machine;
	public String type;
	public Date startTime;
	public Date endTime;

	public AbstractState(){
		super();
	}

}

