package panels;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import main.MainFrame;
import entities.Issue;
import helpers.FormField;
import helpers.IForm;
import panels.visuals.JForm;
import panels.visuals.OComboBox;
import panels.visuals.OMultipleOption;

public class IssueEditPanel extends JForm implements IForm<Issue>
{
	private static final long serialVersionUID = -3029608121596485996L;
	public JRadioButton rbIssue, rbBug, rbEnhancement;
	ButtonGroup cbGroup;
	HashMap<Integer, FormField> fields;
	public Issue currentIssue;
	
	@SafeVarargs
	public IssueEditPanel(ArrayList<OMultipleOption>... multipleOptions)
	{
		super(300, 20);
		initialize();
		populateFieldsData(multipleOptions);
		createForm(fields);
		setListeners();
		
		this.setVisible(true);
		this.setBorder(BorderFactory.createTitledBorder("Create new issue"));
	}
	
	public void initialize()
	{
		fields = new HashMap<>();
		rbIssue = new JRadioButton("Issue",true);
		rbBug = new JRadioButton("Bug");
		rbEnhancement = new JRadioButton("Enhancement");
		
		cbGroup = new ButtonGroup();
		cbGroup.add(rbIssue);
		cbGroup.add(rbBug);
		cbGroup.add(rbEnhancement);
	}
		
	public String getSelectedButton()
	{
		if(rbBug.isSelected()) {
			return rbBug.getText();
		}
		else if (rbEnhancement.isSelected()) {
			return rbEnhancement.getText();
		}
		else {
			return rbIssue.getText();
		}
	}
	
	public void setCreatorItems(String[] keys)
	{
		getComboBox("creator").removeAllItems();
		getComboBox("creator").addItem("");
		
		for(int i=0; i<keys.length; i++)
		{
			getComboBox("creator").addItem(keys[i]);
		}
	}
	
	public void setAssignedItems(String[] keys)
	{
		getComboBox("assignedTo").removeAllItems();
		getComboBox("assignedTo").addItem("");
		
		for(int i=0; i<keys.length; i++)
		{
			getComboBox("assignedTo").addItem(keys[i]);
		}
	}
	
	public void setProjectItems(String[] keys)
	{
		getComboBox("project").removeAllItems();
		getComboBox("project").addItem("");
		
		for(int i=0;i<keys.length;i++)
		{
			getComboBox("project").addItem(keys[i]);
		}
	}
	
	public void setCategoryItems(String[] keys)
	{
		getComboBox("category").removeAllItems();
		getComboBox("category").addItem("");
		
		for (int i=0;i<keys.length;i++)
		{
			getComboBox("category").addItem(keys[i]);
		}
	}
	
	public void setStatusItems(String[] keys)
	{
		getComboBox("status").removeAllItems();
		getComboBox("status").addItem("");
		
		for(int i=0; i<keys.length; i++)
		{
			getComboBox("status").addItem(keys[i]);
		}
	}
	
	public void populateFieldsData(@SuppressWarnings("unchecked") ArrayList<OMultipleOption>... multipleOptions) {
		fields.put(0, new FormField("id", fieldTypes.LABEL, false));
		fields.put(1, new FormField("name", fieldTypes.TEXT_FIELD, 10, -1, true));
		fields.put(2, new FormField("type", fieldTypes.RADIO_GROUP, multipleOptions[0], 0));
		fields.put(3, new FormField("project", fieldTypes.COMBO_BOX, multipleOptions[1], true, 0, true, false));
		fields.put(4, new FormField("creator", fieldTypes.COMBO_BOX, multipleOptions[2], true, MainFrame.loggedInUserId, true, true));
		fields.put(5, new FormField("assignedTo", fieldTypes.COMBO_BOX, multipleOptions[2], false, 0, true, false));
		fields.put(6, new FormField("status", fieldTypes.COMBO_BOX, multipleOptions[3], true, 1, false, true));
		fields.put(7, new FormField("description", fieldTypes.TEXT_AREA, 10, -1, true));
	}
	
	public void applyChanges() {
		//TODO Massive problem when creating new issue - to be resolved immediately
		String name = getTextField("name").getText().trim();
		
		while(name.contains("  ")) { name = name.replace("  ", " "); }
		
		String description = getTextArea("description").getText().trim();
		
		while(description.contains("  ")) { description = description.replace("  ", " "); }
		
		currentIssue.setName(name);
		currentIssue.setTypeId(getButtonGroup("type").getSelectedEntityId());
		currentIssue.setProjectId(getComboBox("project").getSelectedEntityId());
		currentIssue.setCreatorId(getComboBox("creator").getSelectedEntityId());
		currentIssue.setAssigneeId(getComboBox("assignedTo").getSelectedEntityId());
		currentIssue.setStatusId(getComboBox("status").getSelectedEntityId());
		currentIssue.setCategoryId(1);
		currentIssue.setDescription(description);
	}
	
	public void fillForm(Issue issue) {
		currentIssue = issue;
		
		fillForm();
	}
	
	public void fillForm() {
		clearValues();
		getTextField("name").requestFocus();
		
		getInfoLabel("id").setText(currentIssue.getId() + "");
		getTextField("name").setText(currentIssue.getName());
		
		for (Enumeration<AbstractButton> buttons = getButtonGroup("type").getElements(); buttons.hasMoreElements();) {
			AbstractButton radioButton = buttons.nextElement();
			
			if(getButtonGroup("type").getOptions().stream()
					.filter(type -> type.getName().equals(radioButton.getText()))
					.map(OMultipleOption::getId)
					.collect(Collectors.toList())
					.toArray(new Integer[]{})[0] == currentIssue.getTypeId()) {
				radioButton.setSelected(true);
				break;
			}
		}
		
		for(int i = 1; i < getComboBox("project").getItemCount(); i++) {
			String optionText = getComboBox("project").getItemAt(i).toString();
			
			if(getComboBox("project").getOptions().stream()
					.filter(option -> option.getName().equals(optionText))
					.map(OMultipleOption::getId)
					.collect(Collectors.toList())
					.toArray(new Integer[]{})[0] == currentIssue.getProjectId()) {
				getComboBox("project").setSelectedIndex(i);
				break;
			}
		}
		
		for(int i = 1; i < getComboBox("creator").getItemCount(); i++) {
			String optionText = getComboBox("creator").getItemAt(i).toString();
			
			if(getComboBox("creator").getOptions().stream()
					.filter(option -> option.getName().equals(optionText))
					.map(OMultipleOption::getId)
					.collect(Collectors.toList())
					.toArray(new Integer[]{})[0] == currentIssue.getCreatorId()) {
				getComboBox("creator").setSelectedIndex(i);
				break;
			}
		}
		
		for(int i = 1; i < getComboBox("assignedTo").getItemCount(); i++) {
			String optionText = getComboBox("assignedTo").getItemAt(i).toString();
			
			if(getComboBox("assignedTo").getOptions().stream()
					.filter(option -> option.getName().equals(optionText))
					.map(OMultipleOption::getId)
					.collect(Collectors.toList())
					.toArray(new Integer[]{})[0] == currentIssue.getAssigneeId()) {
				getComboBox("assignedTo").setSelectedIndex(i);
				break;
			}
		}
		
		for(int i = 1; i < getComboBox("status").getItemCount(); i++) {
			String optionText = getComboBox("status").getItemAt(i).toString();
			
			if(getComboBox("status").getOptions().stream()
					.filter(option -> option.getName().equals(optionText))
					.map(OMultipleOption::getId)
					.collect(Collectors.toList())
					.toArray(new Integer[]{})[0] == currentIssue.getStatusId()) {
				getComboBox("status").setSelectedIndex(i);
				break;
			}
		}
		
		getTextArea("description").setText(currentIssue.getDescription());
	}
	
	public void setListeners() {
		getComboBox("assignedTo").addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(((OComboBox<String>) e.getSource()).getSelectedIndex() > 0) {
					getComboBox("status").setSelectedIndex(2);
				}
				else {
					getComboBox("status").setSelectedIndex(1);
				}
			}
		});
	}
}