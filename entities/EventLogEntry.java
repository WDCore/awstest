package entities;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import helpers.Loggo;

public class EventLogEntry implements DatabaseModel {
	String eventMessage = "", comment = "", eventCreatorName = "";
	int id = 0, issueId = 0, eventCreatorId = 0;
	int eventTypeId = 0; //1 = comment; 2 = new; 3 = edit; 
	long eventDate = 0;
	boolean seen = false;
	
	public EventLogEntry(int issueId, int eventCreatorId, int eventTypeId, String comment) {
		setIssueId(issueId);
		setEventCreatorId(eventCreatorId);
		setEventTypeId(eventTypeId);
		setComment(comment);
		setEventDate(GregorianCalendar.getInstance().getTimeInMillis());
		setEventMessage(eventTypeId);
	}
	
	public EventLogEntry(int id, int issueId, int eventCreatorId, int eventTypeId, String comment, long eventDate, boolean seen, String eventMessage) {
		setId(id);
		setIssueId(issueId);
		setEventCreatorId(eventCreatorId);
		setEventTypeId(eventTypeId);
		setComment(comment);
		setEventDate(eventDate);
		setSeen(seen);
		setEventMessage(eventMessage);
	}
	
	public int getEventTypeId() {
		return eventTypeId;
	}
	
	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	
	public String getEventMessage() {
		return eventMessage;
	}

	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}
	
	public void setEventMessage(int eventTypeId) {
		switch(eventTypeId) {
			case 2:
				this.eventMessage = "Task was created";
				break;
			case 3:
				this.eventMessage = "Task was edited";
				break;
			default:
				break;
		}
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getIssueId() {
		return issueId;
	}
	
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	
	public int getEventCreatorId() {
		return eventCreatorId;
	}
	
	public void setEventCreatorId(int eventCreatorId) {
		this.eventCreatorId = eventCreatorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public long getEventDate() {
		return eventDate;
	}

	public void setEventDate(long eventDate) {
		this.eventDate = eventDate;
	}
	
	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	public String getEventCreatorName() {
		return eventCreatorName;
	}

	public void setEventCreatorName(String eventCreatorName) {
		this.eventCreatorName = eventCreatorName;
	}

	public void save() {
		if(id > 0) {
			update();
		}
		else {
			insert();
		}
	}
	
	public void insert() {
		if(id > 0) {
			String query = "INSERT INTO events(id, issueId, eventCreatorId, eventTypeId, comment, eventDate) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			id = dbUtility.applyToDatabase(query,
											id + "",
											issueId + "",
											eventCreatorId + "",
											eventTypeId + "",
											comment,
											new Timestamp(eventDate).toString());
		}
		else {
			String query = "INSERT INTO events(issueId, eventCreatorId, eventTypeId, comment, eventDate) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			id = dbUtility.applyToDatabase(query,
											issueId + "",
											eventCreatorId + "",
											eventTypeId + "",
											comment,
											new Timestamp(eventDate).toString());
		}
		
		Loggo.log("Event inserted");
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}
}