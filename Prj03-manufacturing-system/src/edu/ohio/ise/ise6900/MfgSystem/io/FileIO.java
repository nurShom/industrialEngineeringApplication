package edu.ohio.ise.ise6900.MfgSystem.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileIO extends AbstractIO {

	private File inFile;
	private File outFile;
	private BufferedReader bin;
	private PrintWriter pout;
	boolean outToFile;

	public FileIO() throws FileNotFoundException {
		this(false);
	}

	public FileIO(boolean outToFile) throws FileNotFoundException {
		this("./commands.mfg", outToFile);
	}

	public FileIO(String inFileName, boolean outToFile) throws FileNotFoundException {
		this(inFileName, "./output" + (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())) + ".mfgo",
				outToFile);
	}

	public FileIO(String inFileName, String outFileName, boolean outToFile) throws FileNotFoundException {
		inFile = new File(inFileName);
		try {
			bin = new BufferedReader(new FileReader(inFile));
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(inFileName + ":: " + fnfe.getMessage());
		}
		if (outToFile) {
			outFile = new File(outFileName);
			try {
				FileOutputStream fos = new FileOutputStream(outFile);
				pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")));
			} catch (FileNotFoundException fnfe) {
				throw new FileNotFoundException(outFileName + ":: " + fnfe.getMessage());
			}
		} else{
			pout = new PrintWriter(System.out);
		}
	}

	/**
	 * @param outFile
	 *            the outFile to set
	 * @throws FileNotFoundException
	 */
	public void setOutFile(String outFileName) throws FileNotFoundException {
		this.outFile = new File(outFileName);
		try {
			FileOutputStream fos = new FileOutputStream(outFile);
			pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(outFileName + ":: " + fnfe.getMessage());
		}
	}

	/**
	 * @param inFile
	 *            the inFile to set
	 * @throws FileNotFoundException
	 */
	public void setInFile(String inFileName) throws FileNotFoundException {
		this.inFile = new File(inFileName);
		try {
			bin = new BufferedReader(new FileReader(inFile));
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(inFileName + ":: " + fnfe.getMessage());
		}
	}

	public void print(String text) {
		if (pout == null) {
			System.err.println("Output file undefined: " + this.outFile.getName());
			return;
		}
		pout.flush();
		pout.print(text);
	}

	public void println(String text) {
		this.print(text);
		pout.println();
	}

	public void printErr(String text) {
		if (pout == null) {
			System.err.println("Output file undefined: " + this.outFile.getName());
			return;
		}
		pout.println("Error: " + text);
	}

	public String readLine() throws IOException {
		if (bin == null) {
			System.err.println("Input file undefined: " + this.inFile.getName());
			pout.println("Input file undefined: " + this.inFile.getName());
		}
		// Reading next line
		String line = bin.readLine();
		// Checking for file comments or empty line
		while (line != null && (line.trim().startsWith("#") || line.isEmpty())) {
			this.println("\n" + line);
			line = bin.readLine();
		}
		// Checking end of file
		if (line == null) {
			this.println("End of input commands file.");
			line = "exit";
		}

		return line;
	}

}
