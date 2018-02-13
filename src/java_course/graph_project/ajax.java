package java_course.graph_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ajax
 */
@WebServlet("/ajax")
public class ajax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ajax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String getDiameter = request.getParameter("getDiameter");
		String longest = request.getParameter("longest");
		String noCluster = request.getParameter("noCluster");
		PrintWriter out = response.getWriter();
		Graph graph = new Graph();
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro1.txt"),1);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro2.txt"),2);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro3.txt"),3);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro3b.txt"),4);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro4.txt"),5);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro5.txt"),6);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro6.txt"),7);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro7.txt"),8);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro7b.txt"),9);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro8.txt"),10);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro9.txt"),11);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro10.txt"),12);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro11.txt"),13);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro12.txt"),14);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro13.txt"),15);
		graph.addData(getServletContext().getResourceAsStream("/WEB-INF/metro14.txt"),16);
		ArrayList<HashMap<Object, Object>> allNodes = new ArrayList<HashMap<Object, Object>>();
		ArrayList<HashMap<Object, Object>> allEdges = new ArrayList<HashMap<Object, Object>>();
		HashMap<Object, Object> jsonBuilder = new HashMap<Object, Object>();
		ArrayList<Double> latitudes = new ArrayList<Double>();
		ArrayList<Double> longitudes = new ArrayList<Double>();
		for(Node node : graph.getNodes()){
			HashMap<Object, Object> nodeMap = new HashMap<Object, Object>();
			nodeMap.put("id", node.getId());
			nodeMap.put("group", node.getLigne());
			nodeMap.put("name", node.getName());
			Double x = node.getLongitude();
		    Double y = node.getLatitude();
		    latitudes.add(x);
		    longitudes.add(y);
			nodeMap.put("fx", x);
			nodeMap.put("fy", y);
			allNodes.add(nodeMap);
			for(Edge edge : node.getEdges()){
				HashMap<Object, Object> edgeMap = new HashMap<Object, Object>();
				edgeMap.put("source", edge.getStartingNode().getId());
				edgeMap.put("target", edge.getEndingNode().getId());
				edgeMap.put("value", 1);
				allEdges.add(edgeMap);
			}
			
		}
		if(start != "" && end != ""){
			LinkedList<Node> path = new LinkedList<Node>();
			if(longest.equals("1")){
				LongestDijkstra dij = new LongestDijkstra(graph, graph.getNodes().get(Integer.parseInt(start)));
				path = dij.getPath(graph.getNodes().get(Integer.parseInt(end)));
			}else{
				if(noCluster.equals("1")){
					path = graph.getPathWithoutCluster(graph.getNodes().get(Integer.parseInt(start)), graph.getNodes().get(Integer.parseInt(end)));
				}else{
					Dijkstra dij = new Dijkstra(graph, graph.getNodes().get(Integer.parseInt(start)));
					path = dij.getPath(graph.getNodes().get(Integer.parseInt(end)));
				}
			}
			for(Node node : path){
				HashMap<Object, Object> nodeMap = new HashMap<Object, Object>();
				nodeMap.put("id", node.getId());
				nodeMap.put("group", 17);
				nodeMap.put("name", node.getName());
				Double x = node.getLongitude();
			    Double y = node.getLatitude();
			    latitudes.add(x);
			    longitudes.add(y);
				nodeMap.put("fx", x);
				nodeMap.put("fy", y);
				allNodes.add(nodeMap);
				for(Edge edge : node.getEdges()){
					HashMap<Object, Object> edgeMap = new HashMap<Object, Object>();
					edgeMap.put("source", edge.getStartingNode().getId());
					edgeMap.put("target", edge.getEndingNode().getId());
					edgeMap.put("value", 1);
					allEdges.add(edgeMap);
				}
			}
		}else if(getDiameter.equals("1")){
			LinkedList<Node> path = graph.getDiameter();
			for(Node node : path){
				HashMap<Object, Object> nodeMap = new HashMap<Object, Object>();
				nodeMap.put("id", node.getId());
				nodeMap.put("group", 17);
				nodeMap.put("name", node.getName());
				Double x = node.getLongitude();
			    Double y = node.getLatitude();
			    latitudes.add(x);
			    longitudes.add(y);
				nodeMap.put("fx", x);
				nodeMap.put("fy", y);
				allNodes.add(nodeMap);
				for(Edge edge : node.getEdges()){
					HashMap<Object, Object> edgeMap = new HashMap<Object, Object>();
					edgeMap.put("source", edge.getStartingNode().getId());
					edgeMap.put("target", edge.getEndingNode().getId());
					edgeMap.put("value", 1);
					allEdges.add(edgeMap);
				}
			}
		}
		jsonBuilder.put("nodes", allNodes);
		jsonBuilder.put("links", allEdges);
		JSONObject json = new JSONObject(jsonBuilder);
		out.println(json.toString());
	}

}
