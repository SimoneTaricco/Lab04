package it.polito.tdp.lab04.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.ConnectDB;
import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}	
	
	public List<Corso> getCorsi(){		
		return corsoDao.getTuttiICorsi();
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso c){		
		return corsoDao.getStudentiIscrittiAlCorso(c);
	}
	
	public Studente studenteDaMatricola(Integer matricola) {
		return corsoDao.studenteDaMatricola(matricola);
	}
	
	public List<Corso> corsiStudenteDaMatricola(Integer matricola) {
		return studenteDao.corsiStudenteDaMatricola(matricola);
	}
	
	public boolean iscrittoAlCorso(Integer matricola, Corso corso) {
		return studenteDao.iscrittoAlCorso(matricola, corso);
	}
	
	public void iscriviStudenteACorso(Integer matricola, Corso corso) {
		this.corsoDao.iscriviStudenteACorso(matricola, corso);
	}
	

}
