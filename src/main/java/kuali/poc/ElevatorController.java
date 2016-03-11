package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kuali.poc.Elevator.ElevatorStatus;

public class ElevatorController implements PropertyChangeListener{
	private List<Elevator> elevators;
	
	public ElevatorController(int numElevators, int numFloors) {
		elevators = new ArrayList<Elevator>();
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
		// walk through each elevator to see if it is idling at the requested floor
		for (Elevator e: elevators) {
			if (e.getStatus() == ElevatorStatus.IDLE && e.getCurrentFloor() == floorRequest) {
				return e;
			}
		}
		// walk through each elevator to see if a moving elevator will pass the requested floor
		for (Elevator e: elevators) {
			if (e.getStatus() == ElevatorStatus.MOVING_UP && e.getTopMostFloorToBeServiced() >= floorRequest && e.getCurrentFloor() < floorRequest) {
				return e;
			}
			if (e.getStatus() == ElevatorStatus.MOVING_DOWN && e.getBottomMostFloorToBeServiced() <= floorRequest && e.getCurrentFloor() > floorRequest) {
				return e;
			}
		}
		//walk through each idle elevator and return the closest idle elevator
		Elevator el = null;
		int minDist = Integer.MAX_VALUE;
		for (Elevator e: elevators) {
			if (e.getStatus() == ElevatorStatus.IDLE) {
				int diff = Math.abs(e.getCurrentFloor() - floorRequest);
				if (diff < minDist) {
					minDist = diff;
					el = e;
				}
			}
		}
		if (el!=null) {
			return el;
		}
		try {
			//no elevators are idle.. so wait for a second and retry
			Thread.sleep(1000);
			return findBestElevator(floorRequest);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//should not occur
		return null;
		
	}
	public void propertyChange(PropertyChangeEvent evt) {
		String eventPropName = evt.getPropertyName();
		String eventNewValue = evt.getNewValue().toString();
		String eventOldValue = evt.getOldValue().toString();
		
		if(eventPropName == Constants.ELEVATOR_EVT_FLOOR_CHANGED) {
			//elevator floor has been changed
			System.out.println("elevator"+ eventOldValue +" moved to "+eventNewValue);
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
