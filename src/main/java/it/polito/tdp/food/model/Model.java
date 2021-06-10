package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private Graph<Food,DefaultWeightedEdge> grafo;
	private Map<Integer,Food> idMap;
	private FoodDao dao;
	private double TTOTALE;
	private int kLiberi;
	private PriorityQueue<FoodNumber> queue;
	private List<Food> nonDisponibili;
	
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
			
			result.add(new FoodNumber(f, peso));
			
		}

		Collections.sort(result,new Comparator<FoodNumber>() {
			@Override
			public int compare(FoodNumber f1, FoodNumber f2) {
				return f2.getN().compareTo(f1.getN());
			}
		});
		if(result.size()>k) {
			return result.subList(0, k);	
		}
			
		return result;
	}
	
	public List<FoodNumber> getAdiacenti(Food fInput){
		List<FoodNumber> result = new ArrayList<>();
		for(Food f: Graphs.neighborListOf(grafo, fInput)) {
			DefaultWeightedEdge e = grafo .getEdge(f, fInput);
			double peso = grafo.getEdgeWeight(e);
			
			result.add(new FoodNumber(f, peso));
			
		}

		Collections.sort(result,new Comparator<FoodNumber>() {
			@Override
			public int compare(FoodNumber f1, FoodNumber f2) {
				return f2.getN().compareTo(f1.getN());
			}
		});
		return result;
	}
	
	public void init(int k, Food finput) {
		TTOTALE = 0;
		kLiberi = 0;
		nonDisponibili = new ArrayList<>();
		queue = new PriorityQueue<>();
		
		for(FoodNumber f: this.getAdiacenti(finput, k)) {
			queue.add(f);
			nonDisponibili.add(f.getF());
		}
	}
	
	public void run() {
		FoodNumber f;
		while((f = queue.poll()) != null) {
			kLiberi++;
			double tempoT = f.getN();
			TTOTALE = tempoT;

			List<FoodNumber> adiacenti = this.getAdiacenti(f.getF());
			
			for(FoodNumber fn: adiacenti) {
					Food food = fn.getF();
					if(!nonDisponibili.contains(food)) {
						queue.add(new FoodNumber(food, tempoT+fn.getN()));
						kLiberi--;	
						nonDisponibili.add(food);
						if(kLiberi <= 0) {
							break;
						}
					}

			}
		}
	}
	
	public int getNPreparati() {
		return this.nonDisponibili.size();
	}
	
	public double getTempo() {
		return this.TTOTALE;
	}
}
