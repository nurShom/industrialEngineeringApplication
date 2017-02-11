/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.app;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.ohio.ise.ise6900.MfgSystem.model.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.*;

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
		
		try{
			while (true) {
				System.err.flush();
				print(menu);

				String input = sc.nextLine();
				println("Input is: " + input);

				tokenizer = new StringTokenizer(input);
				String commandText;
				try {
					commandText = tokenizer.nextToken();
				} catch (Exception e) {
					printErr("No input specified.");
					continue;
				}
				Command cmd = commands.get(commandText.toLowerCase());
				if(cmd == null){
					printErr("Your command '" + commandText + "' is not supported.");
					continue;
				}
				switch(cmd){
				case JOB:
					//job jobName batchSize
					//creates job
					try{
						String jobName = tokenizer.nextToken();
						int batchSize = Integer.parseInt(tokenizer.nextToken());
						ms.addJob(new Job(jobName, batchSize));
					} catch(AlreadyMemberException ame){
						printErr(ame.getMessage());
					} catch(NumberFormatException mfe){
						printErr("Batch size needs to be an integer!");
					} catch(NoSuchElementException nsee){
						printErr("Not enough job parameters are specified!");
					}
					break;
				case FEATURE:
					//feature featureName jobName
					//creates feature
					try{
						String featureName = tokenizer.nextToken();
						String jobName = tokenizer.nextToken();
						Job job = ms.findJob(jobName);
						job.addFeature(new MfgFeature(featureName));
					} catch(AlreadyMemberException ame){
						printErr(ame.getMessage());
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Not enough feature parameters are specified!");
					}
					break;
				case ACTIVITIES:
					//activities jobName
					//lists all activities under a job
					try{
						String jobName = tokenizer.nextToken();
						Job j = ms.findJob(jobName);
						j.listActivities();
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Job for activity listing need to be specified!");
					}
					break;
					
					
					
					
					
					
				default:
					break;
				}

			}
		} catch(NoSuchElementException nse){
			nse.printStackTrace();
		}
		sc.close();		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MfgSystemConsoleApp mscApp = new MfgSystemConsoleApp();
		mscApp.run();
	}
	
	public void print(String text){
		System.out.print(text);
	}
	public void println(String text){
		System.out.println(text);
	}
	public void printErr(String text){
		System.err.println(text);
	}
	
}
