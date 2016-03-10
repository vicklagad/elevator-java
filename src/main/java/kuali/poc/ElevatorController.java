package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ElevatorController implements PropertyChangeListener{
	private List<Elevator> elevators;
	
	public ElevatorController(int numElevators, int numFloors) {
		for (int i = 0; i < numElevators; i++) {
			Elevator e = new Elevator(1,numFloors);
			elevators.add(e);
		}
	}
	
	private Elevator findBestElevator(int floorRequest) {
		//algorithm to find best elevator to process this new request
		//TODO
		return null;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		String eventPropName = evt.getPropertyName();
		String eventNewValue = evt.getNewValue().toString();
		String eventOldValue = evt.getOldValue().toString();
		
		if(eventPropName == Constants.ELEVATOR_EVT_FLOOR_CHANGED) {
			//elevator floor has been changed
		}
		if (eventPropName == Constants.REQUEST_EVT_REQ_MADE) {
			//new request has been made for a floor, invoke algorithm to find best
			//elevator
			int floor = Integer.parseInt(eventNewValue);
			Elevator e = findBestElevator(floor);
			e.addFloorToService(floor);
		}
		if (eventPropName == Constants.DOOR_EVT_CLOSED) {
			
		}
		if (eventPropName == Constants.DOOR_EVT_OPENED) {
			
		}
		
	}
	
	
}
