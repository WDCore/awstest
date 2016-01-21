package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.thoughtworks.xstream.XStream;

import entities.User;
import helpers.DatabaseUtility;
import helpers.Loggo;

public class LoginBox extends JFrame {
	private static final long serialVersionUID = -1279382875188214385L;
	MainFrame mf;
	JLabel lbUsername, lbPassword;
	JTextField tfUsername;
	JPasswordField tfPassword;
	JButton btLogin;
	MessageDigest md;
	ArrayList<User> users;
	
	public LoginBox() {
		initialize();
		
		if(Primary.settings.get("xmlMode")) {
			readUsers();
		}
		
		setSizes();
		createLayout();
		setListeners();
		setAdditionalProperties();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);		
		
		
		setTitle("Login");
		setVisible(true);
		
		if(Primary.settings.get("startOnSecondScreen")) {
			GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			
			if(gs.length > 1) {
				this.setLocation(gs[1].getDefaultConfiguration().getBounds().x + (gs[1].getDefaultConfiguration().getBounds().width - this.getWidth())/2,
						gs[1].getDefaultConfiguration().getBounds().y + (gs[1].getDefaultConfiguration().getBounds().height - this.getWidth())/2);
			}
		}
		
		if(Primary.settings.get("autoLogin")) {
			tfUsername.setText("origyn");
			tfPassword.setText("gosho");
			
			btLogin.doClick();
		}
	}
	
	public void initialize() {
		users = new ArrayList<>();
		lbUsername = new JLabel("Username");
		lbPassword = new JLabel("Password");
		tfUsername = new JTextField();
		tfPassword = new JPasswordField();
		btLogin = new JButton(new ImageIcon("files/pix/login.png"));
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setSizes() {
		tfUsername.setPreferredSize(new Dimension(300, 20));
	}
	
	public void createLayout() {
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addComponent(lbUsername)
			.addComponent(tfUsername)
			.addComponent(lbPassword)
			.addComponent(tfPassword)
			.addComponent(btLogin));
		
		layout.linkSize(tfUsername, tfPassword);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(lbUsername)
			.addComponent(tfUsername)
			.addComponent(lbPassword)
			.addComponent(tfPassword)
			.addGap(20)
			.addComponent(btLogin));
	}
	
	public void setListeners() {
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseUtility dbInstance = new DatabaseUtility();
				
				Object loggedInUserId = dbInstance.execute("SELECT * FROM users WHERE username = '" + tfUsername.getText() + "' AND password = '" +
															new BigInteger(1, md.digest(new String(tfPassword.getPassword()).getBytes())).toString(16) + "'",
															"id");
				
				if(loggedInUserId == null
						&& users.stream().filter(user-> { return user.getUsername().equals(tfUsername.getText())
								&& user.getPassword().equals(new BigInteger(1, md.digest(new String(tfPassword.getPassword()).getBytes())).toString(16)); } )
								.collect(Collectors.toList()).size() > 0) {
					loggedInUserId = users.stream().filter(user-> { return user.getUsername().equals(tfUsername.getText())
							&& user.getPassword().equals(new BigInteger(1, md.digest(new String(tfPassword.getPassword()).getBytes())).toString(16)); } )
							.map(User::getId)
							.collect(Collectors.toList()).toArray(new Integer[]{})[0];
					
					Loggo.log("User authentication through XML");
				}
				else {
					Loggo.log("User authentication through DB");
				}
				
				if(loggedInUserId != null) {
					login((int) loggedInUserId);
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong username and/or password");
				}
			}
		});
		
		tfUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btLogin.doClick();
			}
		});
		
		tfPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btLogin.doClick();
			}
		});
		
		tfUsername.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) { }
			
			public void focusGained(FocusEvent arg0) {
				((JTextField) arg0.getSource()).selectAll();
			}
		});
		
		tfPassword.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) { }
			
			public void focusGained(FocusEvent arg0) {
				((JTextField) arg0.getSource()).selectAll();
			}
		});
	}
	
	public void setAdditionalProperties() {
		btLogin.setBorder(null);
		btLogin.setOpaque(false);
		btLogin.setContentAreaFilled(false);
		btLogin.setBorderPainted(false);
		btLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btLogin.setVerticalTextPosition(SwingConstants.BOTTOM);
		btLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		btLogin.setPressedIcon(new ImageIcon("files/pix/login.png"));
	}
	
	public void login(int loggedInUserId) {
		mf = new MainFrame(loggedInUserId);
		this.dispose();
	}
	
	public void readUsers() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		
		users = (ArrayList<User>) xstream.fromXML(new File("files/xml/user.xml"));
	}
}