package beans;

import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener {

    void dataChanged(DataChangeEvent event);
}
