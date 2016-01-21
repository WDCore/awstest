package panels;

import helpers.FormField;

import java.util.HashMap;

import panels.visuals.JForm;

public class SpecialityEditPanel extends JForm
{
	HashMap<Integer, FormField> fields;
	
	public SpecialityEditPanel()
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
		
	}

}
