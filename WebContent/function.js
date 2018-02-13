var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	
	var start = getUrlParameter('start');
	var end = getUrlParameter('end');
	var getDiameter = getUrlParameter('getDiameter');
	var longest = getUrlParameter('longest');
	var noCluster = getUrlParameter('noCluster');
	if(start == null){
		start = "";
	}if(end == null){
		end = "";
	}if(getDiameter == null){
		getDiameter = "";
	}if(longest == null){
		longest = "";
	}if(noCluster == null){
		noCluster = "";
	}

$( document ).ready(function() {
	var station1 = "";
	var station2 = "";
	var defaultColor1= "";
		
	$.post("/web_metro/ajax", {start: start, end: end, getDiameter: getDiameter, longest: longest, noCluster: noCluster}, function(data){
		$("#loading").hide();
		var svg = d3.select("svg"),
	    width = +svg.attr("width"),
	    height = +svg.attr("height");
		
		var graph = JSON.parse(data);

		var color = ["#FFCD00", "#003CA6", "#837902", "#6EC4E8", "#CF009E", "#FF7E2E", "#6ECA97", "#704B1C", "#6ECA97", "#E19BDF", "#B6BD00", "#C9910D", "#704B1C", "#007852", "#6EC4E8", "#62259D", "#FF0000"];
		
		var simulation = d3.forceSimulation()
		    .force("link", d3.forceLink().id(function(d) { return d.id; }))
		    .force("charge", d3.forceManyBody())
		    .force("center", d3.forceCenter(width / 2, height / 2));
		
		
		/*d3.json("reseau.json", function(error, graph) {*/
		 /* if (error) throw error;*/
		
		  var link = svg.append("g")
		      .attr("class", "links")
		    .selectAll("line")
		    .data(graph.links)
		    .enter().append("line")
		      .attr("stroke-width", function(d) { return Math.sqrt(d.value); });
		  
		
		  var node = svg.append("g")
		      .attr("class", "nodes")
		    .selectAll("circle")
		    .data(graph.nodes)
		    .enter().append("circle")
		      .attr("r", 5)
		      .attr("fill", function(d) { return color[d.group-1]; })
		  .on("click", clicked);
		  
		  node.append("title")
		  .text(function(n) {
		      return n.name;
		  });
		
		  simulation
		      .nodes(graph.nodes)
		      .on("tick", ticked);
		
		  simulation.force("link")
		      .links(graph.links);
		  
		  function clicked(){
			  var clickedNode = d3.select(this);
			  if(station1 == clickedNode.attr("id")){
				  station1 = "";
				  clickedNode.style("fill", defaultColor1);
			  }else{
				  if(station1 != ""){
					  station2 = clickedNode.attr("id");
					  clickedNode.style("fill", "black");
					  var add = "";
					  if($("#noCluster").is(":checked")){
						  add = "&noCluster=1"
					  }
					  if($("#longestPath").is(":checked")){
						  window.location = "?start="+station1+"&end="+station2+"&longest=1"+add;
					  }else{
						  window.location = "?start="+station1+"&end="+station2+add;
					  }
				  }else{
					  station1 = clickedNode.attr("id");
					  clickedNode.style("fill", "black");
				  }
			  }
		  }
		  
		  function ticked() {
		    link
		        .attr("x1", function(d) { return getX(d.source.x); })
		        .attr("y1", function(d) { return getY(d.source.y); })
		        .attr("x2", function(d) { return getX(d.target.x); })
		        .attr("y2", function(d) { return getY(d.target.y); });
		
		    node
		    	.attr("id", function(d) { return d.id })
		        .attr("cx", function(d) { return getX(d.x); })
		        .attr("cy", function(d) { return getY(d.y); });
		  }
		  
		/*});*/
		
		function dragstarted(d) {
		  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
		  d.fx = getX(d.x);
		  d.fy = getY(d.y);
		}
		
		function dragged(d) {
		  d.fx = getX(d3.event.x);
		  d.fy = getY(d3.event.y);
		}
		
		function dragended(d) {
		  if (!d3.event.active) simulation.alphaTarget(0);
		  d.fx = null;
		  d.fy = null;
		}
	});
	
	
	var bounds = {
	    	"minLon": 2.228313661724341,
	    	"maxLon": 2.464319398948263,
	    	"maxLat": 48.76876863574218,
	    	"minLat": 48.94611087248738
	    }
	    var dimensions = {
	    	width: $("svg").width()-30,
	    	height:$("svg").height()-30
	    }	
	    function getX(x) {
	    	var position = (x - bounds.minLon) / (bounds.maxLon - bounds.minLon); 
	    	return dimensions.width*position+15;
	    }
	    function getY(y) {
	      var position = (y - bounds.minLat) / (bounds.maxLat - bounds.minLat); 
	    	return dimensions.height*position+15;
	    }
	    
	$("#getDiameter").on("click", function(){
		window.location = "?getDiameter=1";
	});
});