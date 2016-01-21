package entities;

import helpers.Loggo;

import javax.swing.ImageIcon;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Issue implements DatabaseModel
{
	private int id = 0;
	private int statusId = 0, typeId = 0, projectId = 0, creatorId = 0, assigneeId = 0, categoryId = 0;
	private String name = "", description = "";
	@XStreamOmitField
	private ImageIcon statusIcon = null;
	
	public Issue() {
		
	}

	public Issue(int id, int typeId, String name, int projectId, int creatorId, int assigneeId, int categoryId, int statusId, String description)
	{
		setId(id);
		setTypeId(typeId);
		setName(name);
		setProjectId(projectId);
		setCreatorId(creatorId);
		setAssigneeId(assigneeId);
		setCategoryId(categoryId);
		setStatusId(statusId);
		setStatusIcon(statusId);
		setDescription(description);
	}
	
	public void save() {
		System.out.println("Save. Issue is: " + id);
		
		if(id > 0) {
			update();
		}
		else {
			insert();
		}
	}
	
	public void update() {
		String query = "UPDATE issues SET name = ?, typeId = ?, projectId = ?, creatorId = ?, assignedUserId = ?, categoryId = ?, statusId = ?, description = ?"
				+ " WHERE id = ?";
		
		dbUtility.applyToDatabase(query, name,
										typeId + "",
										projectId + "",
										creatorId + "",
										assigneeId + "",
										categoryId + "",
										statusId + "",
										description,
										id + "");
		
		Loggo.log("Issue updated");
	}
	
	public void insert() {
		if(id > 0) {
			String query = "INSERT INTO issues(id, name, typeId, projectId, creatorId, assignedUserId, categoryId, statusId, description) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			id = dbUtility.applyToDatabase(query,
											id + "",
											name,
											typeId + "",
											projectId + "",
											creatorId + "",
											assigneeId + "",
											categoryId + "",
											statusId + "",
											description);
		}
		else {
			String query = "INSERT INTO issues(name, typeId, projectId, creatorId, assignedUserId, categoryId, statusId, description) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			id = dbUtility.applyToDatabase(query,
											name,
											typeId + "",
											projectId + "",
											creatorId + "",
											assigneeId + "",
											categoryId + "",
											statusId + "",
											description);
		}
		
		Loggo.log("Issue inserted");
	}
	
	public void delete() {
		dbUtility.applyToDatabase("DELETE FROM issues WHERE id = ?", id + "");
		
		Loggo.log("Issue deleted");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
		
		setStatusIcon(statusId);
	}

	public ImageIcon getStatusIcon() {
		return statusIcon;
	}

	public void setStatusIcon(int statusId) {
		switch (statusId) {
		case 1:
			this.statusIcon = new ImageIcon("files/pix/new.png");
			break;
		case 2:
			this.statusIcon = new ImageIcon("files/pix/assigned.png");
			break;
		case 3:
			this.statusIcon = new ImageIcon("files/pix/accepted.png");
			break;
		case 4:
			this.statusIcon = new ImageIcon("files/pix/rejected.png");
			break;
		case 5:
			this.statusIcon = new ImageIcon("files/pix/in_progress.png");
			break;
		case 6:
			this.statusIcon = new ImageIcon("files/pix/testing.png");
			break;
		case 7:
			this.statusIcon = new ImageIcon("files/pix/closed.png");
			break;
		case 8:
			this.statusIcon = new ImageIcon("files/pix/test_failed.png");
			break;
		default:
			break;
		}
	}	
}