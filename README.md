# ambulanceRobotSimulator
Give the opportunity to simulate the performances of some robots when saving people in a city

The simulator works with the JDK1.8 environment. It needs the Oracle JavaFX library to work. To install it on Eclipse, go to the Eclipse Marketplace and install the library called "e(fx)clipse 3.0.0" or an ulterior version. 


In joined files, there are two png files that represent maps. The map used in the actual version of the simulator is the “Final map.png”. The “Nancy Map.png” is a bigger map, more complex. The project was made to work on the Nancy Map, but because of a lack of time, crosses are not implemented in the robot intelligence. 
However, the graph of the Nancy Map has been made in the file “NancyMap.java”. To use it, go to the Main.java file (in the package main) and change the method Main() by the method contained in the NancyGraph.java.
Sometimes the library make crash the simulator, even when correctly installed. If it happens, just retry until it works. Some other times, the simulator does not crash, but the robot are not moving whereas the name of them objectives are changing like if it was working. It is also a problem with the library. Just retry until it works.
When the simulator works, we can see a representation of the map. There are white, red and green summits. The white summits are classic summits. Red summits are summits with a victim above. They will became white when a robot will take the victim. Green summits are hospitals. Green points are the robot ambulance. The red text above a green point is constitute at the left by the name of the robot objective (where it is going) and at the right the number of victim(s) inside over the robot capacity. 


To adapt the simulator resolution/scale, go to Main.java. In the constant variables, some are indicated to fix the resolution. 

To change the hospital position, go to Main.java. In the Main() method, go to the creation of the wanted summit, and place a “true” argument at the end of the summit constructor call, or remove it to make the summit become a classic summit.
To change the victim position, go to Main.java. In the start() method, in the first loop, put the name of the roads/summits where there is a victim.

To place the robot, go to Main.java. In the start() method, in the second loop, there are examples of how we place robot. The robot will always be placed on an edge. Do not omit to handle the right number of robot(s). 

To change the robot capacity, go to Robot.java. In the constant variables, change the value of the “CAPACITY” variable. 

To change the robot constant speed, go to Robot.java. In the constant variables, change the value of the “TIMECOEF” variable.


Some parts of the code are never used. These parts are concerning crosses. The final map does not have any cross, but it was planned to be. However, the lack of time did not permit to develop the intelligence of crossing to robots. Parts of the code concerning crosses had been kept in prospect to improve the intelligence.

Here is a link to photos and videos of the simulator and of robots. https://drive.google.com/drive/folders/1J1dHO1LULZshwB_YDiqMmbf3ftuhPYjZ?usp=sharing
