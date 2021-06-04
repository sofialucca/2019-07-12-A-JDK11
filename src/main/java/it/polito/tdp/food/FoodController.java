/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodNumber;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if(!isValidGrafo()) {
    		return;
    	}
    	String porz = this.txtPorzioni.getText(); 
    	model.creaGrafo(Integer.parseInt(porz));
    	
    	txtResult.appendText("GRAFO PER CIBI CON PORZIONI MINORI O PARI A: " + porz);
    	txtResult.appendText("\n#VERTICI: " + model.getVertex().size());
    	txtResult.appendText("\n#ARCHI: " + model.getEdgeSet());
    	
    	this.boxFood.getItems().setAll(model.getVertex());
    	this.btnCalorie.setDisable(false);
    	this.boxFood.setDisable(false);
    	this.btnSimula.setDisable(false);
    	this.txtK.setDisable(false);
    }
    
    private boolean isValidGrafo() {
		String s = this.txtPorzioni.getText();
		if(s.equals("")) {
			txtResult.appendText("ERRORE: scrivere un valore per le porzioni");
			return false;
		}
		try {
			if(Integer.parseInt(s) < 0) {
				txtResult.appendText("ERRORE: le porzioni devono essere un numero maggiore di zero");
				return false;
			}
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE: le porzioni devono essere un numero intero.");
			return false;
		}
		return true;
	}

	@FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	Food f = this.boxFood.getValue();
    	if(f == null) {
    		txtResult.appendText("ERRORE: scegliere un cibo.");
    		return;
    	}
    	List<FoodNumber> result = model.getAdiacenti(f, 5);
    	if(result.isEmpty()) {
    		txtResult.appendText("NON VI SONO ADIACENTI A : " + f);
    	}else {
    		txtResult.appendText("I 5 CIBI DI " + f + " CON CALORIE CONGIUNTE MASSIME SONO:\n");
    		for(FoodNumber fn : result) {
    			txtResult.appendText("\n" + fn.toString());
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	if(!isValid()) {
    		return;
    	}
    	String k = this.txtK.getText();
    	Food f = this.boxFood.getValue();
    	model.init(Integer.parseInt(k), f);
    	model.run();
    	int nPiatti = model.getNPreparati();
    	if(nPiatti == 0) {
    		txtResult.appendText("SIMULAZIONE NON POSSIBILE CON " + f + " PER " + k + " STAZIONI DI LAVORO");
    	}else {
    		txtResult.appendText("SIMULAZIOE DI " + k + " STAZIONI, PARTENDO DA " + f);
    		txtResult.appendText("\n\nTOTALE PREPRAZIONI = " + model.getNPreparati());
    		txtResult.appendText("\n\nTEMPO IMPIEGATO = " + String.format("%.5g", model.getTempo()) + " minuti");
    	}
    }

    private boolean isValid() {
    	boolean check = true;
		if(this.boxFood.getValue() == null) {
			txtResult.appendText("ERRORE: selezionare un cibo\n");
			check = false;
		}
		try {
			int k = Integer.parseInt(this.txtK.getText());
			if(k<1 || k>10) {
				txtResult.appendText("ERRORE: k deve esseere un valore tra 1 e 10");
				return false;
			}
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: k deve essere un numero intero");
				return false;
			}
		return check;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
