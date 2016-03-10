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
	
	private Elevator findBestElevator(ElevatorRequest er) {
		//algorithm to find best elevator to process this new request
		return null;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		String eventPropName = evt.getPropertyName();
		String eventNewValue = evt.getNewValue().toString();
		String eventOldValue = evt.getOldValue().toString();
		
		if(eventPropName == Constants.ELEVATOR_EVT_FLOOR_CHANGED) {
			//elevator floor has been changed
		}
		
	}
	
	
}
