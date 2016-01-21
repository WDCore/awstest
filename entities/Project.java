package entities;

import javax.swing.ImageIcon;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Project {
	private int id = 0, managerId = 0;
	private String name = "", url = "", localUrl = "", repository = "";
	@XStreamOmitField
	private ImageIcon projectIcon = null;
	
	public Project(int id, String name, int managerId, String url, String localUrl, String repository) {
		setId(id);
		setManagerId(managerId);
		setName(name);
		setUrl(url);
		setLocalUrl(localUrl);
		setRepository(repository);
	}
	
	public Project(int id, String name, String imageName) {
		setId(id);
		setName(name);
		createProjectIcon(imageName);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getManagerId() {
		return managerId;
	}
	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public ImageIcon getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(ImageIcon projectIcon) {
		this.projectIcon = projectIcon;
	}
	
	public void createProjectIcon(String imageName) {
		this.projectIcon = new ImageIcon("files/pix/" + imageName);
	}
}