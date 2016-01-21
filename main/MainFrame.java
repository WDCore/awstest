package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import entities.EventLogEntry;
import entities.Issue;
import entities.IssueCategory;
import entities.IssueType;
import entities.Project;
import entities.Status;
import entities.User;
import helpers.DatabaseUtility;
import helpers.Loggo;
import panels.IssueEditPanel;
import panels.IssueListPanel;
import panels.ProjectEditPanel;
import panels.ProjectListPanel;
import panels.UsersEditPanel;
import panels.visuals.OMultipleOption;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1255478697242466134L;
	public static int loggedInUserId = 0;
	JPanel pnMain, pnPrimary;
	JLabel lbLoggedUser;
	JButton btLogout;
	JMenuBar menuBar;
	JMenu menuFile, menuView, menuHelp, menuProjects, menuIssues;
	JMenuItem miNew, miOpen, miSave, miAbout, miProjectsList, miTeams, miIssuesList, miIssuesNew;
	JButton btPeople, btProjecs, btSettings;
	IssueListPanel pnIssueList;
	IssueEditPanel pnIssueEdit;
	ProjectEditPanel pnProject;
	UsersEditPanel pnUsers;
	ProjectListPanel pnProjectList;
	HashMap<String, Integer> hmUsers, hmCategory;
	ArrayList<Issue> listIssues;
	ArrayList<IssueType> listIssueTypes;
	@XStreamAlias("statuses")
	ArrayList<Status> listStatuses;
	ArrayList<Project> listProjects;
	ArrayList<User> listUsers;
	ArrayList<IssueCategory> listIssueCategories;
	ArrayList<EventLogEntry> listEvents;
	DatabaseUtility dbInstance;
	
	public MainFrame (int loggedInUserId)
	{
		this.loggedInUserId = loggedInUserId;
		initialize();
		readDatabase();
		postInitialize();
		
		createMenuBar();
		
		setSizes();
		setValues();
		createLayouts();
		setListeners();
		setAdditionalProperties();
		
//		this.setJMenuBar(menuBar);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(960, 720));
		this.setLocationRelativeTo(null);
		setTitle("TrackIssue");
		setVisible(true);
		
		if(Primary.settings.get("startOnSecondScreen")) {
			GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			
			this.setLocation(gs[1].getDefaultConfiguration().getBounds().x + (gs[1].getDefaultConfiguration().getBounds().width - this.getWidth())/2,
					gs[1].getDefaultConfiguration().getBounds().y + (gs[1].getDefaultConfiguration().getBounds().height - this.getWidth())/2);
		}
	}
	
	
	public void createMenuBar()
	{
		menuFile.add(miNew);
		menuFile.add(miOpen);
		menuFile.add(miSave);
		
		menuHelp.add(miAbout);
		
		menuProjects.add(miProjectsList);
		
		menuIssues.add(miIssuesList);
		menuIssues.add(miIssuesNew);
		
		menuBar.add(menuFile);
		menuBar.add(menuProjects);
		menuBar.add(menuIssues);
		menuBar.add(menuHelp);
	}
	
	public void setSizes()
	{
		
	}
	
	public void setValues() {
		lbLoggedUser.setText(listUsers.stream()
										.filter(user -> { return user.getId() == loggedInUserId; }).map(User::getName)
										.collect(Collectors.toList())
										.toArray(new String[]{})[0]);
	}
	
	public void createLayouts()
	{
		GroupLayout layout = new GroupLayout(pnPrimary);
		pnPrimary.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lbLoggedUser)
					.addComponent(btLogout))
				.addComponent(pnMain));
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addComponent(lbLoggedUser)
					.addComponent(btLogout))
				.addComponent(pnMain));
		
		pnMain.setLayout(new CardLayout());
		pnMain.add(pnProjectList, "List All Projects");
		pnMain.add(pnIssueEdit, "Edit Issue");
		pnMain.add(pnIssueList, "List All Issues");
//		pnMain.add(pnPersonalView, "Personal view");
		
		this.add(pnPrimary);
	}
	
	public void initialize()
	{
		dbInstance = new DatabaseUtility();
		hmUsers = new HashMap<>();
		hmCategory = new HashMap<>();
		
		listIssues = new ArrayList<Issue>();
		listIssueTypes = new ArrayList<IssueType>();
		listProjects = new ArrayList<Project>();
		listUsers = new ArrayList<User>();
		listIssueCategories = new ArrayList<IssueCategory>();
		listStatuses = new ArrayList<Status>();
		listEvents = new ArrayList<EventLogEntry>();
		
		lbLoggedUser = new JLabel();
		btLogout = new JButton(new ImageIcon("files/pix/logout.png"));
		
		pnPrimary = new JPanel();
		pnMain = new JPanel();
		
		pnIssueList = new IssueListPanel();
		pnProject = new ProjectEditPanel();
		pnProjectList = new ProjectListPanel();
		pnUsers = new UsersEditPanel();
		
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuView = new JMenu("View");
		menuHelp = new JMenu("Help");
		menuProjects = new JMenu("Projects");
		menuIssues = new JMenu("Issues");
		miNew = new JMenuItem("New");
		miOpen = new JMenuItem("Open");
		miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		miSave = new JMenuItem("Save", KeyEvent.VK_S);
		miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		miAbout = new JMenuItem("About", KeyEvent.VK_A);
		miAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		miProjectsList = new JMenuItem("List Projects");
		miProjectsList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		miIssuesList = new JMenuItem("List Issues");
		miIssuesList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
		miIssuesNew = new JMenuItem("New Issue");
		miTeams = new JMenuItem("Teams");
	}
	
	public void postInitialize() {
		pnIssueEdit = new IssueEditPanel(
			new ArrayList<OMultipleOption>(listIssueTypes.stream().map(type -> {
				return new OMultipleOption(type.getId(), type.getName());
			})
			.collect(Collectors.toList())),
			new ArrayList<OMultipleOption>(listProjects.stream().map(project -> {
				return new OMultipleOption(project.getId(), project.getName());
			})
			.collect(Collectors.toList())),
			new ArrayList<OMultipleOption>(listUsers.stream().map(user -> {
				return new OMultipleOption(user.getId(), user.getName());
			})
			.collect(Collectors.toList())),
			new ArrayList<OMultipleOption>(listStatuses.stream().map(status -> {
				return new OMultipleOption(status.getId(), status.getName());
			})
			.collect(Collectors.toList()))
		);
	}
	
	public void setListeners()
	{
		this.addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void windowClosing(WindowEvent e) {
				customClose();
			}
			
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		pnIssueEdit.btSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(pnIssueEdit.validateForm())
				{
					pnIssueEdit.applyChanges();
					
					Integer highestId = listIssues.stream().map(Issue::getId).reduce(Integer::max).get();
					
					if(pnIssueEdit.currentIssue.getId() == 0) {
						if(!dbInstance.hasConnectivity()) {
							pnIssueEdit.currentIssue.setId(highestId + 1);
						}
						
						listIssues.add(pnIssueEdit.currentIssue);
						listEvents.add(Loggo.logEvent(pnIssueEdit.currentIssue.getId(), 2, ""));
					}
					else {
						listEvents.add(Loggo.logEvent(pnIssueEdit.currentIssue.getId(), 3, ""));
					}
					
					pnIssueEdit.currentIssue.save();
					pnIssueEdit.clearValues();
					
					if(Primary.settings.get("exportToXML")) {
						exportToXML("issue");
						exportToXML("event");
					}
					
					if(pnIssueList.getCurrentProjectId() > 0) {
						filterIssuesList(pnIssueList.getCurrentProjectId(), pnIssueEdit.currentIssue.getTypeId(), pnIssueEdit.currentIssue.getStatusId());
						miIssuesList.doClick();
					}
					else {
						miProjectsList.doClick();
					}
				}
			}
		});
		
		pnIssueEdit.btCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.btBackToProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miProjectsList.doClick();
			}
		});
		
		miIssuesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateViewPanel();
				
				CardLayout cl = (CardLayout) pnMain.getLayout();
				cl.show(pnMain, "List All Issues");
			}
		});
		
		miProjectsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStatistics();
				
				CardLayout cl = (CardLayout) pnMain.getLayout();
				cl.show(pnMain, "List All Projects");
			}
		});
		
		miIssuesNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueEdit.currentIssue = new Issue();
				pnIssueEdit.currentIssue.setProjectId(pnIssueList.getCurrentProjectId());
				pnIssueEdit.currentIssue.setTypeId(pnIssueList.getCurrentTypeId());
				pnIssueEdit.fillForm();
				
				CardLayout cl = (CardLayout) pnMain.getLayout();
				cl.show(pnMain, "Edit Issue");
			}
		});
		
		createIssueListeners();
		
		pnProjectList.getPanels().stream().forEach(panel -> {
			panel.bIssuesList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 1, 0);
				}
			});
			
			panel.btIssueCountNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 1, 1);
				}
			});
			
			panel.btIssueCountAssigned.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 1, 2);
				}
			});
			
			panel.btIssueCountInProgress.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 1, 5);
				}
			});
			
			panel.btIssueCountTesting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 1, 6);
				}
			});
			
			panel.bBugsList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 2, 0);
				}
			});
			
			panel.btBugCountNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 2, 1);
				}
			});
			
			panel.btBugCountAssigned.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 2, 2);
				}
			});
			
			panel.btBugCountInProgress.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 2, 5);
				}
			});
			
			panel.btBugCountTesting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 2, 6);
				}
			});
			
			panel.bFeaturesList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 3, 0);
				}
			});
			
			panel.btFeatureCountNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 3, 1);
				}
			});
			
			panel.btFeatureCountAssigned.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 3, 2);
				}
			});
			
			panel.btFeatureCountInProgress.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 3, 5);
				}
			});
			
			panel.btFeatureCountTesting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterIssuesList(panel.getProjectId(), 3, 6);
				}
			});
		});
		
		btLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logout();
			}
		});
		
		pnIssueList.pnView.btSelfAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					panel.getIssue().setAssigneeId(loggedInUserId);
					panel.getIssue().setStatusId(5);
					panel.getIssue().save();
					Loggo.logEvent(panel.getIssue().getId(), 1, "");
					Loggo.logEvent(panel.getIssue().getId(), 2, "");
					panel.updateButtonVisibility(loggedInUserId);
					panel.validate();
					panel.repaint();
					
					if(Primary.settings.get("exportToXML")) {
						exportToXML("issue");
					}
				});
				
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.pnView.btAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					panel.bEdit.doClick();
				});
			}
		});
		
		pnIssueList.pnView.btAcceptAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					panel.getIssue().setStatusId(5);
					panel.getIssue().save();
					Loggo.logEvent(panel.getIssue().getId(), 2, "");
					panel.updateButtonVisibility(loggedInUserId);
					panel.validate();
					panel.repaint();
					
					if(Primary.settings.get("exportToXML")) {
						exportToXML("issue");
					}
					
					pnIssueList.setCurrentStatusId(panel.getIssue().getStatusId());
					pnIssueList.setStatusFilterButtons(panel.getIssue().getStatusId());
				});
				
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.pnView.btRejectAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					String explanation = JOptionPane.showInputDialog(null, "Причина за отказ?", "Отказ", JOptionPane.QUESTION_MESSAGE);
					
					if(explanation != null) {
						panel.getIssue().setStatusId(4);
						panel.getIssue().save();
						Loggo.logEvent(panel.getIssue().getId(), 3, explanation);
						panel.updateButtonVisibility(loggedInUserId);
						panel.validate();
						panel.repaint();
						
						if(Primary.settings.get("exportToXML")) {
							exportToXML("issue");
						}
						
						pnIssueList.setCurrentStatusId(panel.getIssue().getStatusId());
						pnIssueList.setStatusFilterButtons(panel.getIssue().getStatusId());
					}
				});
				
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.pnView.btReturnForTesting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					panel.getIssue().setStatusId(6);
					panel.getIssue().save();
					Loggo.logEvent(panel.getIssue().getId(), 5, "");
					panel.updateButtonVisibility(loggedInUserId);
					panel.validate();
					panel.repaint();
					
					if(Primary.settings.get("exportToXML")) {
						exportToXML("issue");
					}
					
					pnIssueList.setCurrentStatusId(panel.getIssue().getStatusId());
					pnIssueList.setStatusFilterButtons(panel.getIssue().getStatusId());
				});
				
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.pnView.btSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					panel.getIssue().setStatusId(7);
					panel.getIssue().save();
					Loggo.logEvent(panel.getIssue().getId(), 6, "");
					panel.updateButtonVisibility(loggedInUserId);
					panel.validate();
					panel.repaint();
					
					if(Primary.settings.get("exportToXML")) {
						exportToXML("issue");
					}
					
					pnIssueList.setCurrentStatusId(panel.getIssue().getStatusId());
					pnIssueList.setStatusFilterButtons(panel.getIssue().getStatusId());
				});
				
				miIssuesList.doClick();
			}
		});
		
		pnIssueList.pnView.btNotSolved.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnIssueList.getPanels().stream().filter(panel -> panel.isSelected()).forEach(panel -> {
					String explanation = JOptionPane.showInputDialog(null, "Какво не е наред?", "Провален тест", JOptionPane.QUESTION_MESSAGE);
					
					if(explanation != null) {
						panel.getIssue().setStatusId(8);
						panel.getIssue().save();
						Loggo.logEvent(panel.getIssue().getId(), 7, explanation);
						panel.updateButtonVisibility(loggedInUserId);
						panel.validate();
						panel.repaint();
						
						if(Primary.settings.get("exportToXML")) {
							exportToXML("issue");
						}
						
						pnIssueList.setCurrentStatusId(panel.getIssue().getStatusId());
						pnIssueList.setStatusFilterButtons(panel.getIssue().getStatusId());
					}
				});
				
				miIssuesList.doClick();
			}
		});
	}
	
	public void createIssueListeners() {
		pnIssueList.btAddIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miIssuesNew.doClick();
			}
		});
		
		pnIssueList.getPanels().stream().forEach(panel -> {
			panel.bView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pnIssueList.getPanels().stream().forEach(pn -> {
						if(pn.getIssue().getId() != panel.getIssue().getId()) {
							pn.setSelected(false);
							pn.repaint();
						}
					});
					
					panel.setSelected(true);
					panel.repaint();
					
					updateViewPanel();
				}
			});
			
			panel.bEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pnIssueEdit.fillForm(panel.getIssue());
					
					CardLayout cl = (CardLayout) pnMain.getLayout();
					cl.show(pnMain, "Edit Issue");
				}
			});
			
			panel.bDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showOptionDialog(null, "Do you really want to delete the issue?", "Delete issue #" + panel.getIssue().getId(), 0, 0, null, new String[]{"Yes", "No"}, 1) == 0) {
						for (Iterator<Issue> iterator = listIssues.iterator(); iterator.hasNext();) {
							Issue issue = iterator.next();
							
							if(issue.getId() == panel.getIssue().getId() && issue.getName().equals(panel.getIssue().getName())) {
								issue.delete();
								iterator.remove();
							}
						}
						
						if(Primary.settings.get("exportToXML")) {
							exportToXML("issue");
						}
						
						filterIssuesList(panel.getIssue().getProjectId(), panel.getIssue().getTypeId(), pnIssueList.getCurrentStatusId());
					}
				}
			});
		});
	}
	
	public void setAdditionalProperties() {
		btLogout.setBorder(null);
		btLogout.setOpaque(false);
		btLogout.setContentAreaFilled(false);
		btLogout.setBorderPainted(false);
		btLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btLogout.setVerticalTextPosition(SwingConstants.BOTTOM);
		btLogout.setHorizontalTextPosition(SwingConstants.CENTER);
		btLogout.setPressedIcon(new ImageIcon("files/pix/logout.png"));
	}
	
	public void readDatabase() {
		readIssuesTypesFromDatabase();
		readUsersFromDatabase();
		readStatusesFromDatabase();
		readProjectsFromDatabase();
		readFromFileCategory("files\\Category.txt");
		readEventLogFromDatabase();
		readIssuesFromDatabase();
		
		updateStatistics();
	}
	
	public void readIssuesFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute("SELECT i.id, i.typeId, i.name, i.projectId, i.creatorId, i.assignedUserId, i.categoryId, i.statusId, i.description FROM issues i");
			
			try {
				while(rs.next()) {
					listIssues.add(new Issue(rs.getInt("id"),
											rs.getInt("typeId"),
											rs.getString("name"),
											rs.getInt("projectId"),
											rs.getInt("creatorId"),
											rs.getInt("assignedUserId"),
											rs.getInt("categoryId"),
											rs.getInt("statusId"),
											rs.getString("description")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			dbInstance.disconnect();
			
			Loggo.log("Issues read from DB");
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listIssues = (ArrayList<Issue>) xstream.fromXML(new File("files/xml/issue.xml"));
			
			listIssues.stream().forEach(issue -> issue.setStatusIcon(issue.getStatusId()));
			
			Loggo.log("Issues read from XML");
		}
	}
	
	public void readIssuesTypesFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute("SELECT it.id, it.name FROM issue_types it");
			
			try {
				while(rs.next()) {
					listIssueTypes.add(new IssueType(rs.getInt("id"),
											rs.getString("name")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			dbInstance.disconnect();
			
			Loggo.log("Issue types read from DB");
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listIssueTypes = (ArrayList<IssueType>) xstream.fromXML(new File("files/xml/type.xml"));
			
			Loggo.log("Issue types read from XML");
		}
	}
	
	public void readUsersFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute("SELECT u.id, u.first_name, u.middle_name, u.last_name FROM users u");
			
			try {
				while(rs.next()) {
					String middleName = rs.getString("middle_name");
					String fullName = rs.getString("first_name")
										.concat(middleName.length() > 0?" ".concat(middleName.substring(0, 1).concat(". ")):" ").concat(rs.getString("last_name"));
					
					listUsers.add(new User(rs.getInt("id"),
											fullName));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			dbInstance.disconnect();
			
			Loggo.log("Users read from DB");
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listUsers = (ArrayList<User>) xstream.fromXML(new File("files/xml/user.xml"));
			
			Loggo.log("Users read from XML");
		}
	}
	
	public void readStatusesFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute("SELECT s.id, s.name, s.phase FROM statuses s");
			
			try {
				while(rs.next()) {
					listStatuses.add(new Status(rs.getInt("id"),
												rs.getString("name"),
												rs.getInt("phase")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Loggo.log("Statuses read from DB");
			dbInstance.disconnect();
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listStatuses = (ArrayList<Status>) xstream.fromXML(new File("files/xml/status.xml"));
			
			Loggo.log("Statuses read from XML");
		}
	}
	
	public void readProjectsFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute("SELECT p.id, p.name, p.imageName FROM projects p");
			
			try {
				while(rs.next()) {
					listProjects.add(new Project(rs.getInt("id"), rs.getString("name"), rs.getString("imageName")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			dbInstance.disconnect();
			
			Loggo.log("Projects read from DB");
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listProjects = (ArrayList<Project>) xstream.fromXML(new File("files/xml/project.xml"));
			
			Loggo.log("Projects read from XML");
		}
		
		pnProjectList.listProjects(listProjects);
	}
	
	public void readEventLogFromDatabase() {
		if(dbInstance.hasConnectivity() && !Primary.settings.get("xmlMode")) {
			ResultSet rs = dbInstance.execute(
					"SELECT e.id, e.issueId, e.eventCreatorId, e.eventTypeId, e.comment, e.eventDate, e.seen, et.message eventMessage "
					+ "FROM events e "
					+ "JOIN event_types et on e.eventTypeId = et.id");
			
			try {
				while(rs.next()) {
					listEvents.add(new EventLogEntry(rs.getInt("id"),
														rs.getInt("issueId"),
														rs.getInt("eventCreatorId"),
														rs.getInt("eventTypeId"),
														rs.getString("comment"),
														rs.getLong("eventDate"),
														rs.getBoolean("seen"),
														rs.getString("eventMessage")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			dbInstance.disconnect();
			
			Loggo.log("Event log read from DB");
		}
		else {
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			
			listEvents = (ArrayList<EventLogEntry>) xstream.fromXML(new File("files/xml/events.xml"));
			
			Loggo.log("Events read from XML");
			
			listEvents.stream().forEach(event -> {
				event.setEventCreatorName(listUsers.stream().filter(user -> user.getId() == event.getEventCreatorId()).collect(Collectors.toList()).toArray(new User[]{})[0].getName());
			});
		}
	}
	
	public void readFromFileCategory(String path)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Scanner sc = new Scanner(br);
			
			while (sc.hasNext())
			{
	            String line = sc.nextLine();
	            String[] currentIssueData = line.split(":::");
	            hmCategory.put(currentIssueData[0], Integer.parseInt(currentIssueData[1]));
	            listIssueCategories.add(new IssueCategory(Integer.parseInt(currentIssueData[1]), currentIssueData[0]));
	        }
			
	        sc.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void filterIssuesList(int projectId, int typeId, int statusId) {
		Integer[] ids = pnIssueList.getPanels().stream()
				.filter(panel -> panel.isSelected())
				.map(panel -> {return panel.getIssue().getId();}).collect(Collectors.toList()).toArray(new Integer[]{});
		
		int selectedPanelIssueId = ids.length>0?ids[0]:0;
		
		pnIssueList.tfSearch.setText("");
		pnIssueList.lbProjectName.setText(listProjects.stream().filter(project -> project.getId() == projectId).map(Project::getName)
														.collect(Collectors.toList()).toArray(new String[]{})[0]);
		
		pnIssueList.refreshIssuePanels(listIssues.stream()
													.filter(issue -> issue.getProjectId() == projectId)
													.collect(Collectors.toCollection(ArrayList<Issue>::new)),
										projectId, typeId, statusId);
		createIssueListeners();
		
		pnIssueList.getPanels().stream().forEach(panel -> {
			if(panel.getIssue().getId() == selectedPanelIssueId) {
				panel.setSelected(true);
			}
		});
		
		miIssuesList.doClick();
	}
	
	public void customClose() {
		if(Primary.settings.get("exportToXML")) {
			String[] entities = new String[]{ "issue", "project", "type", "status", /*"user", */"category" };
			
			for (String entity : entities) {
				exportToXML(entity);
			}
		}
	}
	
	public boolean exportToXML(String entity) {
		XStream xstream = new XStream();
		xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		
		String xml = "";
		
		switch (entity) {
		case "issue":
			xml = xstream.toXML(listIssues);
			break;
		case "project":
			xml = xstream.toXML(listProjects);
			break;
		case "type":
			xml = xstream.toXML(listIssueTypes);
			break;
		case "status":
			xml = xstream.toXML(listStatuses);
			break;
		case "user":
			xml = xstream.toXML(listUsers);
			break;
		case "category":
			xml = xstream.toXML(listIssueCategories);
			break;
		case "event":
			xml = xstream.toXML(listEvents);
			break;
		default:
			break;
		}
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream("files/xml/" + entity + ".xml");
			
			fos.write(xml.getBytes("UTF-8"));
			
			Loggo.log(entity.substring(0, 1).toUpperCase() + entity.substring(1) + " exported to XML files");
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Loggo.logError(entity.substring(0, 1).toUpperCase() + entity.substring(1) + " NOT exported to XML files");
		
		return false;
	}
	
	public void updateStatistics() {
		pnProjectList.getPanels().stream().forEach(panel -> {
			panel.addIssueCount("issues_new",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 1 && issue.getStatusId() == 1)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("issues_assigned",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 1 && issue.getStatusId() == 2)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("issues_in_progress",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 1 && issue.getStatusId() == 5)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("issues_testing",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 1 && issue.getStatusId() == 6)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("bugs_new",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 2 && issue.getStatusId() == 1)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("bugs_assigned",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 2 && issue.getStatusId() == 2)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("bugs_in_progress",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 2 && issue.getStatusId() == 5)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("bugs_testing",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 2 && issue.getStatusId() == 6)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("requests_new",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 3 && issue.getStatusId() == 1)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("requests_assigned",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 3 && issue.getStatusId() == 2)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("requests_in_progress",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 3 && issue.getStatusId() == 5)
					.collect(Collectors.toList()).size());
			
			panel.addIssueCount("requests_testing",
					listIssues.stream().filter(issue -> issue.getProjectId() == panel.getProjectId() && issue.getTypeId() == 3 && issue.getStatusId() == 6)
					.collect(Collectors.toList()).size());
		});
	}
	
	public void logout() {
		Primary.settings.remove("autoLogin");
		Primary.settings.put("autoLogin", false);
		
		new LoginBox();
		this.dispose();
	}
	
	public void updateViewPanel() {
		pnIssueList.getPanels().stream().filter(panel -> {return panel.isSelected();}).forEach(panel -> {
			String[] asigneesNames = listUsers.stream().filter(user -> { return user.getId() == panel.getIssue().getAssigneeId(); })
					.map(User::getName).collect(Collectors.toList()).toArray(new String[]{});
			
			Integer[] asigneesIds = listUsers.stream().filter(user -> { return user.getId() == panel.getIssue().getAssigneeId(); })
					.map(User::getId).collect(Collectors.toList()).toArray(new Integer[]{});
			
			pnIssueList.pnView.btComments.setSelected(true);
			
			pnIssueList.pnView.showInfo(panel.getIssue().getId(),
					panel.getIssue().getName(),
					listUsers.stream().filter(user -> { return user.getId() == panel.getIssue().getCreatorId(); }).map(User::getId).collect(Collectors.toList()).toArray(new Integer[]{})[0],
					listUsers.stream().filter(user -> { return user.getId() == panel.getIssue().getCreatorId(); }).map(User::getName).collect(Collectors.toList()).toArray(new String[]{})[0],
					asigneesIds.length>0?asigneesIds[0]:0,
					asigneesNames.length>0?asigneesNames[0]:null,
					listStatuses.stream().filter(status -> { return status.getId() == panel.getIssue().getStatusId(); }).map(Status::getId).collect(Collectors.toList()).toArray(new Integer[]{})[0],
					listStatuses.stream().filter(status -> { return status.getId() == panel.getIssue().getStatusId(); }).map(Status::getName).collect(Collectors.toList()).toArray(new String[]{})[0],
					panel.getIssue().getStatusIcon(),
					panel.getIssue().getDescription(),
					listEvents.stream().filter(event -> event.getIssueId() == panel.getIssue().getId() &&
													(event.getEventCreatorId() == panel.getIssue().getCreatorId() || event.getEventCreatorId() == panel.getIssue().getAssigneeId()))
								.collect(Collectors.toCollection(ArrayList<EventLogEntry>::new)));
		});
	}
}