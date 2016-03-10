package kuali.poc;

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
	private int maxCapacity; //max weight it can hold
	private int tripCount;
	private int floorsPassed;
	
	private boolean doorClosed;
	
	public Elevator(int currentFloor) {
		this.status = Elevator.ElevatorStatus.IDLE;
		this.currentFloor = currentFloor;
	}
	
	public abstract int moveUp() throws IllegalStateException;
	public abstract int moveDown() throws IllegalStateException;
	public abstract int step();
	public abstract void decomission();
	public abstract void stop();
	

	public int getCurrentFloor() {
		return currentFloor;
	}

	public Elevator.ElevatorStatus getStatus() {
		return status;
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
