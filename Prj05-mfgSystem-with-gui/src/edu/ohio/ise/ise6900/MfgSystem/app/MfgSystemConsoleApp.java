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
 * @author Nur Shomik Arafat
 *
 */
public class MfgSystemConsoleApp {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
