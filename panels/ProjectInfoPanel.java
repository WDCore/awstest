package panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

public class ProjectInfoPanel extends JPanel {
	private static final long serialVersionUID = -7515675083359644422L;
	int projectId;
	HashMap<String, Integer> hmIssuesCount;
	public JButton bIssuesList, bBugsList, bFeaturesList,
			btIssueCountNew, btIssueCountTesting, btIssueCountAssigned, btIssueCountInProgress,
			btBugCountNew, btBugCountTesting, btBugCountAssigned, btBugCountInProgress,
			btFeatureCountNew, btFeatureCountTesting, btFeatureCountAssigned, btFeatureCountInProgress;

	public ProjectInfoPanel(int id, String title, ImageIcon icon) {
		initialize(id, icon);
		setAdditionalProperties();
		setSizes();
		createLayouts();
		
		this.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	private void initialize(int id, ImageIcon icon) {
		projectId = id;
		bIssuesList = new JButton("Issues", new ImageIcon("files/pix/project_issues.png"));
		bBugsList = new JButton("Bugs", new ImageIcon("files/pix/project_bugs.png"));
		bFeaturesList = new JButton("<html><center>Feature<br />Requests</center></html>", new ImageIcon("files/pix/project_enhancements.png"));
		btIssueCountNew = new JButton(new ImageIcon("files/pix/new_small.png"));
		btIssueCountTesting = new JButton(new ImageIcon("files/pix/testing_small.png"));
		btIssueCountAssigned = new JButton(new ImageIcon("files/pix/assigned_small.png"));
		btIssueCountInProgress = new JButton(new ImageIcon("files/pix/in_progress_small.png"));
		btBugCountNew = new JButton(new ImageIcon("files/pix/new_small.png"));
		btBugCountTesting = new JButton(new ImageIcon("files/pix/testing_small.png"));
		btBugCountAssigned = new JButton(new ImageIcon("files/pix/assigned_small.png"));
		btBugCountInProgress = new JButton(new ImageIcon("files/pix/in_progress_small.png"));
		btFeatureCountNew = new JButton(new ImageIcon("files/pix/new_small.png"));
		btFeatureCountTesting = new JButton(new ImageIcon("files/pix/testing_small.png"));
		btFeatureCountAssigned = new JButton(new ImageIcon("files/pix/assigned_small.png"));
		btFeatureCountInProgress = new JButton(new ImageIcon("files/pix/in_progress_small.png"));
		hmIssuesCount = new HashMap<>();
	}
	
	private void setAdditionalProperties() {
		bIssuesList.setBorder(null);
		bIssuesList.setOpaque(false);
		bIssuesList.setContentAreaFilled(false);
		bIssuesList.setBorderPainted(false);
		bIssuesList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bIssuesList.setVerticalTextPosition(SwingConstants.BOTTOM);
		bIssuesList.setHorizontalTextPosition(SwingConstants.CENTER);
		bIssuesList.setPressedIcon(new ImageIcon("files/pix/project_issues_pressed.png"));
		
		btIssueCountNew.setBorder(null);
		btIssueCountNew.setToolTipText("New");
		btIssueCountNew.setOpaque(false);
		btIssueCountNew.setContentAreaFilled(false);
		btIssueCountNew.setBorderPainted(false);
		btIssueCountNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btIssueCountNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		btIssueCountNew.setHorizontalTextPosition(SwingConstants.CENTER);
		btIssueCountNew.setPressedIcon(new ImageIcon("files/pix/new_small_pressed.png"));
		
		btIssueCountTesting.setBorder(null);
		btIssueCountTesting.setToolTipText("Testing");
		btIssueCountTesting.setOpaque(false);
		btIssueCountTesting.setContentAreaFilled(false);
		btIssueCountTesting.setBorderPainted(false);
		btIssueCountTesting.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btIssueCountTesting.setVerticalTextPosition(SwingConstants.BOTTOM);
		btIssueCountTesting.setHorizontalTextPosition(SwingConstants.CENTER);
		btIssueCountTesting.setPressedIcon(new ImageIcon("files/pix/testing_small_pressed.png"));
		
		btIssueCountAssigned.setBorder(null);
		btIssueCountAssigned.setToolTipText("Assigned");
		btIssueCountAssigned.setOpaque(false);
		btIssueCountAssigned.setContentAreaFilled(false);
		btIssueCountAssigned.setBorderPainted(false);
		btIssueCountAssigned.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btIssueCountAssigned.setVerticalTextPosition(SwingConstants.BOTTOM);
		btIssueCountAssigned.setHorizontalTextPosition(SwingConstants.CENTER);
		btIssueCountAssigned.setPressedIcon(new ImageIcon("files/pix/assigned_small_pressed.png"));
		
		btIssueCountInProgress.setBorder(null);
		btIssueCountInProgress.setToolTipText("In progress");
		btIssueCountInProgress.setOpaque(false);
		btIssueCountInProgress.setContentAreaFilled(false);
		btIssueCountInProgress.setBorderPainted(false);
		btIssueCountInProgress.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btIssueCountInProgress.setVerticalTextPosition(SwingConstants.BOTTOM);
		btIssueCountInProgress.setHorizontalTextPosition(SwingConstants.CENTER);
		btIssueCountInProgress.setPressedIcon(new ImageIcon("files/pix/in_progress_small_pressed.png"));
		
		bBugsList.setBorder(null);
		bBugsList.setOpaque(false);
		bBugsList.setContentAreaFilled(false);
		bBugsList.setBorderPainted(false);
		bBugsList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bBugsList.setVerticalTextPosition(SwingConstants.BOTTOM);
		bBugsList.setHorizontalTextPosition(SwingConstants.CENTER);
		bBugsList.setPressedIcon(new ImageIcon("files/pix/project_bugs_pressed.png"));
		
		btBugCountNew.setBorder(null);
		btBugCountNew.setToolTipText("New");
		btBugCountNew.setOpaque(false);
		btBugCountNew.setContentAreaFilled(false);
		btBugCountNew.setBorderPainted(false);
		btBugCountNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btBugCountNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		btBugCountNew.setHorizontalTextPosition(SwingConstants.CENTER);
		btBugCountNew.setPressedIcon(new ImageIcon("files/pix/new_small_pressed.png"));
		
		btBugCountTesting.setBorder(null);
		btBugCountTesting.setToolTipText("Testing");
		btBugCountTesting.setOpaque(false);
		btBugCountTesting.setContentAreaFilled(false);
		btBugCountTesting.setBorderPainted(false);
		btBugCountTesting.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btBugCountTesting.setVerticalTextPosition(SwingConstants.BOTTOM);
		btBugCountTesting.setHorizontalTextPosition(SwingConstants.CENTER);
		btBugCountTesting.setPressedIcon(new ImageIcon("files/pix/testing_small_pressed.png"));
		
		btBugCountAssigned.setBorder(null);
		btBugCountAssigned.setToolTipText("Assigned");
		btBugCountAssigned.setOpaque(false);
		btBugCountAssigned.setContentAreaFilled(false);
		btBugCountAssigned.setBorderPainted(false);
		btBugCountAssigned.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btBugCountAssigned.setVerticalTextPosition(SwingConstants.BOTTOM);
		btBugCountAssigned.setHorizontalTextPosition(SwingConstants.CENTER);
		btBugCountAssigned.setPressedIcon(new ImageIcon("files/pix/assigned_small_pressed.png"));
		
		btBugCountInProgress.setBorder(null);
		btBugCountInProgress.setToolTipText("In progress");
		btBugCountInProgress.setOpaque(false);
		btBugCountInProgress.setContentAreaFilled(false);
		btBugCountInProgress.setBorderPainted(false);
		btBugCountInProgress.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btBugCountInProgress.setVerticalTextPosition(SwingConstants.BOTTOM);
		btBugCountInProgress.setHorizontalTextPosition(SwingConstants.CENTER);
		btBugCountInProgress.setPressedIcon(new ImageIcon("files/pix/in_progress_small_pressed.png"));
		
		bFeaturesList.setBorder(null);
		bFeaturesList.setOpaque(false);
		bFeaturesList.setContentAreaFilled(false);
		bFeaturesList.setBorderPainted(false);
		bFeaturesList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bFeaturesList.setVerticalTextPosition(SwingConstants.BOTTOM);
		bFeaturesList.setHorizontalTextPosition(SwingConstants.CENTER);
		bFeaturesList.setPressedIcon(new ImageIcon("files/pix/project_enhancements_pressed.png"));
		
		btFeatureCountNew.setBorder(null);
		btFeatureCountNew.setToolTipText("New");
		btFeatureCountNew.setOpaque(false);
		btFeatureCountNew.setContentAreaFilled(false);
		btFeatureCountNew.setBorderPainted(false);
		btFeatureCountNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btFeatureCountNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		btFeatureCountNew.setHorizontalTextPosition(SwingConstants.CENTER);
		btFeatureCountNew.setPressedIcon(new ImageIcon("files/pix/new_small_pressed.png"));
		
		btFeatureCountTesting.setBorder(null);
		btFeatureCountTesting.setToolTipText("Testing");
		btFeatureCountTesting.setOpaque(false);
		btFeatureCountTesting.setContentAreaFilled(false);
		btFeatureCountTesting.setBorderPainted(false);
		btFeatureCountTesting.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btFeatureCountTesting.setVerticalTextPosition(SwingConstants.BOTTOM);
		btFeatureCountTesting.setHorizontalTextPosition(SwingConstants.CENTER);
		btFeatureCountTesting.setPressedIcon(new ImageIcon("files/pix/testing_small_pressed.png"));
		
		btFeatureCountAssigned.setBorder(null);
		btFeatureCountAssigned.setToolTipText("Assigned");
		btFeatureCountAssigned.setOpaque(false);
		btFeatureCountAssigned.setContentAreaFilled(false);
		btFeatureCountAssigned.setBorderPainted(false);
		btFeatureCountAssigned.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btFeatureCountAssigned.setVerticalTextPosition(SwingConstants.BOTTOM);
		btFeatureCountAssigned.setHorizontalTextPosition(SwingConstants.CENTER);
		btFeatureCountAssigned.setPressedIcon(new ImageIcon("files/pix/assigned_small_pressed.png"));
		
		btFeatureCountInProgress.setBorder(null);
		btFeatureCountInProgress.setToolTipText("In progress");
		btFeatureCountInProgress.setOpaque(false);
		btFeatureCountInProgress.setContentAreaFilled(false);
		btFeatureCountInProgress.setBorderPainted(false);
		btFeatureCountInProgress.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btFeatureCountInProgress.setVerticalTextPosition(SwingConstants.BOTTOM);
		btFeatureCountInProgress.setHorizontalTextPosition(SwingConstants.CENTER);
		btFeatureCountInProgress.setPressedIcon(new ImageIcon("files/pix/in_progress_small_pressed.png"));
	}
	
	private void setSizes() {
		this.setPreferredSize(new Dimension(350, 150));
		bIssuesList.setMinimumSize(new Dimension(40, 40));
		bBugsList.setMinimumSize(new Dimension(40, 40));
		bFeaturesList.setMinimumSize(new Dimension(40, 40));
	}
	
	private void createLayouts() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addComponent(bIssuesList)
					.addGroup(layout.createSequentialGroup()
						.addComponent(btIssueCountNew)
						.addComponent(btIssueCountAssigned)
						.addComponent(btIssueCountInProgress)
						.addComponent(btIssueCountTesting)))
				.addGap(30)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addComponent(bBugsList)
					.addGroup(layout.createSequentialGroup()
						.addComponent(btBugCountNew)
						.addComponent(btBugCountAssigned)
						.addComponent(btBugCountInProgress)
						.addComponent(btBugCountTesting)))
				.addGap(30)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addComponent(bFeaturesList)
					.addGroup(layout.createSequentialGroup()
						.addComponent(btFeatureCountNew)
						.addComponent(btFeatureCountAssigned)
						.addComponent(btFeatureCountInProgress)
						.addComponent(btFeatureCountTesting))));
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
					.addComponent(bIssuesList)
					.addComponent(bBugsList)
					.addComponent(bFeaturesList))
				.addGroup(layout.createParallelGroup()
					.addComponent(btIssueCountNew)
					.addComponent(btIssueCountAssigned)
					.addComponent(btIssueCountInProgress)
					.addComponent(btIssueCountTesting)
					.addComponent(btBugCountNew)
					.addComponent(btBugCountAssigned)
					.addComponent(btBugCountInProgress)
					.addComponent(btBugCountTesting)
					.addComponent(btFeatureCountNew)
					.addComponent(btFeatureCountAssigned)
					.addComponent(btFeatureCountInProgress)
					.addComponent(btFeatureCountTesting)));
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public HashMap<String, Integer> getIssuesCount() {
		return hmIssuesCount;
	}
	
	public void addIssueCount(String type, int count) {
		hmIssuesCount.remove(type);
		hmIssuesCount.put(type, count);
		
		updateButtonTexts();
	}
	
	public void updateButtonTexts() {
		btIssueCountNew.setText(hmIssuesCount.get("issues_new") + "");
		btIssueCountTesting.setText(hmIssuesCount.get("issues_testing") + "");
		btIssueCountAssigned.setText(hmIssuesCount.get("issues_assigned") + "");
		btIssueCountInProgress.setText(hmIssuesCount.get("issues_in_progress") + "");
		btBugCountNew.setText(hmIssuesCount.get("bugs_new") + "");
		btBugCountTesting.setText(hmIssuesCount.get("bugs_testing") + "");
		btBugCountAssigned.setText(hmIssuesCount.get("bugs_assigned") + "");
		btBugCountInProgress.setText(hmIssuesCount.get("bugs_in_progress") + "");
		btFeatureCountNew.setText(hmIssuesCount.get("requests_new") + "");
		btFeatureCountTesting.setText(hmIssuesCount.get("requests_testing") + "");
		btFeatureCountAssigned.setText(hmIssuesCount.get("requests_assigned") + "");
		btFeatureCountInProgress.setText(hmIssuesCount.get("requests_in_progress") + "");
	}
}