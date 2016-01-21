package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import panels.visuals.JForm;
import panels.visuals.OMultipleOption;

public class FormField {
	private String name = "";
	private JForm.fieldTypes type = null;
	private int minLength = 0, maxLength = 0, defaultSelectedEntityId = 0;
	private ArrayList<OMultipleOption> multipleOptions = new ArrayList<>();
	private boolean mandatory = false, optionsSorted = false, selectionLocked = false;
	
	public FormField(String name, JForm.fieldTypes type, int minLength, int maxLength, boolean mandatory) {
		setName(name);
		setType(type);
		setMinLength(minLength);
		setMaxLength(maxLength);
		setMandatory(mandatory);
	}
	
	public FormField(String name, JForm.fieldTypes type, boolean mandatory) {
		setName(name);
		setType(type);
		setMandatory(mandatory);
	}
	
	/* Button Group constructor */
	public FormField(String name, JForm.fieldTypes type, ArrayList<OMultipleOption> multipleOptions, int defaultSelectedEntityId) {
		setName(name);
		setType(type);
		setMultipleOptions(multipleOptions);
		setDefaultSelectedEntityId(defaultSelectedEntityId);
	}
	
	/* ComboBox constructor */
	public FormField(String name, JForm.fieldTypes type, ArrayList<OMultipleOption> multipleOptions,
						boolean mandatory, int defaultSelectedEntityId, boolean optionsSorted, boolean selectionLocked) {
		setName(name);
		setType(type);
		setMultipleOptions(multipleOptions);
		setMandatory(mandatory);
		setDefaultSelectedEntityId(defaultSelectedEntityId);
		setOptionsSorted(optionsSorted);
		setSelectionLocked(selectionLocked);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public JForm.fieldTypes getType() {
		return type;
	}

	public void setType(JForm.fieldTypes type) {
		this.type = type;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public ArrayList<OMultipleOption> getMultipleOptions() {
		return multipleOptions;
	}

	public ArrayList<OMultipleOption> getMultipleOptions(boolean optionsSorted) {
		if(!optionsSorted) {
			return multipleOptions;
		}
		else {
			return (ArrayList<OMultipleOption>) multipleOptions.stream()
					.sorted((OMultipleOption o1, OMultipleOption o2) -> {return o1.getName().compareTo(o2.getName());} )
					.collect(Collectors.toList());
		}
	}
	
	public String[] getMultipleOptionsAsArray(boolean optionsSorted) {
		String[] items = multipleOptions.stream().map(OMultipleOption::getName).collect(Collectors.toList()).toArray(new String[]{});
		
		if(optionsSorted) {
			Arrays.sort(items);
		}
		
		return items;
	}

	public void setMultipleOptions(ArrayList<OMultipleOption> multipleOptions) {
		this.multipleOptions = multipleOptions;
	}

	public int getDefaultSelectedEntityId() {
		return defaultSelectedEntityId;
	}

	public void setDefaultSelectedEntityId(int defaultSelectedOptionIndex) {
		this.defaultSelectedEntityId = defaultSelectedOptionIndex;
	}

	public boolean areOptionsSorted() {
		return optionsSorted;
	}

	public void setOptionsSorted(boolean optionsSorted) {
		this.optionsSorted = optionsSorted;
	}

	public boolean isSelectionLocked() {
		return selectionLocked;
	}

	public void setSelectionLocked(boolean selectionLocked) {
		this.selectionLocked = selectionLocked;
	}
}