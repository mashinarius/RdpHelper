package com.mashinarius;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CloseRDP implements RDPConstants {

	/**
	 * @param string
	 * @return true if the input String is an integer type, and false otherwise
	 */
	private static boolean isNumeric(String string) {
		try {
			@SuppressWarnings("unused")
			int i = Integer.parseInt(string);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Find RDP ID assotiated with the username. Logoff username.
	 * 
	 * @param userName
	 * @return false if fails.
	 */
	public static boolean logoffUser(String userName) {
		try {
			Process proc = Runtime.getRuntime().exec("qwinsta.exe /server:localhost " + userName);
			if (proc.waitFor() != 0) {
				return false;
			}

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String s = null;
			boolean isActiveOrDisconnected = false;

			String procId = new String();
			while ((s = stdInput.readLine()) != null) {
				if (s.contains(userName) && s.contains(STATE_DISCONNECTED)) {
					isActiveOrDisconnected = true;
					procId = s.substring(s.indexOf(userName) + userName.length(), s.indexOf(STATE_DISCONNECTED)).trim();
				} else if (s.contains(userName) && s.contains(STATE_ACTIVE)) {
					isActiveOrDisconnected = true;
					procId = s.substring(s.indexOf(userName) + userName.length(), s.indexOf(STATE_ACTIVE)).trim();
				}
			}
			stdInput.close();

			if (isActiveOrDisconnected) {
				if (isNumeric(procId)) {
					System.out.println("Trying to logoff user " + userName + " using RDP ID = " + procId);
					Process logoff = Runtime.getRuntime().exec("logoff " + procId + " /V");
					if (logoff.waitFor() == 0) {
						System.out.println("Complete.");
						return true;
					}
				}
			}
		} catch (IOException e) {
			if (e.getLocalizedMessage().contains("CreateProcess error=2") && e.getLocalizedMessage().contains("qwinsta")) {
				System.err.println("qwinsta.exe could not be found.");
				System.err.println("Check that Windows and Java platform matches each other - e.g. both x86 or both x64.");
			} else {
				e.printStackTrace();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Find mstsc process assotiated with the username. kill mstsc process.
	 * 
	 * @param userName
	 * @return false if fails.
	 */
	public static boolean killMstscProcess(String userName) {
		String s = null;
		Process procMstsc;
		try {
			procMstsc = Runtime.getRuntime().exec("tasklist /V /nh /FI \"IMAGENAME eq mstsc.exe\" ");

			BufferedReader mstscStdInput = new BufferedReader(new InputStreamReader(procMstsc.getInputStream()));

			String mstscId = new String();
			while ((s = mstscStdInput.readLine()) != null) {
				if (s.contains(userName + RDP_FILE_SUFFIX)) {
					mstscId = s.substring(s.indexOf("mstsc.exe") + "mstsc.exe".length(), s.indexOf("Console")).trim();
				}
			}
			mstscStdInput.close();
			if (isNumeric(mstscId)) {
				System.out.println("Trying to kill mstsc process with ID = " + mstscId);
				Process logoff = Runtime.getRuntime().exec("taskkill /PID " + mstscId + " /F");
				if (logoff.waitFor() == 0) {
					System.out.println("Complete.");
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
