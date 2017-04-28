package edu.ohio.ise.ise6900.MfgSystem.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.ohio.ise.ise6900.MfgSystem.io.*;
import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.*;
import edu.ohio.ise.ise6900.MfgSystem.model.geometry.*;
import javafx.scene.shape.Shape;

public class MfgSystem extends MfgObject
{
	private Map<String, Job> jobs;
	private Map<String, Machine> machines;
	private ArrayList<DrawObject> objects;
	
	enum Command {
		JOB, MACHINE, ACTIVITY, FEATURE, STATE, // to create objects
		ACTIVITIES, FEATURES, STATES, // to report collection of a given object
		DELETE, PRINTOUT, DISPLAY, // to delete, printout, or display an individual object
		JOBS, MACHINES, SYSTEM, // to report system state and collection
		RECTANGLE, TRIANGLE, OBJECTS, // to make draw -objects
		EXIT, QUIT, // to exit the application
		CONSOLE, // to read data from console
		EOF // to close file reader when end of file reached
	}

	static SortedMap<String, Command> commands;
	static String menu;

	static {
		commands = new TreeMap<String, Command>();
		commands.put("job", Command.JOB);
		commands.put("machine", Command.MACHINE);
		commands.put("activity", Command.ACTIVITY);
		commands.put("mfgfeature", Command.FEATURE);
		commands.put("feature", Command.FEATURE);
		commands.put("machine-state", Command.STATE);
		commands.put("state", Command.STATE);

		commands.put("activities", Command.ACTIVITIES);
		commands.put("mfgfeatures", Command.FEATURES);
		commands.put("features", Command.FEATURES);
		commands.put("machine-states", Command.STATES);
		commands.put("states", Command.STATES);

		commands.put("delete", Command.DELETE);
		commands.put("printout", Command.PRINTOUT);
		commands.put("display", Command.DISPLAY);

		commands.put("jobs", Command.JOBS);
		commands.put("machines", Command.MACHINES);
		commands.put("system", Command.SYSTEM);

		commands.put("rectangle", Command.RECTANGLE);
		commands.put("traingle", Command.TRIANGLE);
		commands.put("objects", Command.OBJECTS);

		commands.put("exit", Command.EXIT);
		commands.put("quit", Command.QUIT);
		commands.put("console", Command.CONSOLE);
		commands.put("eof", Command.EOF);

		menu = "\nOptions : \n\t" + commands.keySet().toString() + "\nEnter the command:->";
	}
	
	public static void setIO(AbstractIO io) {
		MfgObject.io = io;
	}
	public static AbstractIO getIO() {
		return MfgObject.io;
	}

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
	
	/**
	 * @return the jobs
	 */
	public Map<String, Job> getJobs() {
		return jobs;
	}
	public void setJobs(Map<String, Job> jobs) {
		this.jobs = jobs;
	}

	/**
	 * @return the machines
	 */
	public Map<String, Machine> getMachines() {
		return machines;
	}
	public void setMachines(Map<String, Machine> machines) {
		this.machines = machines;
	}

	public void addDrawObject(DrawObject o){
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
	
	public void read(){
		try {
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
				case EOF:
					io.printErr("End of file reached...");
				case EXIT:
				case QUIT:
					io.printErr("Closing the reader application!");
					keepRunning = false;
					break;
				case JOB:
					// job jobName batchSize
					// creates job
					try {
						String jobName = tokenizer.nextToken();
						int batchSize = Integer.parseInt(tokenizer.nextToken());
						this.addJob(new Job(jobName, batchSize));
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
					if (this.countJobs() > 0) {
						this.printJobs();
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
						Job job = this.findJob(jobName);
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
						Job j = this.findJob(jobName);
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
						Machine machine = this.findMachine(machineName);
						String jobName = tokenizer.nextToken();
						Job job = this.findJob(jobName);
						String featureName = tokenizer.nextToken();
						MfgFeature feature = job.findFeature(featureName);
						Date startTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						Date endTime = new Date(Long.parseLong(tokenizer.nextToken()) * 1000);
						String actName = machineName + "-" + jobName 
									+ "-" + featureName + "-" + (startTime.getTime()/1000);
						Activity act = new Activity(actName, machine, job, feature, startTime, endTime);
						machine.addState(act);
						job.addActivity(act);
						feature.addActivity(act);
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
						Job j = this.findJob(jobName);
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
						this.addMachine(new Machine(machineName));
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
					if (this.countMachines() > 0) {
						this.printMachines();
					} else {
						io.printErr("System has no Machine.");
					}
					break;
				case STATE:
					// state machineName state startTime endTime
					// creates activity
					try {
						String machineName = tokenizer.nextToken();
						Machine machine = this.findMachine(machineName);
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
						Machine machine = this.findMachine(machineName);
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
					this.printout();
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
								this.findJob(objectName).printout();
							} catch (UnknownObjectException uoe) {
								try {
									this.findMachine(objectName).printout();
								} catch (UnknownObjectException uoe2) {
									io.printErr("No job or machine with name '" + objectName + "' exists!");
								}
							}
						} else if (option == 2) {
							// feature
							MfgFeature feature = this.findJob(tokenizer.nextToken()).findFeature(tokenizer.nextToken());
							feature.printout();
						} else if (option == 3) {
							// activity
							tokenizer.nextToken();
							Job job = this.findJob(tokenizer.nextToken());
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
								this.findJob(objectName).printout();
								this.deleteJob(objectName);
								io.println("Job '" + objectName + "' deleted!");
							} catch (UnknownObjectException uoe) {
								try {
									this.findMachine(objectName).printout();
									this.deleteMachine(objectName);
									io.println("Machine '" + objectName + "' deleted!");
								} catch (UnknownObjectException uoe2) {
									io.printErr("No job or machine with name '" + objectName + "' exists!");
								}
							}
						} else if (option == 2) {
							// feature
							Job job = this.findJob(tokenizer.nextToken());
							String featureName = tokenizer.nextToken();
							job.deleteFeature(featureName);
							io.println("Feature " + featureName + " (in Job " 
										+ job.getName() + ") deleted!");
						} else if (option == 3) {
							// activity
							Machine machine = this.findMachine(tokenizer.nextToken());
							Job job = this.findJob(tokenizer.nextToken());
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
						this.addDrawObject(new Rectangle(x, y, height, width));
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
						this.addDrawObject(new RightTriangle(x, y, height, width));
						break;
					} catch (NumberFormatException nsee) {
						io.printErr("Only four numbers allowed for coordinates x, y, and height and base.");
					}
				case OBJECTS:
					this.printDrawObjects();
					break;
				case CONSOLE:
					io = new ConsoleIO();
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

	@Override
	public LinkedList<Shape> makeShapes() {
		LinkedList<Shape> shapes = new LinkedList<Shape>();
		for(Machine m : this.machines.values()){
			shapes.addAll(m.makeShapes());
		}
//		for(Job j : this.jobs.values()){
//			shapes.addAll(j.makeShapes());
//		}
		return shapes;
	}

	@Override
	public void write() {
		MfgSystem.getIO().println("# Manufacturing System: " + this.getName());
		MfgSystem.getIO().println("\n# machines");
		for(Machine m : this.getMachines().values()){
			m.write();
		}
		MfgSystem.getIO().println("\n# jobs");
		for(Job j : this.getJobs().values()){
			MfgSystem.getIO().println("");
			j.write();
			MfgSystem.getIO().println("# job " + j.getName() + " features");
			for(MfgFeature f : j.getFeatures().values()){
				f.write();
			}
			MfgSystem.getIO().println("# job " + j.getName() + " activities");
			for(Activity a : j.getActivities()){
				a.write();
			}
		}
		for(Machine m : this.getMachines().values()){
			MfgSystem.getIO().println("\n# machine " + m.getName() + " states, total: " + m.getMachineStates().size());
			System.out.println("Number of states:" + m.getMachineStates().size()); 
			for(AbstractState as : m.getMachineStates()){
				if(as instanceof MachineState){
					((MachineState) as).write();
				}
			}
		}
		MfgSystem.getIO().print("\n# End of file");
	}
	
}

