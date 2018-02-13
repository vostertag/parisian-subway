package java_course.graph_project;
import java.util.ArrayList;
import java.util.List;

public class Node {
	
	
	//variables
	private int id;
	private int ligne;
	private String name;
	private List<Edge> edges;
	private double latitude;
	private double longitude;
	
	
	//constructeur avec etiquette
	public Node(int id, String name, double latitude, double longitude, int ligne){
		this.id = id;
		this.edges = new ArrayList();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ligne = ligne;
	}

	public void showEdges(){
		for (Edge edge : edges){
			System.out.println("("+ edge.getStartingNode().getName() + ";" + edge.getEndingNode().getName() +")");
			System.out.println("Clustering level : " + edge.getPassingShortestPath());
		}
	}
	
	public boolean isAboveAvg(Double avgLevel){
		Double clusterLevel = 0.00;
		for(Edge e : this.getEdges()){
			clusterLevel += e.getPassingShortestPath();
		}
		if(clusterLevel > avgLevel*500){
			System.out.println(clusterLevel);
			return true;
		}
		return false;
	}
	
	
	//ajoute l'arrete si celle-ci n'existe pas d�ja
	public void addEdge(Edge edge){
		if (!edgeExist(edge)){
			this.edges.add(edge);
		}
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	//Regarde si une arr�tte existe d�ja
	public boolean edgeExist(Edge edge){
		for (Edge existingEdge : this.edges){
			if (existingEdge.getEndingNode().equals(edge.getEndingNode())  ){
				return true;
			}
		}
		return false;
	}
	
	//R�cup�re la liste des Nodes connect�s � celui-ci
	public List<Node> getNeighbors(){
		List<Node> neighbors = new ArrayList();
		for(Edge edge : this.edges) {
			neighbors.add(edge.getEndingNode());
		}
		return neighbors;
	}
	
	//Getters & Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	public int getLigne() {
		return ligne;
	}
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}


}

