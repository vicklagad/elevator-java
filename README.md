# elevator-java
An attempt to design a good elevator system in Java. 

Run ElevatorSimulator.java and type the floors you want to request on the console. e.g. output:
7
Added floor 7 to service for elevator 3
Open doors for people to get out..
Please select a floor between 1 and 40
Closing doors
31
Added floor 31 to service for elevator 2
Please select a floor between 1 and 40
elevator2 moved to 25
elevator2 moved to 26
elevator2 moved to 27
elevator2 moved to 28
elevator2 moved to 29
elevator2 moved to 30
elevator2 moved to 31
Open doors for people to get out..
Closing doors
35
Added floor 35 to service for elevator 2
Please select a floor between 1 and 40
elevator2 moved to 32
elevator2 moved to 33
elevator2 moved to 34
elevator2 moved to 35
Open doors for people to get out..
Closing doors


Pending Items:
- Method to manually set an elevator in maintenance and back to ready
- What if a person is riding an elevator and presses a floor button from inside the elevator (then the current elevator has to go there and not go through algorithm)
- Add file input 
- Refactor code, write test class and add comments 
- Verify thread interaction thoroughly (Saw some gaps)
- Elevator needs to wait for door open close events in case of multiple requests (needs debugging)
 

