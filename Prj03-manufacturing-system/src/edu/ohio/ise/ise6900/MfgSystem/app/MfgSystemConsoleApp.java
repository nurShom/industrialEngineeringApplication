/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.app;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.ohio.ise.ise6900.MfgSystem.model.MfgSystem;

/**
 * @author na551411
 *
 */
public class MfgSystemConsoleApp {

	enum Command{
		JOB, MACHINE, ACTIVITY, FEATURE, STATE, //to create objects
		ACTIVITIES, FEATURES, STATES,			//to report collection of a given object
		DELETE, PRINTOUT,						//to delete or printout an individual object
		JOBS, MACHINES, SYSTEM,					//to report system state and collection
		RECTANGLE, TRIANGLE,					//to make draw -objects 
		EXIT, QUIT								//to exit the application
	}
	static SortedMap<String, Command> commands;
	static String menu;
	
	static{
		commands = new TreeMap<String, Command>();
		commands.put("job", Command.JOB);
		commands.put("machine", Command.MACHINE);
		commands.put("activity", Command.ACTIVITY);
		commands.put("feature", Command.FEATURE);
		commands.put("machine-state", Command.STATE);
		
		commands.put("delete", Command.DELETE);
		commands.put("printout", Command.PRINTOUT);
		
		commands.put("jobs", Command.JOBS);
		
		commands.put("rectangle", Command.RECTANGLE);
		commands.put("traingle", Command.TRIANGLE);
		
		menu = "\nOptions : \n\t" + commands.keySet().toString() + "\nEnter the command:->";
	}

	public void run(){
		Scanner sc = new Scanner(System.in);
		StringTokenizer tokenizer;
		MfgSystem ms = new MfgSystem("ise6900");
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
