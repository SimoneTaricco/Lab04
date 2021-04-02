/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	 @FXML
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ChoiceBox<Corso> corsoChoiceBox;
    

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	this.txtRisultato.clear();
    	
    	String str = this.txtMatricola.getText();
    	Integer matricola = 0;
    	
    	try {
    		matricola = Integer.parseInt(str);
    	}catch(Exception e) {
    		txtRisultato.setText("La matricola ha formato esclusivamente numerico");
    	}
   
    	
    	if(model.corsiStudenteDaMatricola(matricola)!=null) {
    		
    		if (matricola.toString().trim().length()!=6){
        		txtRisultato.setText("La matricola è formata da sei numeri interi");
        		} else {    		
        			List<Corso> res = this.model.corsiStudenteDaMatricola(matricola); 
        			for(Corso c:res)
        				this.txtRisultato.appendText(c.toString() + "\n");    	
        			}
    		}
    	
    	if (model.studenteDaMatricola(matricola)==null){
    	    		txtRisultato.setText("Matricola " + matricola + " non presente nel database!");
    	    		return;
    		}
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	
    	this.txtRisultato.clear();
    	
    	if(this.corsoChoiceBox.getValue() == null){
    		this.txtRisultato.setText("Selezionare un corso!");
    		return;
    	}
    	
    	List<Studente> res = this.model.getStudentiIscrittiAlCorso(this.corsoChoiceBox.getValue());
    	
    	if(this.corsoChoiceBox.getValue().getCodins()==null) {
    		this.txtRisultato.setText("Selezionare un corso!");
    		return;
    	}
    	
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append(String.format("%-10s ", "MATRICOLA"));
		sb.append(String.format("%-20s ", "NOME"));
		sb.append(String.format("%-20s\n", "COGNOME"));
    	   	
    	for(Studente s:res) {
    		sb.append(String.format("%-10d ", s.getMatricola()));
    		sb.append(String.format("%-20s ", s.getNome()));
    		sb.append(String.format("%-20s\n", s.getCognome()));
    	}
    	
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	this.txtRisultato.clear();
    	
    	Corso c = this.corsoChoiceBox.getValue();
    	
    	String str = this.txtMatricola.getText();
    	Integer matricola = 0;
    	
    	try {
    		matricola = Integer.parseInt(str);
    	}catch(Exception e) {
    		txtRisultato.setText("Formato numerico della matricola non valido!");
    	}
    	
    	if (model.studenteDaMatricola(matricola)==null){
    		txtRisultato.setText("Matricola " + matricola + " non presente nel database!");
    		return;
    	}
    	
    	if (c.getNome()!=null && matricola != 0) {
    		if(this.model.iscrittoAlCorso(matricola, c)==true) {
    			this.txtRisultato.setText("Studente già iscritto al corso!");
    		} else {
    			this.model.iscriviStudenteACorso(matricola, c);
    			this.txtRisultato.setText("Lo studente " + matricola + " è stato iscritto al corso " + c.getNome());
    		}
    	}
    	
    }
    
    @FXML
    void doCompila(ActionEvent event) {
    	
    	this.txtNome.clear();
    	this.txtCognome.clear();
    	
    	String str = this.txtMatricola.getText();
    	Integer matricola = 0;
    	
    	try {
    		matricola = Integer.parseInt(str);
    	}catch(Exception e) {
    		txtRisultato.setText("Formato numerico della matricola non valido!");
    	}
    	
    	if(model.studenteDaMatricola(matricola)!=null) {
    		Studente s = model.studenteDaMatricola(matricola);
    		this.txtCognome.setText(s.getCognome());
    		this.txtNome.setText(s.getNome());
    	}else {
    		txtRisultato.setText("Matricola " + matricola + " non presente nel database!");
    	}
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtRisultato.clear();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    }

    @FXML
	public void setModel(Model model) {
		this.model=model;
    	txtRisultato.setStyle("-fx-font-family: monospace");
		this.corsoChoiceBox.getItems().addAll(model.getCorsi());
		Corso c = new Corso(null,0,null,0);
		this.corsoChoiceBox.getItems().add(c);
	}
}