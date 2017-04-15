package edu.ohio.ise.ise6900.MfgSystem.io;

import java.io.IOException;

public abstract class AbstractIO {

	
	public abstract void print(String text);

	public abstract void println(String text);

	public abstract void printErr(String text);
	
	public abstract String readLine() throws IOException;

}
