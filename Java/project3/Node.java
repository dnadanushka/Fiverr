package project3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
	public String interId;
	public double x;
	public double y;
        /*For the dijkstra's algorithm*/
        private List<Node> shortestPath = new LinkedList<>();
        private double distance = Double.MAX_VALUE; ///converted integer to double
        Map<Node, Double> adjacentNodes = new HashMap<>();///converted integer to double
        
	
	public Node(String intersectionID, double latitude, double longitude){
		interId = intersectionID;
		x = latitude;
		y = longitude;
	}
	
	public boolean equals(String n){
		return (n.compareTo(interId)==0) ? true : false;
	}
        
        /*For the dijkstra's algorithm*/
        public void addDestination(Node destination, double distance) {///converted integer to double
            adjacentNodes.put(destination, distance);
        }
    
        public void setDistance(double i) {///converted integer to double
            this.distance = i;
        }

        public void setShortestPath(LinkedList<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }
        
        public List<Node> getShortestPath(){
            return shortestPath;
        }

        public double getDistance() {
            return distance;
        }

        public Map<Node, Double> getAdjacentNodes() {
            return adjacentNodes;
        }
        
        
}
