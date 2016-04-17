package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	public String retreiveLog(String username)
	{
		
		try{
			File tmpLogFile = new File(username+ "LogFile.txt");

			if(!tmpLogFile.exists())
			{
				return "";
			}
			else{			
				String line = new String();
				StringBuilder logBuilder = new StringBuilder();
				BufferedReader logReader;
	
				logReader = new BufferedReader(new FileReader(tmpLogFile));
	
		        while( ( line = logReader.readLine() ) != null )
		        {
		        	logBuilder.append( line );
		        	logBuilder.append( '\n' );
		        }
		        logReader.close();
		        return logBuilder.toString();
			}
		}catch(IOException e)
		{
			System.out.println("Exception occured when attempting to read from log file.");
		}

		return "";
	}
	
	/**
	 * Closes OutputFileStream
	 * @throws IOException
	 */
	public void closeLogOutput() throws IOException{logOutput.close();}
	
	
	
	private FileOutputStream logOutput;
	//private FileInputStream logInput;
	private File logFile;
}
