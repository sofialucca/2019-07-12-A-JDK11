package it.polito.tdp.food.model;

public class Adiacenti {

	private Food f1;
	private Food f2;
	private Double cal;
	
	public Adiacenti(Food f1, Food f2, Double cal) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.cal = cal;
	}

	public Food getF1() {
		return f1;
	}

	public void setF1(Food f1) {
		this.f1 = f1;
	}

	public Food getF2() {
		return f2;
	}

	public void setF2(Food f2) {
		this.f2 = f2;
	}

	public Double getCal() {
		return cal;
	}

	public void setCal(Double cal) {
		this.cal = cal;
	}
	
}
