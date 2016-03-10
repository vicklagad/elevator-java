package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ElevatorRequest {
	private int floorRequested;
	
	//any change in relevant properties of this elevator should
	//trigger an event on its listener
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	public ElevatorRequest() {
	}

	public void makeRequest(int floor) {
		this.floorRequested = floor;
		//notify listeners that a new request has been made for a floor
		notifyListeners(this, Constants.REQUEST_EVT_REQ_MADE,
				String.valueOf(this.floorRequested),
				String.valueOf(this.floorRequested)); //todo: refactoring needed
	}
	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
		}
	}
	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}
}
