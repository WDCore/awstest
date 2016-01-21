package panels;

import helpers.FormField;

import java.util.HashMap;

import javax.swing.BorderFactory;

import panels.visuals.JForm;

public class UsersEditPanel extends JForm
{
	private static final long serialVersionUID = -9027102247856471362L;
	HashMap<Integer, FormField> fields;
	public UsersEditPanel()
	{
		super(300,20);
		initialize();
		populateFieldsData();
		createForm(fields);
		
		this.setBorder(BorderFactory.createTitledBorder("Create new user"));
	}
	
	public void initialize()
	{
		fields = new HashMap<>();
	}
	
	public void populateFieldsData()
	{
		fields.put(1, new FormField("firstName", fieldTypes.TEXT_FIELD, 3, -1, true));
		fields.put(2, new FormField("surname", fieldTypes.TEXT_FIELD, -1, -1, false));
		fields.put(3, new FormField("lastName", fieldTypes.TEXT_FIELD, 3, -1, true));
	}
}