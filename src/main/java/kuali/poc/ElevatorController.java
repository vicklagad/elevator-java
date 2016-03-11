package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ElevatorController implements PropertyChangeListener{
	private List<Elevator> elevators;
	
	public ElevatorController(int numElevators, int numFloors) {
		for (int i = 0; i < numElevators; i++) {
			Elevator e = new Elevator(1,numFloors, i+1);
			elevators.add(e);
			e.addChangeListener(this); //this class will handle events from elevators
		}
	}
	
	public void makeRequest(int floor) {
		ElevatorRequest er = new ElevatorRequest(floor);
		er.addChangeListener(this); //this class will handle request events
		new Thread(er).start(); //submit a request thread and move on
	}
	
	private Elevator findBestElevator(int floorRequest) {
		//algorithm to find best elevator to process this new request
		//TODO
		return elevators.get(0); //return some elevator for now
	}
	public void propertyChange(PropertyChangeEvent evt) {
		String eventPropName = evt.getPropertyName();
		String eventNewValue = evt.getNewValue().toString();
		String eventOldValue = evt.getOldValue().toString();
		
		if(eventPropName == Constants.ELEVATOR_EVT_FLOOR_CHANGED) {
			//elevator floor has been changed
		}
		if(eventPropName == Constants.ELEVATOR_EVT_UNDER_MAINTENANCE) {
			//elevator just went under maintenance
		}
		if(eventPropName == Constants.ELEVATOR_EVT_READY) {
			//elevator is ready to take requests (is idling)
		}
		if (eventPropName == Constants.REQUEST_EVT_REQ_MADE) {
			//new request has been made for a floor, invoke algorithm to find best
			//elevator
			int floor = Integer.parseInt(eventNewValue);
			Elevator e = findBestElevator(floor);
			try {
				e.addFloorToService(floor);
			} catch (IllegalStateException ex) {
				//the elevator selected by algorithm could not serve the request, so recreate it
				makeRequest(floor);
			} catch (Exception otherEx) {
				System.out.println("System malfunction -- illegal floor sent."+ otherEx.getMessage());
			}
		}
		if (eventPropName == Constants.DOOR_EVT_CLOSED) {
			
		}
		if (eventPropName == Constants.DOOR_EVT_OPENED) {
			
		}
		
	}
	
	
}
