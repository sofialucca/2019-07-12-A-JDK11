package it.polito.tdp.food.model;

public class FoodNumber implements Comparable<FoodNumber>{

	private Food f;
	private Double n;
	
	public FoodNumber(Food f, Double n) {
		super();
		this.f = f;
		this.n = n;
	}

	public Food getF() {
		return f;
	}

	public void setF(Food f) {
		this.f = f;
	}

	public Double getN() {
		return n;
	}

	public void setN(Double n) {
		this.n = n;
	}

	@Override
	public int compareTo(FoodNumber o) {
		// TODO Auto-generated method stub
		return this.n.compareTo(o.n);
	}

	@Override
	public String toString() {
		return f + " - " + String.format("%.5g", n);
	}
	
	
}
