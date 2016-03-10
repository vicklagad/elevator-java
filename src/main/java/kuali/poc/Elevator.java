package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** The main elevator class **/

public class Elevator {
	
	//any change in relevant properties of this elevator should
	//trigger an event on its listener
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	private int currentFloor; //current floor this elevator is on
	
	public static enum ElevatorStatus {
		UNDER_MAINTENANCE, MOVING_UP, MOVING_DOWN, IDLE
	};
	public static enum DoorStatus {
		OPEN, CLOSED
	}
	
	private Elevator.ElevatorStatus status;
	private Elevator.DoorStatus doorStatus;
	private static final int STUB_WAIT_TIME = 1000; //some wait time for tests

	private int maxCapacity; //max weight it can hold
	private int tripCount;
	private int floorsPassed;
	private int maxFloors;
	private TreeSet<Integer> currentFloorsToReach;
	
	
	public Elevator(int currentFloor, int maxFloors) {
		this.status = Elevator.ElevatorStatus.IDLE;
		this.currentFloor = currentFloor;
		this.doorStatus = Elevator.DoorStatus.CLOSED;
		this.maxFloors = maxFloors;
		this.currentFloorsToReach = new TreeSet<Integer>();
	}
	private void checkCurrentState() {
		//check current state of itself
		if (this.currentFloor == this.currentFloorsToReach.first()) {
			this.currentFloorsToReach.remove(currentFloorsToReach.first());
			openDoor(); 
			closeDoor();
			
			if (this.currentFloorsToReach.size()==0) {
				//no more requests to serve, trip over
				addTripsMade();
				if (this.tripCount >=100) {
					//too many trips.. maintenance now
					this.status = ElevatorStatus.UNDER_MAINTENANCE;
				} else {
					//ready to serve
					this.status = ElevatorStatus.IDLE;
				}
			}
		}
		if (this.currentFloor < this.currentFloorsToReach.first()) {
			//elevator is under the requested floor
			try {
				moveUp(); //move it up
			} catch (Exception e) {
				// TODO Handle exceptions
				e.printStackTrace();
			}
		} 
		if (this.currentFloor > this.currentFloorsToReach.first()) {
			//elevator is above the requested floor
			try {
				moveDown(); //move it down
			} catch (Exception e) {
				// TODO Handle exceptions
				e.printStackTrace();
			}
		} 
		
	}
	private void addFloorsVisited() {
		this.floorsPassed ++;
	}
	private void addTripsMade() {
		this.tripCount++;
	}
	public void moveUp() throws Exception {
		try {
			if (this.currentFloor >= this.maxFloors) {
				throw new IllegalStateException("Elevator cannot go up. It is at the top most floor");
			}
			Thread.sleep(Elevator.STUB_WAIT_TIME);
			checkCurrentState(); //check own current state
			synchronized (this){
				this.status = Elevator.ElevatorStatus.MOVING_UP;
				this.currentFloor++;
				notifyListeners(this, Constants.ELEVATOR_EVT_FLOOR_CHANGED,
						String.valueOf(this.currentFloor + 1),
						String.valueOf(currentFloor)); //todo: refactoring needed
			}
		} catch (InterruptedException e) {
			throw new Exception("Thread wait issue.");
		}
		
	}
	
	public void moveDown() throws Exception {
		try {
			if (this.currentFloor <= 1) {
				throw new IllegalStateException("Elevator cannot go down. It is at the bottom most floor");
			}
			Thread.sleep(Elevator.STUB_WAIT_TIME);
			checkCurrentState(); //check own current state
			synchronized (this){
				this.status = Elevator.ElevatorStatus.MOVING_DOWN;
				this.currentFloor--;
				notifyListeners(this, Constants.ELEVATOR_EVT_FLOOR_CHANGED,
						String.valueOf(this.currentFloor - 1),
						String.valueOf(currentFloor)); //todo: refactoring needed
			}
		} catch (InterruptedException e) {
			throw new Exception("Thread wait issue.");
		}
		
	}


//	public abstract void decomission();
//	public abstract void stop();
//	public abstract void start();
	public void openDoor() {
		System.out.println("Open doors for people to get out..");
		try {
			Thread.sleep(Elevator.STUB_WAIT_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeDoor() {
		System.out.println("Closing doors");
		try {
			Thread.sleep(Elevator.STUB_WAIT_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public abstract void closeDoor();
//	
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
