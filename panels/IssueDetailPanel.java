package panels;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import main.MainFrame;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import entities.EventLogEntry;

public class IssueDetailPanel extends JPanel {
	private static final long serialVersionUID = -2755278974240901641L;
	private int selectedIssueId = 0;
	JLabel lbId, lbName, lbCreator, lbAssignee, lbStatus, lbStatusIcon, lbDescription;
	public JButton btSelfAssign, btAssign, btAcceptAssignment, btRejectAssignment, btReturnForTesting, btSolve, btNotSolved;
	public JToggleButton btComments, btEventLog;
	JPanel pnLifecycle, pnDiscussion, pnEventLog;
	ArrayList<EventLogEntry> currentEvents;
	ButtonGroup btGroupLifecycle;
	
	public IssueDetailPanel() {
		initialize();
		createLayout();
		setSizes();
		setListeners();
		setAdditionalProperties();
		
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5)));
	}
	
	public void initialize() {
		lbId = new JLabel();
		lbName = new JLabel();
		lbCreator = new JLabel();
		lbAssignee = new JLabel();
		lbStatus = new JLabel();
		lbStatusIcon = new JLabel();
		lbDescription = new JLabel();
		
		btSelfAssign = new JButton(new ImageIcon("files/pix/self_assign.png"));
		btAssign = new JButton(new ImageIcon("files/pix/assign.png"));
		btAcceptAssignment = new JButton(new ImageIcon("files/pix/accept_button.png"));
		btRejectAssignment = new JButton(new ImageIcon("files/pix/reject_button.png"));
		btReturnForTesting = new JButton(new ImageIcon("files/pix/test_return_button.png"));
		btSolve = new JButton(new ImageIcon("files/pix/solve_button.png"));
		btNotSolved = new JButton(new ImageIcon("files/pix/not_solved_button.png"));
		btComments = new JToggleButton(new ImageIcon("files/pix/comments.png"));
		btEventLog = new JToggleButton(new ImageIcon("files/pix/event_log.png"));
		
		pnLifecycle = new JPanel();
		pnDiscussion = new JPanel();
		pnEventLog = new JPanel();
		
		btGroupLifecycle = new ButtonGroup();
	}
	
	public void setSizes() {
		this.setMinimumSize(new Dimension(350, 480));
		this.setMaximumSize(new Dimension(350, 480));
		lbName.setMinimumSize(new Dimension(200, 50));
		lbDescription.setMinimumSize(new Dimension(200, 20));
	}
	
	public void createLayout() {
		btGroupLifecycle.add(btComments);
		btGroupLifecycle.add(btEventLog);
		
		pnLifecycle.setLayout(new CardLayout());
		pnLifecycle.add(pnDiscussion, "Show comments");
		pnLifecycle.add(pnEventLog, "Show events");
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addComponent(lbId)
				.addComponent(lbName)
				.addComponent(lbCreator)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lbAssignee)
					.addComponent(btSelfAssign)
					.addComponent(btAssign)
					.addComponent(btAcceptAssignment)
					.addComponent(btRejectAssignment)
					.addComponent(btReturnForTesting)
					.addComponent(btSolve)
					.addComponent(btNotSolved))
				.addGroup(layout.createSequentialGroup()
					.addComponent(lbStatusIcon)
					.addComponent(lbStatus))
				.addComponent(lbDescription)
				.addGroup(layout.createSequentialGroup()
					.addComponent(btComments)
					.addComponent(btEventLog))
				.addComponent(pnLifecycle));
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(lbId)
				.addComponent(lbName)
				.addComponent(lbCreator)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addComponent(lbAssignee)
					.addComponent(btSelfAssign)
					.addComponent(btAssign)
					.addComponent(btAcceptAssignment)
					.addComponent(btRejectAssignment)
					.addComponent(btReturnForTesting)
					.addComponent(btSolve)
					.addComponent(btNotSolved))
				.addGroup(layout.createParallelGroup()
					.addComponent(lbStatusIcon)
					.addComponent(lbStatus))
				.addGap(20)
				.addComponent(lbDescription)
				.addGap(20)
				.addGroup(layout.createParallelGroup()
					.addComponent(btComments)
					.addComponent(btEventLog))
				.addComponent(pnLifecycle));
	}
	
	public void setListeners() {
		btComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) pnLifecycle.getLayout();
				cl.show(pnLifecycle, "Show comments");
			}
		});
		
		btEventLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) pnLifecycle.getLayout();
				cl.show(pnLifecycle, "Show events");
			}
		});
	}
	
	public void setAdditionalProperties() {
		btSelfAssign.setToolTipText("Назначи задачата на себе си");
		btSelfAssign.setBorder(null);
		btSelfAssign.setOpaque(false);
		btSelfAssign.setContentAreaFilled(false);
		btSelfAssign.setBorderPainted(false);
		btSelfAssign.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btSelfAssign.setPressedIcon(new ImageIcon("files/pix/self_assign_pressed.png"));
		
		btAssign.setToolTipText("Назначи задачата на някого");
		btAssign.setBorder(null);
		btAssign.setOpaque(false);
		btAssign.setContentAreaFilled(false);
		btAssign.setBorderPainted(false);
		btAssign.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btAssign.setPressedIcon(new ImageIcon("files/pix/assign_pressed.png"));
		
		btAcceptAssignment.setToolTipText("Приеми задачата");
		btAcceptAssignment.setBorder(null);
		btAcceptAssignment.setOpaque(false);
		btAcceptAssignment.setContentAreaFilled(false);
		btAcceptAssignment.setBorderPainted(false);
		btAcceptAssignment.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btAcceptAssignment.setPressedIcon(new ImageIcon("files/pix/accept_button_pressed.png"));
		
		btRejectAssignment.setToolTipText("Отхвърли задачата");
		btRejectAssignment.setBorder(null);
		btRejectAssignment.setOpaque(false);
		btRejectAssignment.setContentAreaFilled(false);
		btRejectAssignment.setBorderPainted(false);
		btRejectAssignment.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btRejectAssignment.setPressedIcon(new ImageIcon("files/pix/reject_button_pressed.png"));
		
		btReturnForTesting.setToolTipText("Поискай тест дали е готово");
		btReturnForTesting.setBorder(null);
		btReturnForTesting.setOpaque(false);
		btReturnForTesting.setContentAreaFilled(false);
		btReturnForTesting.setBorderPainted(false);
		btReturnForTesting.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btReturnForTesting.setPressedIcon(new ImageIcon("files/pix/test_return_button_pressed.png"));
		
		btSolve.setToolTipText("Задачата е решена. Затвори я.");
		btSolve.setBorder(null);
		btSolve.setOpaque(false);
		btSolve.setContentAreaFilled(false);
		btSolve.setBorderPainted(false);
		btSolve.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btSolve.setPressedIcon(new ImageIcon("files/pix/solve_button_pressed.png"));
		
		btNotSolved.setToolTipText("Задачата не е решена. Върни я.");
		btNotSolved.setBorder(null);
		btNotSolved.setOpaque(false);
		btNotSolved.setContentAreaFilled(false);
		btNotSolved.setBorderPainted(false);
		btNotSolved.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btNotSolved.setPressedIcon(new ImageIcon("files/pix/not_solved_button_pressed.png"));
		
		btComments.setToolTipText("Коментари");
		btComments.setBorder(null);
		btComments.setOpaque(false);
		btComments.setContentAreaFilled(false);
		btComments.setBorderPainted(false);
		btComments.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btComments.setSelectedIcon(new ImageIcon("files/pix/comments_pressed.png"));
		
		btEventLog.setToolTipText("Събития");
		btEventLog.setBorder(null);
		btEventLog.setOpaque(false);
		btEventLog.setContentAreaFilled(false);
		btEventLog.setBorderPainted(false);
		btEventLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btEventLog.setSelectedIcon(new ImageIcon("files/pix/event_log_pressed.png"));
		
		lbDescription.setVerticalTextPosition(JLabel.TOP);
		this.setVisible(false);
	}
	
	public void showInfo(int id, String name, int creatorId, String creator, int assigneeId, String assignee, int statusId, String status, ImageIcon statusIcon, String description,
							ArrayList<EventLogEntry> events) {
		setCurrentEvents(events);
		this.setVisible(true);
		
		String assigneeInfo = assignee == null?"Не е назначен":"Назначен на: ".concat(assignee);
		
		lbId.setText(id + "");
		lbName.setText("<html>".concat(name).concat("</html>"));
		lbCreator.setText("Създал: ".concat(creator));
		lbAssignee.setText(assigneeInfo);
		lbStatus.setText("<html>Статус:<br />".concat(status).concat("</html>"));
		lbStatusIcon.setIcon(statusIcon);
		lbDescription.setText("<html>".concat(description).concat("</html>"));
		
		btSelfAssign.setVisible(assignee == null);
		btAssign.setVisible((assignee == null || statusId == 4) && MainFrame.loggedInUserId == creatorId);
		btAcceptAssignment.setVisible(assigneeId == MainFrame.loggedInUserId && statusId == 2);
		btRejectAssignment.setVisible(assigneeId == MainFrame.loggedInUserId && statusId == 2);
		btReturnForTesting.setVisible(assigneeId == MainFrame.loggedInUserId && (statusId == 5 || statusId == 8) && creatorId != assigneeId);
		btSolve.setVisible((creatorId == MainFrame.loggedInUserId && statusId == 6) || (creatorId == assigneeId && statusId == 5));
		btNotSolved.setVisible(creatorId == MainFrame.loggedInUserId && statusId == 6);
		
		createDiscussionLayout();
		createEventLogLayout();
	}
	
	public void clearInfo() {
		lbId.setText("");
		lbName.setText("");
		lbCreator.setText("");
		lbAssignee.setText("");
		lbStatus.setText("");
		lbStatusIcon.setIcon(null);
		lbDescription.setText("");
		
		this.setVisible(false);
	}
	
	public void createDiscussionLayout() {
		pnDiscussion.removeAll();
		
		GroupLayout layout = new GroupLayout(pnDiscussion);
		pnDiscussion.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup horizontalGroup = layout.createParallelGroup(Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		
		currentEvents.stream().filter(event -> event.getEventTypeId() == 1).forEach(event -> {
			JLabel lCreator = new JLabel(event.getEventCreatorName());
			JLabel lTime = new JLabel("(" + new Timestamp(event.getEventDate()).toString() + ")");
			JLabel lContent = new JLabel(event.getComment());
			
			horizontalGroup
				.addGroup(layout.createSequentialGroup()
					.addComponent(lCreator)
					.addComponent(lTime));
			horizontalGroup.addComponent(lContent);
			
			verticalGroup
				.addGroup(layout.createParallelGroup()
					.addComponent(lCreator)
					.addComponent(lTime));
			verticalGroup
				.addComponent(lContent)
				.addGap(10);
		});
		
		layout.setHorizontalGroup(horizontalGroup);
		layout.setVerticalGroup(verticalGroup);
	}
	
	public void createEventLogLayout() {
		pnEventLog.removeAll();
		
		GroupLayout layout = new GroupLayout(pnEventLog);
		pnEventLog.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup horizontalGroup = layout.createParallelGroup(Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		
		currentEvents.stream().filter(event -> event.getEventTypeId() != 1).forEach(event -> {
			JLabel lCreator = new JLabel(event.getEventCreatorName());
			JLabel lTime = new JLabel("(" + new Timestamp(event.getEventDate()).toString() + ")");
			JLabel lContent = new JLabel(event.getComment());
			
			horizontalGroup
				.addGroup(layout.createSequentialGroup()
					.addComponent(lCreator)
					.addComponent(lTime));
			horizontalGroup.addComponent(lContent);
			
			verticalGroup
				.addGroup(layout.createParallelGroup()
					.addComponent(lCreator)
					.addComponent(lTime));
			verticalGroup
				.addComponent(lContent)
				.addGap(10);
		});
		
		layout.setHorizontalGroup(horizontalGroup);
		layout.setVerticalGroup(verticalGroup);
	}

	public int getSelectedIssueId() {
		return selectedIssueId;
	}

	public void setSelectedIssueId(int selectedIssueId) {
		this.selectedIssueId = selectedIssueId;
	}

	public ArrayList<EventLogEntry> getCurrentEvents() {
		return currentEvents;
	}

	public void setCurrentEvents(ArrayList<EventLogEntry> currentEvents) {
		this.currentEvents = currentEvents;
	}
}