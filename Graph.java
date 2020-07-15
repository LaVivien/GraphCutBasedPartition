package graphCutBasedPartition;

import java.util.*;

public class Graph<T> { 
    
    Map<T, Map<T,Integer>> adj = new HashMap<>(); 
	
	public void add(T t) {
		adj.put(t, new HashMap<T, Integer>());
	}

	public void fillEmptyEdge(){
		for(T t: adj.keySet()) {
			Map<T, Integer> map=adj.get(t);
			for (T t1 : adj.keySet()) {
				if(map.get(t1)==null) {					
					map.put(t1, 0);					
				}				
			}			
		}
	}

	public void addEdge(T u, T v, int w) { 
		Map<T, Integer> edges = adj.get(u);
		edges.put(v,w);
		adj.put(u, edges);	 
	} 
	
	public Map<T, Map<T, Integer>> cloneGraph( Map<T, Map<T, Integer>> g) {
		Map<T, Map<T, Integer>> clone = new HashMap<>();
		for(T t: g.keySet()) {
	    	clone.put(t, new HashMap<>());
	    	Map<T, Integer> edges1 = clone.get(t);
	    	Map<T, Integer> edges = g.get(t);
	    	for (Map.Entry<T, Integer> e: edges.entrySet()) {
				edges1.put(e.getKey(), e.getValue());
	    	}
			clone.put(t, edges1);	 
		}
		return clone;
	}
	
	public static void main(String[] arags) { 

	    //case1  integer
		Graph<Integer> g=new Graph<>();
		g.add(0);
		g.add(1);
		g.add(2);
		g.add(3);
		g.add(4);
		g.add(5);
	    g.addEdge(0, 1, 16); 
	    g.addEdge(0, 2, 13); 	    
	    g.addEdge(1, 2, 10); 
	    g.addEdge(2, 1, 4); 	    
	    g.addEdge(1, 3, 12); 
	    g.addEdge(3, 2, 9); 
	    g.addEdge(2, 4, 14); 
	    g.addEdge(4, 3, 7); 	    
	    g.addEdge(3,5, 20);   
	    g.addEdge(4, 5, 4); 	    
	    g.fillEmptyEdge();
	    System.out.println(g.adj);
	    
	    int org = g.adj.get(0).get(1);
        int update = org-2;
        g.adj.get(0).put(1, update); 
        System.out.println(g.adj);
	    
	    Map<Integer, Map<Integer, Integer>> clone =g.cloneGraph(g.adj);
	    System.out.println(clone);
	 
	    //case 2 string
		Graph<String> g1=new Graph<>();
		g1.add("S");
		g1.add("A");
		g1.add("B");
		g1.add("C");
		g1.add("T");
	  
	    g1.addEdge("S", "A", 7); 
	    g1.addEdge("A", "B", 4); 	    
	    g1.addEdge("S", "B", 5); 
	    g1.addEdge("A", "C", 4); 	    
	    g1.addEdge("B", "T", 7); 
	    g1.addEdge("C", "T", 6); 
	    g1.fillEmptyEdge();
	    System.out.println(g1.adj);
    
	    int org1 = g1.adj.get("S").get("A");
	    int update1 = org1-2;
	    g1.adj.get("S").put("A", update1); 
	    System.out.println(g1.adj);
	    
	    Map<String, Map<String, Integer>> clone1 =g1.cloneGraph(g1.adj);
	    System.out.println(clone1);
	} 
}