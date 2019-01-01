package project3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;


public class Graph {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM
    private int nodeCount, edgeCount;
    private Hashtable<String, Node> nTable;
    private Hashtable<String, LinkedList<String>> eTable;
    private ArrayList<String> nIDS;
   
    public Set<Node> nodes;
    public static boolean drawRed = false;
    
    public List<Node> redPath = new LinkedList<>();
    
    

    
    public Graph(int numNodes) {
    	nodeCount = numNodes;
    	nTable = new Hashtable<String, Node>(numNodes*2);
    	eTable = new Hashtable<String, LinkedList<String>>(numNodes*2);
    	nIDS = new ArrayList<String>(numNodes);
        nodes = new HashSet<>();
    }
    
    public void insert(Node v){
    	nTable.put(v.interId, v);
    	//instantiate and hash empty LinkedList for each new Node
    	LinkedList<String> tmp = new LinkedList<String>();
    	eTable.put(v.interId, tmp);
    	nIDS.add(v.interId);
        
    }
    
    public void insert(Edge e){
    	eTable.get(e.interId1).add(e.interId2);
        
        /*Adding the adjacycy vertices*/
        Node startNode = nTable.get(e.interId1);
        Node endNode = nTable.get(e.interId2);
        double dist = distance(startNode.x,startNode.y,endNode.x,endNode.y);
        nTable.get(e.interId1).addDestination(nTable.get(e.interId2), dist);
        nTable.get(e.interId2).addDestination(nTable.get(e.interId1), dist);
        
    	edgeCount++;
    }
    
    public void delete(Edge e){
    	eTable.get(e.interId1).remove(e.interId2);
    	eTable.get(e.interId2).remove(e.interId1);
    }
    
    public boolean connected(int interId1, int interId2) {
    	return (eTable.get(interId1).contains(interId2) || eTable.get(interId2).contains(interId1));
    }
    
    public int vertices() { 
    	return nodeCount;
    }
    
    public int edges() {
    	return edgeCount;
    }
    
    private double[] maxMin(){
    	double[] maxMin = new double[4];//minX, maxX, minY, maxY
    	maxMin[0] = maxMin[1] = nTable.get(nIDS.get(0)).x;
    	maxMin[2] = maxMin[3] = nTable.get(nIDS.get(0)).y;
    	for(int i=0; i<nodeCount; i++){
    		maxMin[0] = (maxMin[0] < nTable.get(nIDS.get(i)).x) ? maxMin[0] : nTable.get(nIDS.get(i)).x;
    		maxMin[1] = (maxMin[1] > nTable.get(nIDS.get(i)).x) ? maxMin[1] : nTable.get(nIDS.get(i)).x;
    		maxMin[2] = (maxMin[2] < nTable.get(nIDS.get(i)).y) ? maxMin[2] : nTable.get(nIDS.get(i)).y;
    		maxMin[3] = (maxMin[3] > nTable.get(nIDS.get(i)).y) ? maxMin[3] : nTable.get(nIDS.get(i)).y;
    	}
    	return maxMin;
    }

    /********************************************************************************
    *********************************************************************************/
    private double distance(double startLat, double startLong,
                                       double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));
        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);
        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c ; // <-- d

    }

    private double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
        
    public Graph calculateShortestPathFromSource(Graph graph, String sourceString, String dest) {
        
        Node source = nTable.get(sourceString);
        nodes = new HashSet<Node>(nTable.values());
        
        for(Node n : nodes){
            if(n.equals(sourceString)){
                sourceString = n.interId;
                source = n;
                source.setDistance(0);
            }
        }
        
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry< Node, Double> adjacencyPair: 
              currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                    
                }
            }
            settledNodes.add(currentNode);
        }
        
        Node destNode;
        for(Node n : nodes){
            if(n.equals(dest)){
                destNode = n;  
                redPath = destNode.getShortestPath();
            }
        }
        
        
        return graph;
    }
    
    
    private Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node: unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    
    private void CalculateMinimumDistance(Node evaluationNode,
        double edgeWeigh, Node sourceNode) {
          double sourceDistance = sourceNode.getDistance();
          if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
              evaluationNode.setDistance(sourceDistance + edgeWeigh);
              LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
              shortestPath.add(sourceNode);
              evaluationNode.setShortestPath(shortestPath);
          }
    }
    
        /********************************************************************************
    *********************************************************************************/
   
    @SuppressWarnings("static-access")
    public void drawGraph(JFrame frame){
    	int margin = 3;
    	int windowSize = 600;
    	
    	double[] maxMin = maxMin();
    	System.out.printf("%f %f %f %f\n", maxMin[0], maxMin[1], maxMin[2], maxMin[3]);
    	double xSF = Math.abs(maxMin[1] - maxMin[0]);
    	double ySF = Math.abs(maxMin[3] - maxMin[2]);
    	double scaleFactor = (maxMin[1]-maxMin[0] > maxMin[3]-maxMin[2]) ? 1/(maxMin[1]-maxMin[0]) : 1/(maxMin[3]-maxMin[2]);
    
    	System.out.printf("scale factor: %f, x/y scale factor: %f\n", scaleFactor, ySF/xSF);
    	
    	frame.setSize((int)Math.ceil(windowSize*(ySF/xSF)+margin*2), (int)Math.ceil(1.36*(windowSize+margin*2)));
    	
    	scaleFactor = (xSF>ySF) ? Math.abs(.98*scaleFactor*frame.getBounds().height) : Math.abs(.96*scaleFactor*frame.getBounds().width);
    	
    	DPanel draw = new DPanel();
    	for(int i=0; i<vertices(); i++){
    		LinkedList<String> eList = eTable.get(nIDS.get(i));
    		Node v = nTable.get(nIDS.get(i));
    		for(int j=0; j<eList.size(); j++){
    			draw.addLine(	Math.abs((int)((v.y-maxMin[2])*scaleFactor))+margin,
    							Math.abs((int)((v.x-maxMin[1])*scaleFactor*1.3))+margin,
    							Math.abs((int)((nTable.get(eList.get(j)).y-maxMin[2])*scaleFactor))+margin,
    							Math.abs((int)((nTable.get(eList.get(j)).x-maxMin[1])*scaleFactor*1.3))+margin );
    		}
    	}
    	
        for(int i = 0; i< redPath.size()-1;i++){
            Node u = redPath.get(i);
            Node v = redPath.get(i+1);
            draw.addRedLine(	Math.abs((int)((u.y-maxMin[2])*scaleFactor))+margin,
    							Math.abs((int)((u.x-maxMin[1])*scaleFactor*1.3))+margin,
    							Math.abs((int)((v.y-maxMin[2])*scaleFactor))+margin,
    							Math.abs((int)((v.x-maxMin[1])*scaleFactor*1.3))+margin );
            
            
        }

    	frame.setLocationRelativeTo(null);
    	frame.add(draw);
    	frame.setVisible(true);
    }
    
    @SuppressWarnings("serial")
    private static class DPanel extends JPanel{
    	
    	private static ArrayList<int[]> lines = new ArrayList<int[]>();
        private static ArrayList<int[]> Redlines = new ArrayList<int[]>();
        
    	public static void addLine(int x1, int y1, int x2, int y2){
    		int[] d = {x1, y1, x2, y2};
    		lines.add(d);
    	}
        
        public static void addRedLine(int x1, int y1, int x2, int y2){
    		int[] d = {x1, y1, x2, y2};
    		Redlines.add(d);
    	}
        
    	
    	public void paintComponent(Graphics g){
    		super.paintComponent(g);
    		for(int i=0; i<lines.size(); i++){
    			int[] d = lines.get(i);
    			g.drawLine(d[0],d[1],d[2],d[3]);
    		}
                
                if(drawRed){
                    g.setColor(Color.red);
                    for(int i=0; i<Redlines.size(); i++){
                            int[] d = Redlines.get(i);
                            g.drawLine(d[0],d[1],d[2],d[3]);
                    }
                }
                
    	}
    }

    
}