package com.cvrskidz.jhop.gui.view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import javax.swing.JFrame;

public class JHopOptionsDialog extends JDialog{
    Map<String, Option> fields;
            
    private class Option {
        JTextField field;
        String value;
        
        public Option(JTextField field) {
            this.field = field;
            value = null;
        }
        
        public JPanel fieldPanel(String name) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(name);
            panel.add(label, BorderLayout.WEST);
            panel.add(field, BorderLayout.CENTER);
            return panel;
        }
    }
    
    public JHopOptionsDialog(Collection<String> options) {
        super();
        fields = new HashMap<>();

        JPanel contents = new JHopStackPanel();
        
        for(String option : options) {
            JTextField optionInput = new JTextField();
            Option optionComponents = new Option(optionInput);
            fields.put(option, optionComponents);
            contents.add(optionComponents.fieldPanel(option));
        }
        
        add(contents);
    }
    
    public String getOption(String name) {
        return fields.get(name).value;
    }
}
