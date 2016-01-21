package entities;

public class Status {
	private int id = 0, phase = 0;
	private String name = "";
	
	public Status(int id, String name, int phase) {
		setId(id);
		setName(name);
		setPhase(phase);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}
}