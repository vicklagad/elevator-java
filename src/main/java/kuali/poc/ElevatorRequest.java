package kuali.poc;

public abstract class ElevatorRequest {
	private int forFloor;
	private int fromFloor;
	
	public ElevatorRequest() {
	}
	public abstract void addRequest();
}
