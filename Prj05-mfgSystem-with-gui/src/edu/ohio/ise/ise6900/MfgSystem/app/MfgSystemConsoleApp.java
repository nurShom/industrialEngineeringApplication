/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.ohio.ise.ise6900.MfgSystem.geometry.*;
import edu.ohio.ise.ise6900.MfgSystem.io.*;
import edu.ohio.ise.ise6900.MfgSystem.model.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.*;

/**
 * @author na551411
 *
 */
public class MfgSystemConsoleApp {
	
	AbstractIO io;

	enum Command {
		JOB, MACHINE, ACTIVITY, FEATURE, STATE, // to create objects
		ACTIVITIES, FEATURES, STATES, // to report collection of a given object
		DELETE, PRINTOUT, // to delete or printout an individual object
		JOBS, MACHINES, SYSTEM, // to report system state and collection
		RECTANGLE, TRIANGLE, OBJECTS, // to make draw -objects
		EXIT, QUIT // to exit the application
	}

	static SortedMap<String, Command> commands;
	static String menu;

	static {
		commands = new TreeMap<String, Command>();
		commands.put("job", Command.JOB);
		commands.put("machine", Command.MACHINE);
		commands.put("activity", Command.ACTIVITY);
		commands.put("feature", Command.FEATURE);
		commands.put("machine-state", Command.STATE);
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
	
	public void setIO(AbstractIO io){
		this.io = io;
	}

	public void run() {
		try {
			MfgSystem ms = new MfgSystem("ise6900");
			boolean keepRunning = true;
			StringTokenizer tokenizer;
			while (keepRunning) {
				System.out.print(menu);

				String input = io.readLine();
				io.println("\nInput is: " + input);

				tokenizer = new StringTokenizer(input);
				String commandText;
				try {
					commandText = tokenizer.nextToken();
				} catch (Exception e) {
					io.printErr("No input specified.");
					continue;
				}
				Command cmd = commands.get(commandText.toLowerCase());
				if (cmd == null) {
					io.printErr("Your command '" + commandText + "' is not supported.");
					continue;
				}
				switch (cmd) {
				case EXIT:
				case QUIT:
					io.printErr("Closing the application!");
					keepRunning = false;
					break;
				case JOB:
					// job jobName batchSize
					// creates job
					try {
						String jobName = tokenizer.nextToken();
						int batchSize = Integer.parseInt(tokenizer.nextToken());
						ms.addJob(new Job(jobName, batchSize));
					} catch (AlreadyMemberException ame) {
						io.printErr(ame.getMessage());
					} catch (NumberFormatException mfe) {
						io.printErr("Batch size needs to be an integer!");
					} catch (NoSuchElementException nsee) {
						io.printErr("Not enough job parameters are specified!");
					}
					break;
				case JOBS:
					// jobs
					// lists all jobs in the system
					if (ms.countJobs() > 0) {
						ms.printJobs();
					} else {
						io.printErr("System has no Job.");
					}
					break;
				case FEATURE:
					// feature featureName jobName
					// creates feature
					try {
						String featureName = tokenizer.nextToken();
						String jobName = tokenizer.nextToken();
						Job job = ms.findJob(jobName);
						job.addFeature(new MfgFeature(featureName, job));
					} catch (AlreadyMemberException ame) {
						io.printErr(ame.getMessage());
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (NoSuchElementException nsee) {
						io.printErr("Not enough feature parameters are specified!");
					}
					break;
				case FEATURES:
					// features jobName
					// lists all features under a job
					try {
						String jobName = tokenizer.nextToken();
						Job j = ms.findJob(jobName);
						j.listFeatures();
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (NoSuchElementException nsee) {
						io.printErr("Job for feature listing need to be specified!");
					}
					break;
				case ACTIVITY:
					// activity machineName jobName featureName startTime endTime
					// creates activity
					try {

						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						String jobName = tokenizer.nextToken();
						Job job = ms.findJob(jobName);
						String featureName = tokenizer.nextToken();
						MfgFeature feature = job.findFeature(featureName);
						Date startTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Date endTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						String actName = jobName + "-" + featureName + "-" + (startTime.getTime()/1000);
						Activity act = new Activity(actName, machine, job, feature, startTime, endTime);
						machine.addState(act);
						job.addActivity(act);
					} catch (AlreadyMemberException ame) {
						io.printErr(ame.getMessage());
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (NoSuchElementException nsee) {
						io.printErr("Not enough activitiy parameters are specified!");
					} catch (NumberFormatException nsee) {
						io.printErr("Only positive integers are allowed for start-time and end-time!");
					} catch (OverlappingStateException ose) {
						io.printErr(ose.getMessage());
					} catch (InvalidStateException ise) {
						io.printErr(ise.getMessage());
					}
					break;
				case ACTIVITIES:
					// activities jobName
					// lists all activities under a job
					try {
						String jobName = tokenizer.nextToken();
						Job j = ms.findJob(jobName);
						j.listActivities();
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (NoSuchElementException nsee) {
						io.printErr("Job for activity listing need to be specified!");
					}
					break;
				case MACHINE:
					// machine machineName
					// creates machine
					try {
						String machineName = tokenizer.nextToken();
						ms.addMachine(new Machine(machineName));
					} catch (AlreadyMemberException ame) {
						io.printErr(ame.getMessage());
					} catch (NumberFormatException mfe) {
						io.printErr("Batch size needs to be an integer!");
					} catch (NoSuchElementException nsee) {
						io.printErr("Not enough machine parameters are specified!");
					}
					break;
				case MACHINES:
					// machines
					// lists all machines in the system
					if (ms.countMachines() > 0) {
						ms.printMachines();
					} else {
						io.printErr("System has no Machine.");
					}
					break;
				case STATE:
					// state stateName machineName state startTime endTime
					// creates activity
					try {
						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						String state = tokenizer.nextToken();
						StateType stype = StateType.findStateType(state);
						Date startTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Date endTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						String stateName = machineName+"-"+stype+"-"+(startTime.getTime()/1000);
						machine.addState(new MachineState(stateName, machine, stype, startTime, endTime));
					} catch (AlreadyMemberException ame) {
						io.printErr(ame.getMessage());
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (OverlappingStateException ose) {
						io.printErr(ose.getMessage());
					} catch (UnknownStateException use) {
						io.printErr(use.getMessage());
					} catch (InvalidStateException ise) {
						io.printErr(ise.getMessage());
					}
					break;
				case STATES:
					// states machineName
					// lists all states under a machine
					try {
						String machineName = tokenizer.nextToken();
						Machine machine = ms.findMachine(machineName);
						machine.listStates();
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (NoSuchElementException nsee) {
						io.printErr("Machine for activity listing need to be specified!");
					}
					break;
				case SYSTEM:
					// system
					// prints mfg-system information
					ms.printout();
					break;
				case PRINTOUT:
					/* activity: printout machine job feature
					 * feature: printout job feature
					 * job: printout job
					 * machine: printout machine 
					 */
					try {
						int option = tokenizer.countTokens();
						if (option == 1) {
							// job or machine
							String objectName = tokenizer.nextToken();
							try {
								ms.findJob(objectName).printout();
							} catch (UnknownObjectException uoe) {
								try {
									ms.findMachine(objectName).printout();
								} catch (UnknownObjectException uoe2) {
									io.printErr("No job or machine with name '" + objectName + "' exists!");
								}
							}
						} else if (option == 2) {
							// feature
							MfgFeature feature = ms.findJob(tokenizer.nextToken()).findFeature(tokenizer.nextToken());
							feature.printout();
						} else if (option == 3) {
							// activity
							tokenizer.nextToken();
							Job job = ms.findJob(tokenizer.nextToken());
							MfgFeature feature = job.findFeature(tokenizer.nextToken());
							Activity activity = job.findActivity(feature);
							activity.printout();
						} else {
							io.printErr("Number of parameters for " + commandText.toUpperCase() + " should be 1, 2, or 3");
						}
					} catch (NoSuchElementException nsee) {
						io.printErr("Number of parameters for " + commandText.toUpperCase() + " should be 1, 2, or 3");
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (InvalidStateException ise) {
						io.printErr(ise.getMessage());
					}
					break;
				case DELETE:
					/* activity: printout machine job feature
					 * feature: printout job feature
					 * job: printout job
					 * machine: printout machine 
					 */
					try {
						int option = tokenizer.countTokens();
						if (option == 1) {
							// job or machine
							String objectName = tokenizer.nextToken();
							try {
								ms.findJob(objectName).printout();
								ms.deleteJob(objectName);
								io.println("Job '" + objectName + "' deleted!");
							} catch (UnknownObjectException uoe) {
								try {
									ms.findMachine(objectName).printout();
									ms.deleteMachine(objectName);
									io.println("Machine '" + objectName + "' deleted!");
								} catch (UnknownObjectException uoe2) {
									io.printErr("No job or machine with name '" + objectName + "' exists!");
								}
							}
						} else if (option == 2) {
							// feature
							Job job = ms.findJob(tokenizer.nextToken());
							String featureName = tokenizer.nextToken();
							job.deleteFeature(featureName);
							io.println("Feature " + featureName + " (in Job " 
										+ job.getName() + ") deleted!");
						} else if (option == 3) {
							// activity
							Machine machine = ms.findMachine(tokenizer.nextToken());
							Job job = ms.findJob(tokenizer.nextToken());
							MfgFeature feature = job.findFeature(tokenizer.nextToken());
							Date st = new Date(0);
							Date et = new Date(1000);
							job.deleteActivity(new Activity("", machine, job, feature, st, et));
							machine.deleteState(new Activity("", machine, job, feature, st, et));
							io.println("Activity (job " + job.getName() 
										+ ", feature " + feature.getName()  + ") deleted!");
						} else {
							io.printErr("Number of parameters for " + commandText.toUpperCase() + " should be 1, 2, or 3");
						}
					} catch (NoSuchElementException nsee) {
						io.printErr("Number of parameters for " + commandText.toUpperCase() + " should be 1, 2, or 3");
					} catch (UnknownObjectException uoe) {
						io.printErr(uoe.getMessage());
					} catch (InvalidStateException ise) {
						io.printErr(ise.getMessage());
					}
					break;
				case RECTANGLE:
					try {
						double x = Double.parseDouble(tokenizer.nextToken());
						double y = Double.parseDouble(tokenizer.nextToken());
						double height = Double.parseDouble(tokenizer.nextToken());
						double width = Double.parseDouble(tokenizer.nextToken());
						ms.addDrawObject(new Rectangle(x, y, height, width));
						break;
					} catch (NumberFormatException nsee) {
						io.printErr("Only four numbers allowed for coordinates x, y, and height and width.");
					}
				case TRIANGLE:
					try {
						double x = Double.parseDouble(tokenizer.nextToken());
						double y = Double.parseDouble(tokenizer.nextToken());
						double height = Double.parseDouble(tokenizer.nextToken());
						double width = Double.parseDouble(tokenizer.nextToken());
						ms.addDrawObject(new RightTriangle(x, y, height, width));
						break;
					} catch (NumberFormatException nsee) {
						io.printErr("Only four numbers allowed for coordinates x, y, and height and base.");
					}
				case OBJECTS:
					ms.printDrawObjects();
					break;
				default:
					io.printErr("Option '" + commandText + "' not yet implemnted!");
					break;
				}

			}
		} catch (NoSuchElementException nse) {
			nse.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Project-3:
		MfgSystemConsoleApp mscApp = new MfgSystemConsoleApp();
		if(args.length>0){
			String inFile = args[0];
			try {
				mscApp.setIO(new FileIO(inFile, false));
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}
		} else{
			mscApp.setIO(new ConsolIO());
		}
		mscApp.run();
		*/
		
		/*
		 * Project-3A:
		 */
		MfgSystem ms;
		if(args.length>0){
			String inFile = args[0];
			if(inFile.endsWith(".mfg")){
				ms = new MfgSystem(inFile.substring(0, inFile.length()-4));
				try {
					MfgSystem.setIO(new FileIO(inFile));
				} catch (FileNotFoundException e) {
					System.out.flush();
					System.err.println("\nFileNotFoundException:");
					System.err.println(e.getMessage());
				}
			}else{
				System.err.println("Input file name: "
						+ inFile
						+ "\nPlease only enter file names with extension '.mfg'!");
				return;
			}
		} else{
			ms = new MfgSystem("manual-config-system");
		}
		ms.read();
		
	}



}
