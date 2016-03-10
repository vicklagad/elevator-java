package kuali.poc;

import java.util.List;

/** The main elevator class **/

public abstract class Elevator {
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
