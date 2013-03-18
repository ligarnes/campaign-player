package net.alteiar.client.test.bean;

import java.beans.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ligarnes
 */
public class CharacterBean extends BasicBeans {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private String sampleProperty;
    
    public CharacterBean() {
    }
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        try {
            String oldValue = sampleProperty;
            vetoableChangeSupport.fireVetoableChange(PROP_SAMPLE_PROPERTY, oldValue, value);
            sampleProperty = value;
            propertyChangeSupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(CharacterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
