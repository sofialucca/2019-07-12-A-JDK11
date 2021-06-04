package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private Graph<Food,DefaultWeightedEdge> grafo;
	private Map<Integer,Food> idMap;
	private FoodDao dao;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(int porz) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		
		dao.setVertici(idMap, porz);
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Adiacenti a : dao.getArchi(idMap)) {
			DefaultWeightedEdge e = grafo.getEdge(a.getF2(), a.getF1());
			if(e == null) {
				Graphs.addEdge(grafo, a.getF1(), a.getF2(), a.getCal());
			}
		}
	}
	
	public List<Food> getVertex() {
		List<Food> result = new ArrayList<>(grafo.vertexSet());
		Collections.sort(result);
		return result;
	}
	
	public int getEdgeSet() {
		return grafo.edgeSet().size();
	}
	
	public List<FoodNumber> getAdiacenti(Food fInput, int k){
		List<FoodNumber> result = new ArrayList<>(k);
		for(Food f: Graphs.neighborListOf(grafo, fInput)) {
			DefaultWeightedEdge e = grafo .getEdge(f, fInput);
			double peso = grafo.getEdgeWeight(e);
			
			FoodNumber nuovo = new FoodNumber(f, peso);
			
			try{
				FoodNumber ultimo = result.get(k-1);
				if(ultimo.getN() < peso){
					ultimo = nuovo;
				}
			}catch(IndexOutOfBoundsException iob) {

				result.add(nuovo);
			}

			
			Collections.sort(result);
		}
		return result;
	}
}
