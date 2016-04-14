package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Log {
	
	/**
	 * Initializes the log file
	 * @param username
	 */
	public void createLog(String username)
	{
		logFile = new File(username+ "LogFile.txt");
		try{
			if(!logFile.exists())
			{
				logFile.createNewFile();
			}
			logOutput = new FileOutputStream(logFile);
			logInput = new FileInputStream(logFile);
			
		}catch(IOException e)
		{
			System.out.println("Exception occured while attempting to create log file.");
		}		
	}
	
	/**
	 * Writes a message to a FileOutputStream
	 * @param message
	 */
	public void writeToLog(String message)
	{
		byte[] contentInBytes = message.getBytes();
		try{
			logOutput.write(contentInBytes);
			logOutput.flush();
		}catch(IOException e)
		{
			System.out.println("Exception occured while attempting to write to log file.");
		}
	}
	
	/**
	 * Parse logfile to be sent to text pane 
	 * @return
	 */
	public String retreiveLog()
	{
		String temp = "Log Stuff";
		try
		{
			logInput.close();	
		}catch(IOException e)
		{
			System.out.println("Exception occured when attempting to read from log file.");
		}

		return temp;
	}
	
	/**
	 * Closes OutputFileStream
	 * @throws IOException
	 */
	public void closeLogOutput() throws IOException{logOutput.close();}
	
	
	
	private FileOutputStream logOutput;
	private FileInputStream logInput;
	private File logFile;
}
