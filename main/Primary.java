package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Primary 
{
	public static HashMap<String, Boolean> settings = new HashMap<>();
	
	public Primary()
	{
		readSettings();
		new LoginBox();
	}
	
	public void readSettings() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("files/settings/main.txt"));
			Scanner sc = new Scanner(br);
			
			while (sc.hasNext())
			{
	            String line = sc.nextLine();
	            String[] settingsData = line.split(":::");
	            
	            settings.put(settingsData[0], Boolean.parseBoolean(settingsData[1]));
	        }
			
	        sc.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public static void main (String args[])
	{
		new Primary(); 
	}
}