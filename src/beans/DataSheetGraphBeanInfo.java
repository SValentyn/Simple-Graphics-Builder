package beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * A tool for building verification of a bin and setting its characteristics.
 * Not implemented in full measure.
 */
public class DataSheetGraphBeanInfo extends SimpleBeanInfo {

    private PropertyDescriptor[] propertyDescriptors;

    public DataSheetGraphBeanInfo() throws IntrospectionException {
        propertyDescriptors = new PropertyDescriptor[]{
                new PropertyDescriptor("color", DataSheetGraph.class),
                new PropertyDescriptor("filled", DataSheetGraph.class),
                new PropertyDescriptor("deltaX", DataSheetGraph.class),
                new PropertyDescriptor("deltaY", DataSheetGraph.class)};
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors;
    }

}
