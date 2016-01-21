package panels;

import helpers.FormField;

import java.util.HashMap;

import panels.visuals.JForm;

public class CategoryEditPanel extends JForm
{
	HashMap<Integer, FormField> fields;
	
	public CategoryEditPanel()
	{
		super(300,20);
		initialize();
	}
	
	public void initialize()
	{
		fields = new HashMap<>();
	}
	
	public void populateFieldsData()
	{
		
	}
}