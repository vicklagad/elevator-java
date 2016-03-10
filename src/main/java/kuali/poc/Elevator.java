package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/** The main elevator class **/

public class Elevator {
	
	//any change in relevant properties of this elevator should
	//trigger an event on its listener
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	private int currentFloor; //current floor this elevator is on
	
	public static enum ElevatorStatus {
		UNDER_MAINTENANCE, MOVING, IDLE
	};
	public static enum DoorStatus {
		OPEN, CLOSED
	}
	private Elevator.ElevatorStatus status;
	private Elevator.DoorStatus doorStatus;

	private int maxCapacity; //max weight it can hold
	private int tripCount;
	private int floorsPassed;
	private int maxFloors;
	private List<Integer> currentFloorsToService;
	
	
	public Elevator(int currentFloor, int maxFloors) {
		this.status = Elevator.ElevatorStatus.IDLE;
		this.currentFloor = currentFloor;
		this.doorStatus = Elevator.DoorStatus.CLOSED;
		this.maxFloors = maxFloors;
	}
	
	public abstract int moveUp() throws IllegalStateException;
	public abstract int moveDown() throws IllegalStateException;
	public abstract int step();
	public abstract void decomission();
	public abstract void stop();
	public abstract void start();
	public abstract void openDoor();
	public abstract void closeDoor();
	
	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
			}
	}
	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}
	
	

	public int getCurrentFloor() {
		return currentFloor;
	}

	public Elevator.ElevatorStatus getStatus() {
		return status;
	}

	public Elevator.DoorStatus getDoorStatus() {
		return doorStatus;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getTripCount() {
		return tripCount;
	}

	public int getFloorsPassed() {
		return floorsPassed;
	}

	
	
	

}
