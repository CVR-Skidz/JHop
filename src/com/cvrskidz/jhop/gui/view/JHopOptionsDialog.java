package com.cvrskidz.jhop.gui.view;

import com.cvrskidz.jhop.gui.controllers.Controller;
import com.cvrskidz.jhop.gui.controllers.JHopOptionsDialogController;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 * A JHopOptionsDialog is a specialization of a dialog
 * in which options can be added to the window, each one consisting
 * of a name and text field. The dialog provides a map of 
 * options and their values which can be retrieved from the dialog once it 
 * is closed.
 *
 * @author cvrskidz 18031335
 */
public class JHopOptionsDialog extends JDialog{
    public static final int MIN_WIDTH = 400;
    public static final int MIN_HEIGHT = 300;
    
    private Map<String, Option> fields;
    private JFrame owner;
    private JButton accept, cancel;     //controls
    private Controller controller;      //logic for control events
    
    //pair of option input & values
    private class Option {
        JTextField field;
        String value;
        
        public Option(JTextField field) {
            this.field = field;
            value = null;
        }
    }
    
    /**
     * Construct a new dialog containing inputs for all the given options.
     * 
     * @param options A collection of option names
     * @param owner The parnet of this window
     */
    public JHopOptionsDialog(Collection<String> options, JFrame owner) {
        super(owner);
        this.owner = owner;
        fields = new HashMap<>();
        initComponents();        
        populate(options);

        // make interactive
        controller = new JHopOptionsDialogController(this);
        controller.link();

        //position and show
        center();
        setLocation(owner.getX(), owner.getY());
        setVisible(true);
    }
    
    /**
     * Prepare this instances controls to be added
     */
    private void initComponents() {
        accept = new JButton("Ok");
        cancel = new JButton("Cancel");
    }

    /**
     * Create map entries for each options and add them to the dialog
     */
    private void populate(Collection<String> options) {
        JPanel contents = new JHopStackPanel();
        
        //populate map
        for(String option : options) {
            JTextField optionInput = new JTextField();
            Option optionComponents = new Option(optionInput);
            fields.put(option, optionComponents);
            Border border = BorderFactory.createTitledBorder(option);
            optionInput.setBorder(border);
            contents.add(optionInput);
        }
        
        //populate UI
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controls.add(accept);
        controls.add(cancel);
        contents.add(controls);
        add(contents);
    }
    
    //TODO
    private void center() {
        setSize(MIN_WIDTH, MIN_HEIGHT);
        int offsetX = (owner.getWidth() - getWidth())/2;
        int offsetY = (owner.getHeight() - getHeight())/2;
        setLocation(owner.getX() + offsetX, owner.getY() + offsetY);
    }
    
    /**
     * @return a set containing all field names present in this dialog
     */
    public Set<String> getFieldNames() {
        return fields.keySet();
    }
    
    /**
     * @param name The option to retrieve
     * @return The value of the given option supplied by the user. 
     */
    public String getControlValue(String name) {
        return fields.get(name).field.getText();
    }
    
    /**
     * @param name The option to retrieve
     * @return The saved value of the given option. 
     */
    public String get(String name) {
        return fields.get(name).value;
    }
    
    /**
     * Save the value of the given option
     * 
     * @param The option to save
     * @return True if the option was saved.
     */
    public boolean set(String name) {
        Option opt = fields.get(name);
        
        if(opt != null) {
            opt.value = getControlValue(name);
            return true;
        }
        
        return false;
    }
    
    /**
     * @return A button used to accept all options entered by the user
     */
    public JButton getAcceptControl() {
        return accept;
    }
    
    /**
     * @return A button used to discard all options entered by the user
     */
    public JButton getCancelControl() {
        return cancel;
    }
}
