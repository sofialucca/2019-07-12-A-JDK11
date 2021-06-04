package it.polito.tdp.food.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(5);
		m.init(3, m.getVertex().get(3));
		m.run();
		System.out.println(m.getNPreparati());
		System.out.println(m.getTempo());
	}

}
