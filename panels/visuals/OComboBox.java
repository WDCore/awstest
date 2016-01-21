package panels.visuals;

import helpers.FormField;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class OComboBox<E> extends JComboBox<E>{
	private static final long serialVersionUID = 880424372897780521L;
	private boolean toBeSelected = false, selectionLocked = false;
	private ArrayList<OMultipleOption> options = new ArrayList<>();
	private int defaultSelectedIndex = 0;
	
	public OComboBox(FormField field) {
		setToBeSelected(field.isMandatory());
		setOptions(field.getMultipleOptions());
		setSelectionLocked(field.isSelectionLocked());
		updateComboBoxState();
		
		fillOptions(field);
	}
	
	public void updateComboBoxState() {
		if(selectionLocked) {
			this.setEditable(true);
			((JTextField) this.getEditor().getEditorComponent()).setDisabledTextColor(Color.BLACK);
			((JTextField) this.getEditor().getEditorComponent()).setBackground(Color.LIGHT_GRAY);
			this.setEnabled(false);
		}
		else {
			((JTextField) this.getEditor().getEditorComponent()).setBackground(null);
			this.setEditable(false);
			this.setEnabled(true);
		}
	}
	
	public boolean isToBeSelected() {
		return toBeSelected;
	}

	public void setToBeSelected(boolean toBeSelected) {
		this.toBeSelected = toBeSelected;
	}

	public boolean validateFormComponent() {
		String errorMessage = "<html>";
		boolean error = false;
		
		if(toBeSelected && this.getSelectedIndex() == 0) {
			errorMessage += "You must choose an item.";
			error = true;
		}
		
		errorMessage += "</html>";
		
		if(error) {
			this.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.setToolTipText(errorMessage);
		}
		else {
			this.setBorder(UIManager.getBorder("ComboBox.border"));
			this.setToolTipText(null);
		}
		
		return error;
	}
	
	public int getSelectedEntityId() {
		Integer[] ids = this.getOptions().stream()
				.filter(option -> option.getName().equals(this.getSelectedItem().toString()))
				.map(OMultipleOption::getId)
				.collect(Collectors.toList())
				.toArray(new Integer[]{});
		
		return ids.length>0?ids[0]:0;
	}
	
	@SuppressWarnings("unchecked")
	private void fillOptions(FormField field) {
		this.removeAllItems();
		this.addItem((E) "");
		
		for (Iterator<OMultipleOption> iterator = options.iterator(); iterator.hasNext();) {
			OMultipleOption option = (OMultipleOption) iterator.next();
			
			this.addItem((E) option.getName());
			
			if(field.getDefaultSelectedEntityId() == option.getId()) {
				defaultSelectedIndex = this.getItemCount()-1;
				
				resetSelection();
			}
		}
	}
	
	public void resetSelection() {
		if(selectionLocked){
			this.setEnabled(true);
		}
		
		this.setSelectedIndex(defaultSelectedIndex);
		
		if(selectionLocked){
			this.setEnabled(false);
		}
	}

	public ArrayList<OMultipleOption> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<OMultipleOption> options) {
		this.options = options;
	}

	public boolean isSelectionLocked() {
		return selectionLocked;
	}

	public void setSelectionLocked(boolean selectionLocked) {
		this.selectionLocked = selectionLocked;
	}
}