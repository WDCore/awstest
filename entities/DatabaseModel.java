package entities;

import helpers.DatabaseUtility;

public interface DatabaseModel {
	public DatabaseUtility dbUtility = new DatabaseUtility();
	
	public void save();
	public void insert();
	public void update();
	public void delete();
}