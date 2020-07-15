# Graph Cut-Based Parition

## Problem Statement
Given a flow network, meaning:
- a directed graph G
- designated source and sink nodes s and t
- edge capacities

return a partition of the nodes of G into three sets:
- "upstream nodes": contains all nodes v such that, for all minimum s-t cuts (A, B), v is in A (always on the source side)
- "downstream nodes": contains all nodes v such that, for all minimum s-t cuts (A, B), v is in B (always on the sink side)
- "central nodes": contains all nodes v such that there exists some minimum cut where v is on the source side and there exists some minimum cut where v is on the sink side.

Note that, for any flow network:

- the upstream nodes must contain s, the source, but may not contain any other nodes;
- the downstream nodes must contain t, the sink, but may not contain any other nodes;
- the set of central nodes may be empty;
- but, the three sets are disjoint and their union must be all the nodes in the network.

## Implementation
Implement an algorithm to produce this partition of nodes. In particular, you should implement a function
	static <T> Map<T, Integer> cut_partition(Map<T, Map<T, Integer>> G, T source, T sink)
	
The weighted, directed, input graph G is represented as a map of maps. In the map G, keys are nodes (of generic type T), and values are maps. In those maps, keys are nodes (of
generic type T), and values are integers.

So, if a node u is a key in the map G, and its map has a key-value pair v, i, then this should be interpreted as the graph having directed edge (u, v) with capacity i.

The source and sink parameters are of generic type T; these identify the source and sink nodes of the flow network and can be assumed to be keys in the map G.

The return type is a map from nodes (of generic type T) to integers. This map should contain all the nodes of the graph G as keys. The corresponding value for each node is one of three integers:

- -1, if the node is an upstream node (on the source side of all min cuts)
- 1, if the node is a downstream node (on the sink side of all min cuts)
- 0, if the node is a central node