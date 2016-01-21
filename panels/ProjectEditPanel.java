package panels;

import helpers.FormField;

import java.util.HashMap;

import panels.visuals.JForm;

public class ProjectEditPanel extends JForm {
	private static final long serialVersionUID = -7515675083359644422L;	
	HashMap<Integer, FormField> fields;
	
	public ProjectEditPanel()
	{
		super(300,20);
		initialize();
		populateFieldsData();
		createForm(fields);
		
	}
	
	public void initialize()
	{
		fields = new HashMap<>();
	}
	
	public void populateFieldsData()
	{
		//name;client;description
		
		fields.put(1, new FormField("name", fieldTypes.TEXT_FIELD, 10,-1, true));
		fields.put(1, new FormField("client", fieldTypes.TEXT_FIELD, 10, 100, true));
		fields.put(1, new FormField("description", fieldTypes.TEXT_FIELD, 10, -1, true));
	}
}