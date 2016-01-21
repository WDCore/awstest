package helpers;

import java.util.ArrayList;

import panels.visuals.OMultipleOption;

public interface IForm<T> {
	public void fillForm(T object);
	
	public void applyChanges();
	
	public void populateFieldsData(@SuppressWarnings("unchecked") ArrayList<OMultipleOption>... multipleOptions);
}