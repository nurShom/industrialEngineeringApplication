package edu.ohio.ise.ise6900.MfgSystem.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	
	public FileIO(String inFileName) throws FileNotFoundException {
		this(inFileName, false);
	}
	
	public FileIO(String inFileName, boolean outToFile) throws FileNotFoundException {
		this(new File(inFileName), outToFile);
	}
	
	public FileIO(File inFile) throws FileNotFoundException {
		this(new BufferedReader(new FileReader(inFile)), false);
	}
	
	public FileIO(File inFile, boolean outToFile) throws FileNotFoundException {
		this(new BufferedReader(new FileReader(inFile)), outToFile);
	}
	
	public FileIO(String inFileName, String outFileName, boolean outToFile) throws FileNotFoundException {
		this(new BufferedReader(new FileReader(new File(inFileName))), outFileName, outToFile);
	}
	
	public FileIO(InputStream inFileStream) throws FileNotFoundException {
		this(new BufferedReader(new InputStreamReader(inFileStream)));
	}
	
	public FileIO(BufferedReader inFileReader) throws FileNotFoundException {
		this(inFileReader, false);
	}
		
	public FileIO(BufferedReader inFileReader, boolean outToFile) throws FileNotFoundException {
		this(inFileReader, "./output" 
				+ (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())) + ".mfgo",
				outToFile);
	}

	public FileIO(BufferedReader inFileReader, String outFileName, boolean outToFile) throws FileNotFoundException {
		bin = inFileReader;
		if (outToFile) {
			outFile = new File(outFileName);
			try {
				FileOutputStream fos = new FileOutputStream(outFile);
				pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")));
			} catch (FileNotFoundException fnfe) {
				throw new FileNotFoundException(outFileName + ":: " + fnfe.getMessage());
			} catch (UnsupportedEncodingException uee) {
				System.err.println("Encoding not supported.");
				try {
					FileOutputStream fos = new FileOutputStream(outFile);
					pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
				} catch (FileNotFoundException fnfe) {
					throw new FileNotFoundException(outFileName + ":: " + fnfe.getMessage());
				}
			}
		} else {
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
	public void setInFile(String inFileName) throws FileNotFoundException{
		this.setInFile(new File(inFileName));
	}
	
	public void setInFile(File inFile) throws FileNotFoundException {
		try {
			this.setInFile(new BufferedReader(new FileReader(inFile)));
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(inFile.getName() + ":: " + fnfe.getMessage());
		}
	}
	
	public void setInFile(InputStream inFileStream) throws FileNotFoundException {
		this.setInFile(new BufferedReader(new InputStreamReader(inFileStream)));
	}
	
	public void setInFile(BufferedReader inFileReader) {
		bin = inFileReader;
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
			//line = "exit";
			line = "console";
		}

		return line;
	}

}
