package streamserve.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import streamserve.context.LogService;

public class Base64Filter {
	
	protected byte[] readAll(InputStream input, LogService log, String filter) {
		
		byte[] allBytes = new byte[0];
		byte[] currentBytes = new byte[1024 * 50];
		
		try {
			
			int count = input.read(currentBytes);
			
			while(count > 0) {
				
				byte[] newBytes = Arrays.copyOf(allBytes, allBytes.length + currentBytes.length);
				System.arraycopy(currentBytes, 0, newBytes, allBytes.length, currentBytes.length);
				
				allBytes = newBytes;
				count = input.read(currentBytes);
			}
		}
		catch (IOException e) {
			
			log.log(filter, "Error reading from input stream", streamserve.context.LogLevel.error);
			throw new streamserve.exception.Exception("Error reading from input stream", e);
		}
		
		return allBytes;
	}
	
	protected void writeAll(OutputStream output, byte[] outputBytes, LogService log, String filter) {
		
		try {
			
			output.write(outputBytes);
		}
		catch (IOException e) {
			
			log.log(filter, "Error writing to output stream", streamserve.context.LogLevel.error);
			throw new streamserve.exception.Exception("Error writing to output stream", e);
		}
	}

}
