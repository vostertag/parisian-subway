package java_course.graph_project;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Dijkstra {

	private Graph graph;
	private double[] distance;
	private boolean[] dejaVisite;
	private Node currentNode;
	private Map<Node, Node> predecessors;
	
	public Dijkstra(Graph graph, Node startingNode){
		this.graph = graph;
		this.distance = new double[this.graph.getOrder()];
		this.dejaVisite = new boolean[this.graph.getOrder()];
		this.predecessors = new HashMap<Node, Node>();		
		this.fillWithInfinity(this.distance);
		this.fillWIthFalse(this.dejaVisite);
		Arrays.fill(distance, Double.POSITIVE_INFINITY);
		this.distance[startingNode.getId()] = 0;
		
		while (this.getMinimumPositionUnvisited(this.distance) != -1){
			this.currentNode = graph.getNodes().get(this.getMinimumPositionUnvisited(this.distance));
			this.dejaVisite[this.currentNode.getId()] = true;
			for (Edge edge : this.currentNode.getEdges()){
				if(this.dejaVisite[edge.getEndingNode().getId()] == false){
					if(this.distance[edge.getEndingNode().getId()] > this.distance[this.currentNode.getId()] + edge.getWeight()){
						edge.setPassingShortestPath((edge.getPassingShortestPath()+1));
						this.distance[edge.getEndingNode().getId()] = this.distance[this.currentNode.getId()] + edge.getWeight();                                                                                                
						this.predecessors.put(edge.getEndingNode(), this.currentNode);
					}
				}
			}
		}
		
	}
	
	public void fillWithInfinity(double[] array){
		for (double number : array){
			number = Double.POSITIVE_INFINITY;
		}
	}
	
	public void fillWIthFalse(boolean[] array){
		for (boolean bool : array){
			bool = false;
		}
	}
	
	public int getMinimumPositionUnvisited(double[] array){
		double minimum = Double.POSITIVE_INFINITY;
		int position = -1;
		for (int i=0; i<array.length ; i++){
			if (!this.dejaVisite[i]){
				if (array[i] < minimum){
					minimum = array[i];
					position = i;
				}
				
			}
		}
		return position;
	}
	
	public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
	
	public void printPathTo(Node target){
		System.out.print(getPath(target).size());
		LinkedList<Node> path = getPath(target);
		
		for(Node n : path){
			System.out.println(n.getName());
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public double[] getDistance() {
		return distance;
	}

	public void setDistance(double[] distance) {
		this.distance = distance;
	}

	public boolean[] getDejaVisite() {
		return dejaVisite;
	}

	public void setDejaVisite(boolean[] dejaVisite) {
		this.dejaVisite = dejaVisite;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public Map<Node, Node> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(Map<Node, Node> predecessors) {
		this.predecessors = predecessors;
	}
	
	
	
}
