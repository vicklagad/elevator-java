package kuali.poc;

import java.util.List;

public class ElevatorController {
	private List<ElevatorRequest> requests;
	private List<Elevator> elevators;
	
	public ElevatorController() {
		
	}
	public void requestElevator(int floor) {
		//elevator to be summoned to where person pushes the button from
		ElevatorRequest request = new ElevatorRequest();
		request.setElevatorController(this);
		request.makeRequest(floor);
		requests.add(request);
	}
	
	
}
