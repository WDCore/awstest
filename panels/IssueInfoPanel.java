package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import entities.Issue;

public class IssueInfoPanel extends JPanel {
	private static final long serialVersionUID = 9068744560037565318L;
	JLabel lTitle, lProject, lCreatedBy, lAssignedTo, lComments;
	public JButton bView, bEdit, bDelete;
	Issue issue;
	int maxTitleLength = 50;
	boolean selected = false;
	
	public IssueInfoPanel(Issue issue, int loggedInUserId) {
		this.issue = issue;
		
		initialize();
		setSizes();
		setAdditionalProperties(loggedInUserId);
		updateButtonVisibility(loggedInUserId);
		createLayouts();
		
		TitledBorder titledBorder = new TitledBorder(issue.getId() + "");
		
		titledBorder.setTitleJustification(TitledBorder.LEADING);
		titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
		titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD));
		
		this.setBorder(titledBorder);
//		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), issue.getId() + "", TitledBorder.LEADING, TitledBorder.LEADING, super.getFont(), Color.BLACK));
	}
	
	private void initialize() {
		lTitle = new JLabel(issue.getName().length() > maxTitleLength?issue.getName().substring(0, maxTitleLength) + "...":issue.getName());
		bView = new JButton(new ImageIcon("files/pix/view.png"));
		bEdit = new JButton(new ImageIcon("files/pix/edit.png"));
		bDelete = new JButton(new ImageIcon("files/pix/delete.png"));
		lComments = new JLabel(new ImageIcon("files/pix/comments.png"));
		lComments.setHorizontalTextPosition(JLabel.CENTER);
		lComments.setForeground(Color.WHITE);
		lComments.setText("12");
	}
	
	public void setSizes() {
		this.setPreferredSize(new Dimension(450, 30));
	}
	
	private void setAdditionalProperties(int loggedInUserId) {
		bView.setToolTipText("View");
		bView.setBorder(null);
		bView.setOpaque(false);
		bView.setContentAreaFilled(false);
		bView.setBorderPainted(false);
		bView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		bEdit.setToolTipText("Edit");
		bEdit.setBorder(null);
		bEdit.setOpaque(false);
		bEdit.setContentAreaFilled(false);
		bEdit.setBorderPainted(false);
		bEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		bDelete.setToolTipText("Delete");
		bDelete.setBorder(null);
		bDelete.setOpaque(false);
		bDelete.setContentAreaFilled(false);
		bDelete.setBorderPainted(false);
		bDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void updateButtonVisibility(int loggedInUserId) {
		bEdit.setVisible(loggedInUserId == issue.getCreatorId() && issue.getStatusId() == 1);
		bDelete.setVisible(loggedInUserId == issue.getCreatorId() && issue.getStatusId() == 1);
	}

	private void createLayouts() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(lTitle)
					.addComponent(bView)
					.addComponent(bEdit)
					.addComponent(bDelete))
				.addGroup(layout.createSequentialGroup()
					.addComponent(lComments)));
		
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(lTitle)
					.addComponent(lComments))
				.addComponent(bView)
				.addComponent(bEdit)
				.addComponent(bDelete));
	}
	
	public Issue getIssue() {
		return issue;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(issue.getStatusIcon().getImage(), 45, 2, null);
		
		if(isSelected()) {
			String picName = "eye";
			Random rand = new Random();
			
			if(rand.nextInt(100) > 50) {
				if(rand.nextInt(100) > 50) {
					if(rand.nextInt(100) > 50) {
						if(rand.nextInt(2) == 1) {
							picName = "ельоменат";
						}
						else {
							picName = "ельоменат_alt";
						}
					}
				}
			}
			
			g.drawImage(new ImageIcon("files/pix/" + picName + ".png").getImage(),
					this.getPreferredSize().width - 35,
					2, null);
		}
	}
}