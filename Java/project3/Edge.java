package project3;

public class Edge {
	public String roadId;
	public String interId1;
	public String interId2; // an edge from vertex ID v to vertex ID w
	
	public Edge(String roadId, String intersectionId1, String intersectionId2) {
		this.roadId = roadId;
		interId1 = intersectionId1;
		interId2 = intersectionId2;
	}
}
