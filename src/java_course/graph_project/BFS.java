package java_course.graph_project;
import java.util.ArrayList;
import java.util.List;

public class BFS {
	
	private Graph graph;
	private List<Node> dejaVisite;
	private List<Node> queue;

	public BFS(Graph g, Node startingNode){
		this.graph = g;
		this.dejaVisite = new ArrayList();
		this.queue = new ArrayList();
		this.queue.add(startingNode);
		while(!this.queue.isEmpty()){
			this.dejaVisite.add(queue.get(0));
			this.queue.remove(0);
			for(Node node : this.dejaVisite.get(this.dejaVisite.size()-1).getNeighbors()){
				if(!isInArray(node, this.dejaVisite) && !isInArray(node, this.queue)){
					this.queue.add(node);
				}
			}
		}
	}
	
	public void show(){
		for (Node node : this.dejaVisite){
			System.out.println(node.getName());
		}
	}
	
	public boolean isInArray(Node nodeSearched, List<Node> list){
		for (Node node : list){
			if (node.getId() == nodeSearched.getId()){
				return true;
			}
		}
		return false;
	}
}
