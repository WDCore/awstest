package panels;

import helpers.Loggo;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import main.MainFrame;
import entities.Issue;

public class IssueListPanel extends JPanel {
	private static final long serialVersionUID = -8964513532226851567L;
	ArrayList<IssueInfoPanel> panels;
	public JScrollPane scrlPane;
	JScrollBar scrlBar;
	JPanel pnIssueList, pnSearch;
	public IssueDetailPanel pnView;
	public JLabel lbSearch, lbProjectName, lbType, lbStatus, lbTypeChosen, lbStatusChosen;
	public JTextField tfSearch;
	public JButton btBackToProjects, btAddIssue;
	Timer searchFieldTimer = new Timer();
	private int currentTypeId = 0, currentProjectId = 0, currentStatusId = 0;
	JRadioButton rbTypeIssues, rbTypeBugs, rbTypeFeatureRequests, rbStatusAll, rbStatusNew, rbStatusAssigned, rbStatusInProgress, rbStatusTesting, rbStatusClosed;
	ButtonGroup filterButtonsType, filterButtonsStatus;
	
	public IssueListPanel() {
		initialize();
		setSizes();
		createLayouts();
		setListeners();
		setAdditionalProperties();
	}
	
	public void initialize() {
		scrlPane = new JScrollPane();
		scrlBar = scrlPane.getVerticalScrollBar();
		panels = new ArrayList<>();
		pnIssueList = new JPanel();
		pnSearch = new JPanel();
		pnView = new IssueDetailPanel();
		lbSearch = new JLabel(new ImageIcon("files/pix/search.png"));
		lbProjectName = new JLabel();
		lbType = new JLabel("Type");
		lbStatus = new JLabel("Status");
		lbTypeChosen = new JLabel();
		lbStatusChosen = new JLabel();
		btBackToProjects = new JButton(new ImageIcon("files/pix/back_to_projects.png"));
		btAddIssue = new JButton(new ImageIcon("files/pix/new_issue.png"));
		tfSearch = new JTextField();
		filterButtonsType = new ButtonGroup();
		rbTypeIssues = new JRadioButton(new ImageIcon("files/pix/project_issues_small.png"));
		rbTypeBugs = new JRadioButton(new ImageIcon("files/pix/project_bugs_small.png"));
		rbTypeFeatureRequests = new JRadioButton(new ImageIcon("files/pix/project_enhancements_small.png"));
		filterButtonsStatus = new ButtonGroup();
		rbStatusAll = new JRadioButton(new ImageIcon("files/pix/empty_set.png"));
		rbStatusNew = new JRadioButton(new ImageIcon("files/pix/new_small.png"));
		rbStatusAssigned = new JRadioButton(new ImageIcon("files/pix/assigned_small.png"));
		rbStatusInProgress = new JRadioButton(new ImageIcon("files/pix/in_progress_small.png"));
		rbStatusTesting = new JRadioButton(new ImageIcon("files/pix/testing_small.png"));
		rbStatusClosed = new JRadioButton(new ImageIcon("files/pix/closed_small.png"));
		
		scrlPane.setViewportView(pnIssueList);
		scrlPane.getVerticalScrollBar().setUnitIncrement(16);
		scrlPane.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void setSizes() {
		tfSearch.setMaximumSize(new Dimension(150, 24));
	}
	
	public void refreshIssuePanels(ArrayList<Issue> list, int projectId, int typeId, int statusId) {
		setCurrentProjectId(projectId);
		setCurrentTypeId(typeId);
		setCurrentStatusId(statusId);
		pnIssueList.removeAll();
		panels.clear();
		
		for (Iterator<Issue> iterator = list.iterator(); iterator.hasNext();) {
			panels.add(new IssueInfoPanel(iterator.next(), MainFrame.loggedInUserId));
		}
		
		setTypeFilterButtons(getCurrentTypeId());
		setStatusFilterButtons(getCurrentStatusId());
		filterPanels(tfSearch.getText(), getCurrentTypeId(), getCurrentStatusId());
		
		createScrollPanelLayout();
	}
	
	public void setTypeFilterButtons(int typeId) {
		switch (typeId) {
		case 1:
			rbTypeIssues.setSelected(true);
			lbTypeChosen.setText("Issues");
			break;
		case 2:
			rbTypeBugs.setSelected(true);
			lbTypeChosen.setText("Bugs");
			break;
		case 3:
			rbTypeFeatureRequests.setSelected(true);
			lbTypeChosen.setText("Requests");
			break;
		default:
			break;
		}
	}
	
	public void setStatusFilterButtons(int statusId) {
		for (Enumeration<AbstractButton> buttons = filterButtonsStatus.getElements(); buttons.hasMoreElements();) {
			buttons.nextElement().setSelected(true);
		}
		
		switch (statusId) {
		case 0:
			rbStatusAll.setSelected(true);
			lbStatusChosen.setText("All");
			break;
		case 1:
			rbStatusNew.setSelected(true);
			lbStatusChosen.setText("New");
			break;
		case 2:
			rbStatusAssigned.setSelected(true);
			lbStatusChosen.setText("Assigned");
			break;
		case 5:
			rbStatusInProgress.setSelected(true);
			lbStatusChosen.setText("In progress");
			break;
		case 6:
			rbStatusTesting.setSelected(true);
			lbStatusChosen.setText("Testing");
			break;
		case 7:
			rbStatusClosed.setSelected(true);
			lbStatusChosen.setText("Closed");
			break;
		default:
			break;
		}
	}
	
	public void setListeners() {
		tfSearch.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				applyDelayedFilter();
			}
			
			public void keyReleased(KeyEvent e) { }
			
			public void keyPressed(KeyEvent e) { }
		});
		
		rbTypeIssues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentTypeId(1);
				lbTypeChosen.setText("Issues");
				
				applyDelayedFilter();
			}
		});
		
		rbTypeBugs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentTypeId(2);
				lbTypeChosen.setText("Bugs");
				
				applyDelayedFilter();
			}
		});
		
		rbTypeFeatureRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentTypeId(3);
				lbTypeChosen.setText("Requests");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(0);
				lbStatusChosen.setText("All");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(1);
				lbStatusChosen.setText("New");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusAssigned.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(2);
				lbStatusChosen.setText("Assigned");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusInProgress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(5);
				lbStatusChosen.setText("In Progress");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusTesting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(6);
				lbStatusChosen.setText("Testing");
				
				applyDelayedFilter();
			}
		});
		
		rbStatusClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentStatusId(7);
				lbStatusChosen.setText("Closed");
				
				applyDelayedFilter();
			}
		});
	}
	
	public void applyDelayedFilter() {
		searchFieldTimer.cancel();
		searchFieldTimer = new Timer();
		
		searchFieldTimer.schedule(new TimerTask() {
			public void run() {
				filterPanels(tfSearch.getText(), getCurrentTypeId(), getCurrentStatusId());
			}
		}, 500);
	}
	
	public void createLayouts() {
		filterButtonsType.add(rbTypeIssues);
		filterButtonsType.add(rbTypeBugs);
		filterButtonsType.add(rbTypeFeatureRequests);
		
		filterButtonsStatus.add(rbStatusAll);
		filterButtonsStatus.add(rbStatusNew);
		filterButtonsStatus.add(rbStatusAssigned);
		filterButtonsStatus.add(rbStatusInProgress);
		filterButtonsStatus.add(rbStatusTesting);
		filterButtonsStatus.add(rbStatusClosed);
		
		/* Search Panel Layout */
		GroupLayout layout = new GroupLayout(pnSearch);
		pnSearch.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout = new GroupLayout(pnSearch);
		pnSearch.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel l1 = new JLabel("|");
		JLabel l2 = new JLabel("|");
		JLabel l3 = new JLabel("|");
		JLabel l4 = new JLabel("|");
		JLabel l5 = new JLabel("|");
		JLabel l6 = new JLabel("|");
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(lbType)
				.addGroup(layout.createSequentialGroup()
					.addComponent(rbTypeIssues)
					.addComponent(rbTypeBugs)
					.addComponent(rbTypeFeatureRequests))
				.addComponent(lbTypeChosen))
			.addGap(15)
			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(l1)
				.addComponent(l2)
				.addComponent(l3))
			.addGap(15)
			.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(lbStatus)
				.addGroup(layout.createSequentialGroup()
					.addComponent(rbStatusAll)
					.addComponent(rbStatusNew)
					.addComponent(rbStatusAssigned)
					.addComponent(rbStatusInProgress)
					.addComponent(rbStatusTesting)
					.addComponent(rbStatusClosed))
				.addComponent(lbStatusChosen))
			.addGap(15)
			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(l4)
				.addComponent(l5)
				.addComponent(l6))
			.addGap(15)
			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(btBackToProjects)
					.addComponent(lbProjectName))
				.addGroup(layout.createSequentialGroup()
					.addComponent(lbSearch)
					.addComponent(tfSearch))));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(lbType)
				.addComponent(l1)
				.addComponent(lbStatus)
				.addComponent(l4)
				.addComponent(btBackToProjects)
				.addComponent(lbProjectName))
			.addGroup(layout.createParallelGroup()
				.addComponent(rbTypeIssues)
				.addComponent(rbTypeBugs)
				.addComponent(rbTypeFeatureRequests)
				.addComponent(l2)
				.addComponent(rbStatusAll)
				.addComponent(rbStatusNew)
				.addComponent(rbStatusAssigned)
				.addComponent(rbStatusInProgress)
				.addComponent(rbStatusTesting)
				.addComponent(rbStatusClosed)
				.addComponent(l5)
				.addComponent(lbSearch)
				.addComponent(tfSearch))
			.addGroup(layout.createParallelGroup()
				.addComponent(lbTypeChosen)
				.addComponent(l3)
				.addComponent(lbStatusChosen)
				.addComponent(l6)));
		
		/* Entire Panel Layout */
		layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(btAddIssue)
			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(pnSearch)
				.addGroup(layout.createSequentialGroup()
					.addComponent(scrlBar)
					.addComponent(scrlPane)
					.addComponent(pnView))));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(pnSearch)
			.addGroup(layout.createParallelGroup()
				.addComponent(btAddIssue)
				.addComponent(scrlBar)
				.addComponent(scrlPane)
				.addComponent(pnView)));
	}
	
	public void createScrollPanelLayout() {
		GroupLayout layout = new GroupLayout(pnIssueList);
		pnIssueList.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup horizontalGroup = layout.createParallelGroup(Alignment.TRAILING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		
		panels.stream().sorted((IssueInfoPanel p1, IssueInfoPanel p2) -> p1.getIssue().getName().compareToIgnoreCase(p2.getIssue().getName())).forEach(panel -> {
			horizontalGroup.addComponent(panel, 0, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
			verticalGroup.addComponent(panel);
		});
		
		layout.setHorizontalGroup(horizontalGroup);
		
		layout.setVerticalGroup(verticalGroup);
	}
	
	public void setAdditionalProperties() {
		scrlPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		rbTypeIssues.setSelectedIcon(new ImageIcon("files/pix/project_issues_small_pressed.png"));
		rbTypeBugs.setSelectedIcon(new ImageIcon("files/pix/project_bugs_small_pressed.png"));
		rbTypeFeatureRequests.setSelectedIcon(new ImageIcon("files/pix/project_enhancements_small_pressed.png"));
		
		rbTypeIssues.setToolTipText("Issues");
		rbTypeBugs.setToolTipText("Bugs");
		rbTypeFeatureRequests.setToolTipText("Feature Requests");
		
		rbStatusAll.setSelectedIcon(new ImageIcon("files/pix/empty_set_pressed.png"));
		rbStatusNew.setSelectedIcon(new ImageIcon("files/pix/new_small_pressed.png"));
		rbStatusAssigned.setSelectedIcon(new ImageIcon("files/pix/assigned_small_pressed.png"));
		rbStatusInProgress.setSelectedIcon(new ImageIcon("files/pix/in_progress_small_pressed.png"));
		rbStatusTesting.setSelectedIcon(new ImageIcon("files/pix/testing_small_pressed.png"));
		rbStatusClosed.setSelectedIcon(new ImageIcon("files/pix/closed_small_pressed.png"));
		
		rbStatusAll.setToolTipText("All");
		rbStatusNew.setToolTipText("New");
		rbStatusAssigned.setToolTipText("Assigned");
		rbStatusInProgress.setToolTipText("In Progress");
		rbStatusTesting.setToolTipText("Testing");
		rbStatusClosed.setToolTipText("Closed");
		
		btBackToProjects.setBorder(null);
		btBackToProjects.setOpaque(false);
		btBackToProjects.setContentAreaFilled(false);
		btBackToProjects.setBorderPainted(false);
		btBackToProjects.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btBackToProjects.setVerticalTextPosition(SwingConstants.BOTTOM);
		btBackToProjects.setHorizontalTextPosition(SwingConstants.CENTER);
		btBackToProjects.setPressedIcon(new ImageIcon("files/pix/back_to_projects_pressed.png"));
		
		btAddIssue.setBorder(null);
		btAddIssue.setOpaque(false);
		btAddIssue.setContentAreaFilled(false);
		btAddIssue.setBorderPainted(false);
		btAddIssue.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btAddIssue.setVerticalTextPosition(SwingConstants.BOTTOM);
		btAddIssue.setHorizontalTextPosition(SwingConstants.CENTER);
		btAddIssue.setPressedIcon(new ImageIcon("files/pix/new_issue_pressed.png"));
	}
	
	public void filterPanels(String searchString, int typeId, int statusId) {
		Loggo.log("Type: " + typeId + " / Status: " + statusId);
		
		panels.stream().forEach(panel -> {
			int searchStringId = 0;
			
			try {
				searchStringId =  Integer.parseInt(searchString);
			}
			catch(NumberFormatException e) { }
			
			if((panel.getIssue().getName().toLowerCase().contains(searchString.toLowerCase()) || panel.getIssue().getId() == searchStringId)
					&& panel.getIssue().getTypeId() == typeId
					&& (panel.getIssue().getStatusId() == statusId || (statusId == 0 && panel.getIssue().getStatusId() != 7))) {
				panel.setVisible(true);
			}
			else {
				panel.setVisible(false);
			}
		});
		
		scrlPane.getVerticalScrollBar().setValue(0);
	}

	public ArrayList<IssueInfoPanel> getPanels() {
		return panels;
	}
	
	public void addPanel(IssueInfoPanel newPanel) {
		panels.add(newPanel);
	}

	public int getCurrentTypeId() {
		return currentTypeId;
	}

	public void setCurrentTypeId(int currentTypeId) {
		this.currentTypeId = currentTypeId;
	}

	public int getCurrentProjectId() {
		return currentProjectId;
	}

	public void setCurrentProjectId(int currentProjectId) {
		this.currentProjectId = currentProjectId;
	}

	public int getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(int currentStatusId) {
		this.currentStatusId = currentStatusId;
	}
}