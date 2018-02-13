package java_course.graph_project;

public class Edge {
	
	
	//variables
	private double weight;
	private Node StartingNode;
	private Node EndingNode;
	private int passingShortestPath;
	
	
	//constructeur arr�te simple
	public Edge(Node startingNode, Node endingNode) {;
		this.StartingNode = startingNode;
		this.EndingNode = endingNode;
		this.weight = 1;
		this.passingShortestPath = 0;
	}

	
	//constructeur arr�te pond�r�
	public Edge(Node startingNode, Node endingNode, double weight) {;
		this.StartingNode = startingNode;
		this.EndingNode = endingNode;
		this.weight = weight;
		this.passingShortestPath = 0;
	}	
	
	
	//Getter & Setter
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Node getStartingNode() {
		return StartingNode;
	}

	public void setStartingNode(Node startingNode) {
		StartingNode = startingNode;
	}

	public Node getEndingNode() {
		return EndingNode;
	}

	public void setEndingNode(Node endingNode) {
		EndingNode = endingNode;
	}


	public int getPassingShortestPath() {
		return passingShortestPath;
	}


	public void setPassingShortestPath(int passingShortestPath) {
		this.passingShortestPath = passingShortestPath;
	}
	
	
}