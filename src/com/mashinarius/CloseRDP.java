package com.mashinarius;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CloseRDP {

	public static void main(String[] args) {

	}
	
	
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int i = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static void closeRDP(String userName)
	{
		try {
			Process proc = Runtime.getRuntime().exec("qwinsta.exe /server:localhost " + userName); // /server:localhost ^| findstr /c:" + userName
			proc.waitFor();
			
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));

				/*BufferedReader stdError = new BufferedReader(new 
				     InputStreamReader(proc.getErrorStream()));*/

				String s = null;
				boolean isActiveOrDisc = false;
				//String procId = new String();
				String procId=new String();
				while ((s = stdInput.readLine()) != null) {
					if (s.contains(userName) && s.contains("Disc"))
					{
						isActiveOrDisc= true;
						procId = s.substring(s.indexOf(userName)+userName.length(), s.indexOf("Disc")).trim();
					}				    
					else if (s.contains(userName) && s.contains("Active"))
					{
						isActiveOrDisc= true;
						procId = s.substring(s.indexOf(userName)+userName.length(), s.indexOf("Active")).trim();
					}
					
				}
				System.out.println("RDP ID=" + procId);
				if (isActiveOrDisc )
				{
					String tasklistcmd = "tasklist /V /nh /FI \"IMAGENAME eq mstsc.exe\" ";
					Process procMstsc = Runtime.getRuntime().exec(tasklistcmd);
					BufferedReader mstscStdInput = new BufferedReader(new InputStreamReader(procMstsc.getInputStream()));
					
					String mstscId = new String();
					while ((s = mstscStdInput.readLine()) != null) {
						if (s.contains(userName+"_temporaryFile"))
						{
							mstscId = s.substring(s.indexOf("mstsc.exe")+"mstsc.exe".length(), s.indexOf("Console")).trim();
							System.out.println("mstsc.exe PID=" + mstscId);
						}				    
					}
					
					if (isNumeric(procId) && isNumeric(mstscId))
					{
						System.out.println("Trying to logoff user " + userName);
						Process logoff = Runtime.getRuntime().exec("logoff " + procId + " /V");
						logoff.waitFor();
						logoff = Runtime.getRuntime().exec("taskkill /PID " + mstscId + " /F");
						logoff.waitFor();
						System.out.println("Complete.");
					}
				}

				// read any errors from the attempted command
				/*System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}*/
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
