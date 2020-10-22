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

public class JHopOptionsDialog extends JDialog{
    public static final int MIN_WIDTH = 400;
    public static final int MIN_HEIGHT = 300;
    
    private Map<String, Option> fields;
    private JFrame owner;
    private JButton accept, cancel;
    private Controller controller;
    
    private class Option {
        JTextField field;
        String value;
        
        public Option(JTextField field) {
            this.field = field;
            value = null;
        }
    }
    
    public JHopOptionsDialog(Collection<String> options, JFrame owner) {
        super(owner);
        this.owner = owner;
        fields = new HashMap<>();
        accept = new JButton("Ok");
        cancel = new JButton("Cancel");

        populate(options);
        center();
        controller = new JHopOptionsDialogController(this);
        controller.link();
        setLocation(owner.getX(), owner.getY());
        setVisible(true);
    }
    
    private void populate(Collection<String> options) {
        JPanel contents = new JHopStackPanel();
        
        for(String option : options) {
            JTextField optionInput = new JTextField();
            Option optionComponents = new Option(optionInput);
            fields.put(option, optionComponents);
            Border border = BorderFactory.createTitledBorder(option);
            optionInput.setBorder(border);
            contents.add(optionInput);
        }
        
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
    
    public Set<String> getFieldNames() {
        return fields.keySet();
    }
    
    public String getControlValue(String name) {
        return fields.get(name).field.getText();
    }
    
    public String get(String name) {
        return fields.get(name).value;
    }
    
    public boolean set(String name) {
        Option opt = fields.get(name);
        
        if(opt != null) {
            opt.value = getControlValue(name);
            return true;
        }
        
        return false;
    }
    
    public JButton getAcceptControl() {
        return accept;
    }
    
    public JButton getCancelControl() {
        return cancel;
    }
}
