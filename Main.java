package graphCutBasedPartition;
import java.util.*;

public class Main<T> {
	
	private Graph<T> mygraph;

	public Main(Graph<T> g) {
		mygraph=g;
	}
	
    private boolean bfs(Map<T, Map<T, Integer>> rGraph, T s,  T t, Map<T, T> parent) {   
    	 Map<T, Boolean> visited = new HashMap<>(); 
         for(T x: rGraph.keySet()) 
             visited.put(x,false);   
        Queue<T> q = new LinkedList<>(); 
        q.add(s); 
        visited.put(s,true); 
        parent.put(s,null);   
        while (!q.isEmpty()) { 
           T u = q.poll(); 
           for(T v: rGraph.keySet()) {
                if (rGraph.get(u).get(v) >0 && !visited.get(v) ){ 
                    q.offer(v); 
                    parent.put( v,u); 
                    visited.put(v, true); 
                } 
            } 
        } 
        return (visited.get(t) == true); 
    } 
      
    private void dfs(Map<T, Map<T, Integer>> rGraph, T s,  Map<T, Boolean> visited) { 
        visited.put(s,true); 
        for(T v: rGraph.keySet()) {
                if (rGraph.get(s).get(v)>0 && !visited.get(v)) { 
                    dfs(rGraph, v, visited); 
                } 
        } 
    } 
  
    public  Map<T, Integer> cut_partition(Map<T, Map<T, Integer>> g, T s, T t) { 
        T u,v; 
        Map<T, Map<T, Integer>> rGraph =mygraph.cloneGraph(g);
        Map<T, T> parent = new HashMap<>(); 
        for(T x: rGraph.keySet()) {
        	parent.put(x, null);
        }    
        while (bfs(rGraph, s, t, parent)) { 
            int pathFlow = Integer.MAX_VALUE;          
            for (v=t; v!=s; v=parent.get(v)) 
            { 
                u = parent.get(v); 
                pathFlow = Math.min(pathFlow, rGraph.get(u).get(v)); 
            } 
            for (v=t; v!=s; v=parent.get(v)) 
            { 
                u = parent.get(v); 
                //u-v
                int org = rGraph.get(u).get(v);
                int update = org-pathFlow;
                rGraph.get(u).put(v, update);             
                //reverse v-u   
                org = rGraph.get(v).get(u);
                update = org+pathFlow;
                rGraph.get(v).put(u, update); 
            } 
        } 
          
        Map<T, Boolean> isVisited = new HashMap<>(); 
        for(T x: rGraph.keySet()) 
        	isVisited.put(x,false);         
        dfs(rGraph, s, isVisited);   

        //return result
        Set<T> upStream=new HashSet<>();
        Set<T> downStream = new HashSet<>();
        Set<T> central = new HashSet<>();
        upStream.add(s);
        downStream.add(t);
        for(T x: rGraph.keySet()) {
        	if(x!=s && x!=t)
        	central.add(x);
        }
        for(T i: rGraph.keySet()) {
        	for(T j: rGraph.keySet()) { 
                if (g.get(i).get(j)>0 && isVisited.get(i) && !isVisited.get(j)) { 
                	System.out.println(i + " - " + j); 
                    upStream.add(i);
                    downStream.add(j);
                    central.remove(i);
                    central.remove(j);
                } 
            } 
        } 
        System.out.println("upstream: "+ upStream);
        System.out.println("downstream: "+downStream);
        System.out.println("central: "+central);
        Map<T, Integer> res = new HashMap<>();
        for(T i: rGraph.keySet()) {
        	if (upStream.contains(i))
        		res.put(i,-1);
        	else if (downStream.contains(i))
        		res.put(i, 1);
        	else
        		res.put(i, 0);
        }
        return res;  
    } 
    
    public int fordFulkerson(Map<T, Map<T, Integer>> graph, T s, T t)  { 
        T u, v; 
        Map<T, Map<T, Integer>> rGraph =mygraph.cloneGraph(graph);
        Map<T, T> parent = new HashMap<>(); 
        for(T x: rGraph.keySet()) {
        	parent.put(x, null);
        }
        int max_flow = 0; 
        while (bfs(rGraph, s, t, parent)) 
        { 
            int path_flow = Integer.MAX_VALUE; 
            for (v=t; v!=s; v=parent.get(v)) 
            { 
                u = parent.get(v); 
                path_flow = Math.min(path_flow, rGraph.get(u).get(v)); 
            } 
            for (v=t; v != s; v=parent.get(v)) 
            { 
                u = parent.get(v); 
                //u-v
                int org = rGraph.get(u).get(v);
                int update = org-path_flow;
                rGraph.get(u).put(v, update);             
                //reverse v-u   
                org = rGraph.get(v).get(u);
                update = org+path_flow;
                rGraph.get(v).put(u, update); 
            } 
            max_flow += path_flow; 
        } 
        return max_flow; 
    } 
  
    public static void main(String args[]) { 
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
	    g.addEdge(3, 5, 20);   
	    g.addEdge(4, 5, 4); 	    
	    g.fillEmptyEdge();	  

	    Main<Integer> m = new Main<>(g); 
	    System.out.println("The maximum possible flow is " + 
                   m.fordFulkerson(g.adj, 0, 5)); 
        Map<Integer, Integer> res = m.cut_partition(g.adj, 0, 5);
        System.out.println("result: "+res);
        System.out.println();
        
	    //test case2, string
    	Graph<String> g1=new Graph<>();
		g1.add("S");
		g1.add("A");
		g1.add("B");
		g1.add("C");
		g1.add("T");
	  
	    g1.addEdge("S", "A", 7); 
	    g1.addEdge("S", "B", 5); 
	    g1.addEdge("A", "C", 4); 
	    g1.addEdge("A", "B", 4); 	    	       
	    g1.addEdge("C", "T", 6); 
	    g1.addEdge("B", "T", 7); 
	    g1.fillEmptyEdge();
    
	    Main<String> m1 = new Main<>(g1); 
	    System.out.println("The maximum possible flow is " + 
                   m1.fordFulkerson(g1.adj, "S", "T")); 
        Map<String, Integer> res1 = m1.cut_partition(g1.adj, "S", "T");
        System.out.println("result: "+res1);
    } 
}
