package panels.visuals;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import helpers.FormField;

public class JForm extends JPanel {
	private static final long serialVersionUID = -432718193365912996L;
	int componentsWidth = 0, componentsHeight = 0;
	HashMap<Integer, String> hmFieldOrder = new HashMap<>();
	HashMap<String, OTextField> hmTextFields = new HashMap<>();
	HashMap<String, OComboBox<String>> hmComboBoxes = new HashMap<>();
	HashMap<String, OTextArea> hmTextAreas = new HashMap<>();
	HashMap<String, JLabel> hmLabels = new HashMap<>();
	HashMap<String, JLabel> hmInfoLabels = new HashMap<>();
	HashMap<String, OButtonGroup> hmRadioGroups = new HashMap<>();
	public JButton btSave = new JButton("Save");
	public JButton btCancel = new JButton("Cancel");
	public enum fieldTypes {
		LABEL,
		TEXT_FIELD,
		COMBO_BOX,
		TEXT_AREA,
		RADIO_GROUP
	}
	
	public JForm(int componentsWidth, int componentsHeight) {
		setComponentsWidth(componentsWidth);
		setComponentsHeight(componentsHeight);
	}

//	Validate the form IssuePanel
	public boolean validateForm()
	{
		long errors = hmTextFields.values().stream().filter(field -> field.validateFormComponent() == true).count();
		errors += hmComboBoxes.values().stream().filter(field -> field.validateFormComponent() == true).count();
		errors += hmTextAreas.values().stream().filter(field -> field.validateFormComponent() == true).count();
		
		return errors>0?false:true;
	}
	
	public void createForm(HashMap<Integer, FormField> fields) {
		fields.keySet().forEach(key-> {
			switch (fields.get(key).getType()) {
			case LABEL:
				hmInfoLabels.put(fields.get(key).getName(), new JLabel());
				break;
			case TEXT_FIELD:
				hmTextFields.put(fields.get(key).getName(), new OTextField(fields.get(key)));
				break;
			case COMBO_BOX:
				hmComboBoxes.put(fields.get(key).getName(), new OComboBox<String>(fields.get(key)));
				break;
			case TEXT_AREA:
				hmTextAreas.put(fields.get(key).getName(), new OTextArea(fields.get(key)));
				break;
			case RADIO_GROUP:
				hmRadioGroups.put(fields.get(key).getName(), new OButtonGroup(fields.get(key)));
				
//				String[] radioButtonNames = fields.get(key).getMultipleOptionsAsArray(fields.get(key).areOptionsSorted());
//				
//				for(int i = 0; i < radioButtonNames.length; i++) {
//					JRadioButton currentRadioButton = new JRadioButton(radioButtonNames[i]);
//					hmRadioGroups.get(fields.get(key).getName()).add(currentRadioButton);
//					
//					if(i == fields.get(key).getDefaultSelectedOptionIndex()) {
//						hmRadioGroups.get(fields.get(key).getName()).setSelected(currentRadioButton.getModel(), true);
//					}
//				}
				
				break;
			default:
				break;
			}
			
			hmLabels.put(fields.get(key).getName(), new JLabel(formatLabelText(fields.get(key).getName())));
			hmFieldOrder.put(key, fields.get(key).getName());
		});
		
		createLayouts();
		setSizes();
	}
	
	public void setSizes() {
		hmTextFields.values().forEach(field-> {
			field.setMinimumSize(new Dimension(componentsWidth, componentsHeight));
			field.setMaximumSize(new Dimension(componentsWidth, componentsHeight));
		});
		
		hmComboBoxes.values().forEach(field-> {
			field.setMinimumSize(new Dimension(componentsWidth, componentsHeight));
			field.setMaximumSize(new Dimension(componentsWidth, componentsHeight));
		});
		
		hmTextAreas.values().forEach(field-> {
			field.setMinimumSize(new Dimension(componentsWidth, componentsHeight * 5));
			field.setMaximumSize(new Dimension(componentsWidth, componentsHeight * 5));
		});
	}
	
	public void createLayouts()
	{
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup labelsHorizontalGroup = layout.createParallelGroup();
		ParallelGroup fieldsHorizontalGroup = layout.createParallelGroup();
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		
		hmFieldOrder.keySet().stream().sorted().forEach(key-> {
			String fieldName = hmFieldOrder.get(key);
			Component component = hmTextFields.get(fieldName);
			
			if(component == null) {
				component = hmComboBoxes.get(fieldName);
			}
			
			if(component == null) {
				component = hmTextAreas.get(fieldName);
			}
			
			if(component == null) {
				component = hmInfoLabels.get(fieldName);
			}
			
			labelsHorizontalGroup.addComponent(hmLabels.get(fieldName));
			
			SequentialGroup radioHorizontalGroup = layout.createSequentialGroup();
			ParallelGroup radioVerticalGroup = layout.createParallelGroup();
			
			if(component == null) {
				radioVerticalGroup.addComponent(hmLabels.get(fieldName));
				
				for(Enumeration<AbstractButton> buttons = hmRadioGroups.get(fieldName).getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					
					radioHorizontalGroup.addComponent(button);
					radioVerticalGroup.addComponent(button);
				}
			}
			
			if(component == null) {
				fieldsHorizontalGroup.addGroup(radioHorizontalGroup);
				verticalGroup.addGroup(radioVerticalGroup);
			}
			else {
				fieldsHorizontalGroup.addComponent(component);
				verticalGroup.addGroup(layout.createParallelGroup()
					.addComponent(hmLabels.get(fieldName))
					.addComponent(component));
			}
		});
		
		verticalGroup.addGap(30);
		verticalGroup.addGroup(layout.createParallelGroup()
			.addComponent(btSave)
			.addComponent(btCancel));
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
					.addGroup(layout.createSequentialGroup()
						.addGroup(labelsHorizontalGroup)
						.addGroup(fieldsHorizontalGroup))
					.addGroup(layout.createSequentialGroup()
						.addComponent(btSave)
						.addComponent(btCancel))));
		
		layout.setVerticalGroup(verticalGroup);
	}
	
	public String formatLabelText(String text) {
		String formattedString = text.replaceAll(String.format("%s|%s|%s",
																"(?<=[A-Z])(?=[A-Z][a-z])",
																"(?<=[^A-Z])(?=[A-Z])",
																"(?<=[A-Za-z])(?=[^A-Za-z])"
															),
												" ").toLowerCase();
		
		formattedString = formattedString.substring(0, 1).toUpperCase() + formattedString.substring(1);
//		String[] r = s.split("(?=\\p{Lu})");
		
		return formattedString;
	}
	
	public void clearValues()
	{
		hmComboBoxes.values().stream().forEach(cb -> {
			cb.resetSelection();
		});
		
		hmTextFields.values().stream().forEach(tf -> {
			tf.setText("");
		});
		
		hmTextAreas.values().stream().forEach(ta -> {
			ta.setText("");
		});
		
		hmInfoLabels.values().stream().forEach(lb -> {
			lb.setText("");
		});
		
		hmRadioGroups.values().stream().forEach(bg -> {
			for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
				AbstractButton radioButton = buttons.nextElement();
				
				radioButton.setSelected(true);
				break;
			}
		});
	}
	
	public OComboBox<String> getComboBox(String name) {
		return hmComboBoxes.get(name);
	}
	
	public OTextField getTextField(String name) {
		return hmTextFields.get(name);
	}
	
	public OTextArea getTextArea(String name) {
		return hmTextAreas.get(name);
	}

	public JLabel getInfoLabel(String name) {
		return hmInfoLabels.get(name);
	}
	
	public OButtonGroup getButtonGroup(String name) {
		return hmRadioGroups.get(name);
	}

	public int getComponentsHeight() {
		return componentsHeight;
	}

	public void setComponentsHeight(int componentsHeight) {
		this.componentsHeight = componentsHeight;
	}

	public int getComponentsWidth() {
		return componentsWidth;
	}

	public void setComponentsWidth(int componentsWidth) {
		this.componentsWidth = componentsWidth;
	}
}