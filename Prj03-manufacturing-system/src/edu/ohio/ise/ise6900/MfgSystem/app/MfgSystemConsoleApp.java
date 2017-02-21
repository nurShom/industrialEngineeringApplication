/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.app;

import java.util.Date;
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
		RECTANGLE, TRIANGLE, OBJECTS,			//to make draw -objects 
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
		commands.put("state", Command.STATE);
		
		commands.put("activities", Command.ACTIVITIES);
		commands.put("features", Command.FEATURES);
		commands.put("states", Command.STATES);
		
		commands.put("delete", Command.DELETE);
		commands.put("printout", Command.PRINTOUT);
		
		commands.put("jobs", Command.JOBS);
		commands.put("machines", Command.MACHINES);
		commands.put("system", Command.SYSTEM);
		
		commands.put("rectangle", Command.RECTANGLE);
		commands.put("traingle", Command.TRIANGLE);
		commands.put("objects", Command.OBJECTS);
		
		commands.put("exit", Command.EXIT);
		commands.put("quit", Command.QUIT);
		
		menu = "\nOptions : \n\t" + commands.keySet().toString() + "\nEnter the command:->";
	}

	public void run(){
		Scanner sc = new Scanner(System.in);
		StringTokenizer tokenizer;
		MfgSystem ms = new MfgSystem("ise6900");
//		MfgSystemConsoleApp.commands.
		boolean keepRunning = true;
		try{
			while (keepRunning) {
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
				case EXIT:
				case QUIT:
					keepRunning = false;
					printErr("Closing the application!");
					break;
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
				case JOBS:
					//jobs
					//lists all jobs in the system
					if(ms.countJobs()>0){
						ms.printJobs();	
					}
					else{
						printErr("System has no Job.");
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
				case FEATURES:
					//features jobName
					//lists all features under a job
					try{
						String jobName = tokenizer.nextToken();
						Job j = ms.findJob(jobName);
						j.listFeatures();
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Job for feature listing need to be specified!");
					}
					break;
				case ACTIVITY:
					//activity machineName jobName featureName startTime endTime
					//creates activity
					try{
						
						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						String jobName = tokenizer.nextToken();
						Job job = ms.findJob(jobName);
						String featureName = tokenizer.nextToken();
						MfgFeature feature = job.findFeature(featureName);
						Date startTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Date endTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Activity act = new Activity("act-"+jobName+"-"+featureName, 
								machine, job, feature, startTime, endTime);
						machine.addState(act);
						job.addActivity(act);
					} catch(AlreadyMemberException ame){
						printErr(ame.getMessage());
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Not enough activitiy parameters are specified!");
					} catch(NumberFormatException nsee){
						printErr("Only positive integers are allowed for start-time and end-time!");
					} catch (OverlappingStateException ose) {
						printErr(ose.getMessage());
					} catch (InvalidStateException ise) {
						printErr(ise.getMessage());
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
				case MACHINE:
					//machine machineName
					//creates machine
					try{
						String machineName = tokenizer.nextToken();
						ms.addMachine(new Machine(machineName));
					} catch(AlreadyMemberException ame){
						printErr(ame.getMessage());
					} catch(NumberFormatException mfe){
						printErr("Batch size needs to be an integer!");
					} catch(NoSuchElementException nsee){
						printErr("Not enough machine parameters are specified!");
					}
					break;
				case MACHINES:
					//machines
					//lists all machines in the system
					if(ms.countMachines()>0){
						ms.printMachines();	
					}
					else{
						printErr("System has no Machine.");
					}
					break;
				case STATE:
					//state stateName machineName state startTime endTime
					//creates activity
					try{
						String stateName = tokenizer.nextToken();
						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						String state = tokenizer.nextToken();
						StateType stype = StateType.findStateType(state); 
						Date startTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Date endTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						machine.addState(new MachineState(stateName, machine, stype, startTime, endTime));
					} catch(AlreadyMemberException ame){
						printErr(ame.getMessage());
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Not enough activitiy parameters are specified!");
					} catch (OverlappingStateException ose) {
						printErr(ose.getMessage());
					} catch (UnknownStateException use) {
						printErr(use.getMessage());
					} catch (InvalidStateException ise) {
						printErr(ise.getMessage());
					}
					break;
				case STATES:
					//states machineName
					//lists all states under a machine
					try{
						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						machine.listStates();
					} catch(UnknownObjectException uoe){
						printErr(uoe.getMessage());
					} catch(NoSuchElementException nsee){
						printErr("Machine for activity listing need to be specified!");
					}
					break;
				case SYSTEM:
					//system
					//prints mfg-system information
					ms.printout();
					break;
				case PRINTOUT:
					//printout job feature activity
					//printout machine state
					//printout job/machine
					try {
						int option = tokenizer.countTokens();
						//println("token left: " + option);
						if (option == 1) {
							String objectName = tokenizer.nextToken();
							try{
								ms.findJob(objectName).printout();
							} catch (UnknownObjectException uoe) {
								try{
									ms.findMachine(objectName).printout();
								} catch (UnknownObjectException uoe2) {
									printErr("No job or machine with name '" 
											+ objectName + "' exists!");
								}
							}
						}
						else if (option == 2) {
							AbstractState state = ms.findMachine(tokenizer.nextToken()).findState(tokenizer.nextToken());
							state.printout();
						}
						else if (option == 3) {
							Activity activity = ms.findJob(tokenizer.nextToken()).findActivity(tokenizer.nextToken());
							activity.printout();
						}
						else{
							printErr("Number of parameters for " 
									+ commandText.toUpperCase() + " should be 1, 2, or 3");
						}
					} catch (NoSuchElementException nsee) {
						printErr("Number of parameters for "  
								+ commandText.toUpperCase() + " should be 1, 2, or 3");
					} catch (UnknownObjectException uoe) {
						printErr(uoe.getMessage());
					}
					break;
				case DELETE:
					//delete job feature activity
					//delete machine state
					//delete job/machine
					try {
						int option = tokenizer.countTokens();
						//println("token left: " + option);
						if (option == 1) {
							String objectName = tokenizer.nextToken();
							try{
								ms.findJob(objectName).printout();
								ms.deleteJob(objectName);
								println("Job '" + objectName + "' deleted!");
							} catch (UnknownObjectException uoe) {
								try{
									ms.findMachine(objectName).printout();
									ms.deleteMachine(objectName);
									println("Machine '" + objectName + "' deleted!");
								} catch (UnknownObjectException uoe2) {
									printErr("No job or machine with name '" 
											+ objectName + "' exists!");
								}
							}
						}
						else if (option == 2) {
							Machine machine = ms.findMachine(tokenizer.nextToken());
							AbstractState state = machine.findState(tokenizer.nextToken());
							state.printout();
							machine.deleteState(state.getName());
							println("Machine-State " + state.getName() + " deleted!");
						}
						else if (option == 3) {
							Job job = ms.findJob(tokenizer.nextToken());
							Activity activity = job.findActivity(tokenizer.nextToken());
							activity.printout();
							job.deleteActivity(activity.getName());
							println("Activity " + activity.getName() + " deleted!");
						}
						else{
							printErr("Number of parameters for " 
									+ commandText.toUpperCase() + " should be 1, 2, or 3");
						}
					} catch (NoSuchElementException nsee) {
						printErr("Number of parameters for " 
								+ commandText.toUpperCase() + " should be 1, 2, or 3");
					} catch (UnknownObjectException uoe) {
						printErr(uoe.getMessage());
					} 
					break;
					
					
					
				default:
					printErr("Option '" + commandText + "' not yet implemnted!");
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
