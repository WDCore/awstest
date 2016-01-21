package panels;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import entities.Project;

public class ProjectListPanel extends JPanel {
	private static final long serialVersionUID = 8402342005164035818L;
	ArrayList<ProjectInfoPanel> panels;
	
	public ProjectListPanel() {
		initialize();
		
	}
	
	public void initialize() {
//		projectImage = new JLabel(new ImageIcon(new ImageIcon("files\\pix\\unikalnipodaraci.jpg").getImage().getScaledInstance(193, 150, Image.SCALE_SMOOTH)));
		panels = new ArrayList<>();
	}
	
	public void listProjects(ArrayList<Project> list) {
		for (Iterator<Project> iterator = list.iterator(); iterator.hasNext();) {
			Project project = iterator.next();
			panels.add(new ProjectInfoPanel(project.getId(), project.getName(), project.getProjectIcon()));
		}
		
		createLayouts();
	}
	
	public void createLayouts() {
		FlowLayout fl = new FlowLayout();
		this.setLayout(fl);
		fl.setAlignment(FlowLayout.LEADING);
		
		panels.stream()/*.sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))*/.forEach(panel -> {
			this.add(panel);
		});
	}

	public ArrayList<ProjectInfoPanel> getPanels() {
		return panels;
	}
}