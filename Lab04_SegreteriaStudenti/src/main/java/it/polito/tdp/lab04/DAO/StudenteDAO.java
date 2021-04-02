package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import it.polito.tdp.corsi.db.ConnectDB;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public List corsiStudenteDaMatricola(Integer matricola) {
		
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM studente s, corso c, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND c.codins = i.codins AND s.matricola = ?";

		try {
			
			List<Corso> listaCorsi = new ArrayList<Corso>();
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola.toString());

			ResultSet rs = st.executeQuery();		

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
				listaCorsi.add(c);
			}

			conn.close();
			return listaCorsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public boolean iscrittoAlCorso(Integer matricola, Corso corso) {
		
		String sql = "SELECT * FROM iscrizione i WHERE i.matricola = ? AND i.codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola.toString());
			st.setString(2, corso.getCodins());;
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;				
			}else{			
				rs.close();
				st.close();
				conn.close();
				return false;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
		

}
