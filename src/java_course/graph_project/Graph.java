package java_course.graph_project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	
	//variables
	private int order;
	private int numbebOfEdges;
	private List<Node> nodes;
	private Node testedNode;
	
	
	//constructeur basique pour graphe vide
	public Graph(){
		this.order = 0;
		this.numbebOfEdges = 0;
		this.nodes = new ArrayList();
	}
	
	public void show(){
		for(Node node : nodes){
			System.out.println(node.getName());
		}
	}
	// On lit un fichier contenant une ligne de m�tro et on l'ajoute au graphe
	public void addData(InputStream input, int ligne) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line;
		Node previous = null;
		while ((line = reader.readLine()) != null) {
			String stationName = "";	
			String latitude = "";
			String longitude ="";
			int numeroData = 1;
			for(int i=0 ; i<line.length() ; i++){
				if(line.charAt(i) == ','){
					numeroData +=1;
				}else if(numeroData == 3 && line.charAt(i) != ','){
					stationName += line.charAt(i);
				}else if(numeroData == 5 && line.charAt(i) != ','){
					latitude += line.charAt(i);
				}else if(numeroData == 6 && line.charAt(i) != ','){
					longitude += line.charAt(i);
				}
				
			}
			double latitudeDouble = Double.parseDouble(latitude);
			double longitudeDouble = Double.parseDouble(longitude);
			this.testedNode = new Node(this.order, stationName, latitudeDouble, longitudeDouble, ligne);
			this.addNode(this.testedNode);
			if (previous == null){
				previous = this.testedNode;
			}
			else{
				double weight = Math.sqrt(Math.pow((this.testedNode.getLatitude() - previous.getLatitude() ),2) + Math.pow((this.testedNode.getLongitude() - previous.getLongitude()),2));
				Edge edge = new Edge(this.testedNode, previous, weight);
				Edge reverseEdge = new Edge(previous, this.testedNode, weight);
				this.addEdge(edge);
				this.addEdge(reverseEdge);
				previous = this.testedNode;
			}
		}
	}
	//Djikstra diameter
	public LinkedList<Node> getDiameter(){
		LinkedList<Node> longestPath = new LinkedList<Node>();
		Double max = 0.00;
		for(int i =0; i<this.order; i++){
			for(int j=0; j<=i; j++){
				Dijkstra dji = new Dijkstra(this, this.getNodes().get(i));
				if(dji.getDistance()[this.getNodes().get(j).getId()] > max){
					longestPath = dji.getPath(this.getNodes().get(j));
					max = dji.getDistance()[this.getNodes().get(j).getId()];
				}
			}
		}
		return longestPath;
	}
	
	public LinkedList<Node> getPathWithoutCluster(Node start, Node end){
		LinkedList<Node> longestPath = new LinkedList<Node>();
		Double max = 0.00;
		for(int i =0; i<this.order; i++){
			for(int j=0; j<=i; j++){
				Dijkstra dji = new Dijkstra(this, this.getNodes().get(i));
				if(dji.getDistance()[this.getNodes().get(j).getId()] > max){
					longestPath = dji.getPath(this.getNodes().get(j));
					max = dji.getDistance()[this.getNodes().get(j).getId()];
				}
			}
		}
		double averageClusterLevel = 0;
		for(Node node : this.getNodes()){
			for (Edge edge : node.getEdges()){
				averageClusterLevel += edge.getPassingShortestPath();
			}
		}
		averageClusterLevel /= (this.order*(this.order + 1))/2;
		System.out.println("avglevel: "+ averageClusterLevel);
		Graph graph2 = this;
		LinkedList<Node> toRemove = new LinkedList<Node>();
		System.out.println("remove : " + averageClusterLevel*100);
		for(Node node : this.getNodes()){
			if(node.isAboveAvg(averageClusterLevel)){
				if(!node.getName().equals(start.getName()) && !node.getName().equals(end.getName())){
					toRemove.add(node);
				}
			}
		}
		System.out.println("On remove : ");
		for(Node node : toRemove){
			System.out.println(node.getName());
		}
		DijkstraCluster dji = new DijkstraCluster(graph2, this.getNodes().get(start.getId()), toRemove);
		return dji.getPath(end);
	}
	
	//ajoute l'arrete si celle-ci n'existe pas d�ja
	public void addEdge(Edge edge){
		if (!edgeExist(edge)){
			edge.getStartingNode().addEdge(edge);
		}
	}
	
	public void removeNode(Node node){
		this.getNodes().remove(node);
	}
	
	
	//Regarde si l'arrete existe deja sur l'un des noeuds du graphe
	public boolean edgeExist(Edge edge){
			if(edge.getStartingNode().edgeExist(edge)){
				return true;
			}
		return false;
	}
	
	
	//ajoute le noeud si celui-ci n'existe pas d�ja
	public boolean addNode(Node node){
		if (!nodeExist(node)){
			this.nodes.add(node);
			this.order +=1;
			return true;
		}
		return false;
	}
	
	
	//Regarde si un noeud existe d�ja dans le graphe
	public boolean nodeExist(Node node){
		for (Node existingNode : this.nodes){
			if (existingNode.getName().equals(node.getName())){
				this.testedNode = existingNode;
				return true;
			}
		}
		return false;
	}
	
	
	//Getters & Setters
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getNumbebOfEdges() {
		return numbebOfEdges;
	}
	public void setNumbebOfEdges(int numbebOfEdges) {
		this.numbebOfEdges = numbebOfEdges;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

}
