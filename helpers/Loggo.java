package helpers;

import entities.EventLogEntry;
import main.MainFrame;

public class Loggo {
	public static void log(String separator, String... messages) {
		for (int i = 0; i < messages.length-1; i++) {
			System.out.print(messages[i].concat(separator));
		}
		
		if(messages.length > 0) {
			System.out.println(messages[messages.length-1]);
		}
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void logError(String message) {
		System.err.println(message);
	}
	
	public static EventLogEntry logEvent(int issueId, int eventTypeId, String comment) {
		EventLogEntry event = new EventLogEntry(issueId, MainFrame.loggedInUserId, eventTypeId, comment);
		
		event.save();
		
		return event;
	}
}