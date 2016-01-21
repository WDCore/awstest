package panels.visuals;

import helpers.FormField;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.Document;

public class OTextField extends JTextField {
	private static final long serialVersionUID = -7627234507379200037L;
	private int minimumTextLength = -1, maximumTextLength = -1;
	private boolean mandatory = false;

	public OTextField() {
		super();
	}
	
	public OTextField(int columns) {
		super(columns);
	}
	
	public OTextField(String text) {
		super(text);
	}
	
	public OTextField(FormField field) {
		super();
		setMinimumTextLength(field.getMinLength());
		setMaximumTextLength(field.getMaxLength());
		setMandatory(field.isMandatory());
	}
	
	public OTextField(String text, int columns) {
		super(text, columns);
	}
	
	public OTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public int getMinimumTextLength() {
		return minimumTextLength;
	}

	public void setMinimumTextLength(int minimumTextLength) {
		this.minimumTextLength = minimumTextLength;
	}

	public int getMaximumTextLength() {
		return maximumTextLength;
	}

	public void setMaximumTextLength(int maximumTextLength) {
		this.maximumTextLength = maximumTextLength;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean validateFormComponent() {
		String errorMessage = "<html>";
		boolean error = false;
		
		if(mandatory && this.getText().length() == 0) {
			errorMessage += "This data is required and must be entered.";
			error = true;
		}
		
		if(minimumTextLength > 0 && minimumTextLength > this.getText().length()) {
			errorMessage += "<br />Text is too short. Must be at least " + minimumTextLength + " characters long.";
			error = true;
		}
		
		if(maximumTextLength > 0 && maximumTextLength < this.getText().length()) {
			errorMessage += "<br />Text is too short. Must be at most " + maximumTextLength + " characters long.";
			error = true;
		}
		
		errorMessage += "</html>";
		
		if(error) {
			this.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.setToolTipText(errorMessage);
		}
		else {
			this.setBorder(UIManager.getBorder("TextField.border"));
			this.setToolTipText(null);
		}
		
		return error;
	}
}