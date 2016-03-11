package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ElevatorRequest implements Runnable {
	private int floorRequested;
	
	//any change in relevant properties of this elevator should
	//trigger an event on its listener
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	public ElevatorRequest(int floorRequested) {
		this.floorRequested = floorRequested;
	}

	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
		}
	}
	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}

	public void run() {
		notifyListeners(this, Constants.REQUEST_EVT_REQ_MADE,
				String.valueOf(this.floorRequested),
				String.valueOf(this.floorRequested)); //todo: refactoring needed
	}
}
