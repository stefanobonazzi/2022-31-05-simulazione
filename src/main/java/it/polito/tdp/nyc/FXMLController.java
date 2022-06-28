/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<String> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<Adiacenza, String> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<Adiacenza, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<Adiacenza> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String p = this.cmbProvider.getValue();
    	if(p == null) {
    		this.txtResult.setText("Seleziona un provider!");
    		return;
    	}
    	
    	String s = this.model.creaGrafo(p);
    	this.txtResult.setText(s);
    	List<String> cities = this.model.getVertices();
    	this.cmbQuartiere.getItems().clear();
    	this.cmbQuartiere.getItems().addAll(cities);
    	this.tblQuartieri.getItems().clear();
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	String q = this.cmbQuartiere.getValue();
    	if(q == null) {
    		this.txtResult.setText("Seleziona un quartiere!");
    		return;
    	}
    	
    	List<Adiacenza> adiacenze = this.model.quartieriAdiacenti(q);
    	Collections.sort(adiacenze);
    	this.tblQuartieri.setItems(FXCollections.observableArrayList(adiacenze));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String p = this.cmbProvider.getValue();
    	if(p == null) {
    		this.txtResult.setText("Seleziona un provider!");
    		return;
    	}
    	
    	String q = this.cmbQuartiere.getValue();
    	if(q == null) {
    		this.txtResult.setText("Seleziona un quartiere!");
    		return;
    	}
    	
    	int n;
    	try {
			n = Integer.parseInt(this.txtMemoria.getText());
		} catch (Exception e) {
			this.txtResult.setText("Inserisci un valore intero per n!");
    		return;
		}
    	
    	String res = this.model.simula(p, q, n);
    	this.txtResult.setText(res);	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";

        this.clQuartiere.setCellValueFactory(new PropertyValueFactory<Adiacenza, String>("city"));
        this.clDistanza.setCellValueFactory(new PropertyValueFactory<Adiacenza, Double>("distanza"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<String> providers = this.model.getProviders();
    	this.cmbProvider.getItems().addAll(providers);
    }

}
