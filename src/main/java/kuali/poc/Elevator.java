package kuali.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
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
	private int id;
	
	
	public Elevator(int currentFloor, int maxFloors, int id) {
		this.status = Elevator.ElevatorStatus.IDLE;
		this.currentFloor = currentFloor;
		this.doorStatus = Elevator.DoorStatus.CLOSED;
		this.maxFloors = maxFloors;
		this.currentFloorsToReach = new TreeSet<Integer>();
		this.id = id;
	}
	
	public void addFloorToService(int floor) throws Exception{
		if (floor < 1 || floor > this.maxFloors) {
			throw new IllegalArgumentException("Cannot service floors less than 1 or greater than "+this.maxFloors);
		}
		if (this.status != ElevatorStatus.UNDER_MAINTENANCE) {
			//elevator is not undermaintenance
			if ( 	(this.status == ElevatorStatus.IDLE )
					||
					(this.status == ElevatorStatus.MOVING_UP && this.currentFloor < floor)
					||
					(this.status == ElevatorStatus.MOVING_DOWN && this.currentFloor > floor)
				) {
				//elevator is either idle or moving up/ moving down and can service the requested floor
					this.currentFloorsToReach.add(floor);
			} else {
				//elevator is moving up or down but cannot service the requested floor as it has passed it
				throw new IllegalStateException("Sorry, incorrect request sent for this elevator. Please try again.");				
			}
		} else {
			//elevator is under maintenance, cannot service the requested floor (or any floors)
			throw new IllegalStateException("This elevator is under maintenance. Cannot serve floor request");
		}
	}
	private void checkCurrentState() {
		//check current state of itself
		int nextFloor = -1;
		if (this.status == ElevatorStatus.MOVING_UP) {
			//if elevator is moving up the next floor to stop at is the lowest number in the treeset
			nextFloor = this.currentFloorsToReach.first();
		} else if (this.status == ElevatorStatus.MOVING_DOWN) {
			//if elevator is moving down the next floor to stop at is the highest number in the treeset
			//remember that the elevator will not accept requests that it cannot serve
			nextFloor = this.currentFloorsToReach.last();
		}
		
		
		if (this.currentFloor == nextFloor) {
			this.currentFloorsToReach.remove(currentFloorsToReach.first());
			openDoor(); 
			closeDoor();
			
			if (this.currentFloorsToReach.size()==0) {
				//no more requests to serve, trip over
				addTripsMade();
				if (this.tripCount >=100) {
					//too many trips.. maintenance now
					this.status = ElevatorStatus.UNDER_MAINTENANCE;
					notifyListeners(this, Constants.ELEVATOR_EVT_UNDER_MAINTENANCE,
							String.valueOf(this.id),
							String.valueOf(this.id)); //elevator floor changed event fired
					
				} else {
					//ready to serve
					this.status = ElevatorStatus.IDLE;
					notifyListeners(this, Constants.ELEVATOR_EVT_READY,
							String.valueOf(this.currentFloor),
							String.valueOf(this.currentFloor)); //elevator ready to serve..is idling
				}
			}
		}
		if (this.currentFloorsToReach.size()>0 && this.currentFloor < nextFloor) {
			//elevator is under the requested floor
			try {
				moveUp(); //move it up
			} catch (Exception e) {
				// TODO Handle exceptions
				e.printStackTrace();
			}
		} 
		if (this.currentFloorsToReach.size()>0 && this.currentFloor > nextFloor) {
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
			synchronized (this){
				this.status = Elevator.ElevatorStatus.MOVING_UP;
				this.currentFloor++;
				addFloorsVisited();
				notifyListeners(this, Constants.ELEVATOR_EVT_FLOOR_CHANGED,
						String.valueOf(this.currentFloor + 1),
						String.valueOf(currentFloor)); //elevator floor changed event fired
			}
			checkCurrentState(); //check own current state
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
			synchronized (this){
				this.status = Elevator.ElevatorStatus.MOVING_DOWN;
				this.currentFloor--;
				addFloorsVisited();
				notifyListeners(this, Constants.ELEVATOR_EVT_FLOOR_CHANGED,
						String.valueOf(this.currentFloor - 1),
						String.valueOf(currentFloor)); //todo: refactoring needed
			}
			checkCurrentState(); //check own current state
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
			notifyListeners(this, Constants.DOOR_EVT_OPENED,
					"",
					""); //event itself is self explanatory. no need for old and new values
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
			notifyListeners(this, Constants.DOOR_EVT_CLOSED,
					"",
					""); //event itself is self explanatory. no need for old and new values
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
