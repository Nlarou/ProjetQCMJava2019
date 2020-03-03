package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Examen;
import beans.ExamenAQuestion;
import beans.Question;
import beans.Utilisateur;
import beans.UtilisateurAExamen;
import singleton.Singleton;

public class UtilisateurAExamenDao {

	Connection conn = Singleton.getInstance();
	public void add(Utilisateur user, Examen examenChoisi)throws Exception
	{
		String req="INSERT INTO utilisateuraexamen(id_utilisateur,id_examen) VALUE("+user.getId()+","+
				examenChoisi.getId()+")";
		System.out.println(req);
		conn.createStatement().executeUpdate(req);
	}
	

	public void suprimmerWithUser(Utilisateur user)throws Exception{
		String query = "DELETE from utilisateuraexamen WHERE id_utilisateur=" + user.getId();
		System.out.println(query +  user.getId());
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
	}
	
	public void suprimmerWithExam(Examen exam)throws Exception{
		String query = "DELETE from utilisateuraexamen WHERE id_examen=" + exam.getId();
		System.out.println(query +  exam.getId());
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
	}
	
	public UtilisateurAExamen getById(Utilisateur user, Examen examenChoisi)throws Exception{
		
		String query = "SELECT * from utilisateuraexamen WHERE id_utilisateur=" + user.getId() + " AND id_examen=" + examenChoisi.getId();
		System.out.println(query);
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			UtilisateurAExamen eq = new UtilisateurAExamen();
			eq.setIdUtilisateur(rs.getInt("id_utilisateur"));
			eq.setIdExamen(rs.getInt("id_examen"));
			eq.setNote(rs.getDouble("note"));
			return eq;
		}
		return null;
	}
	public List getAll()throws Exception{
	
		List<UtilisateurAExamen> myList = new ArrayList<UtilisateurAExamen>();
		String query;
		query = "SELECT * from utilisateuraexamen";
		System.out.println(query);
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			UtilisateurAExamen eq = new UtilisateurAExamen();
			eq.setIdUtilisateur(rs.getInt("id_utilisateur"));
			eq.setIdExamen(rs.getInt("id_examen"));
			eq.setNote(rs.getDouble("note"));
			myList.add(eq);
			
		}
		return myList;
	}
	
	public double getNote(Examen examen)throws Exception{
		double note =0;
		String query = "SELECT SUM(point_eleve) FROM examenaquestion where id_examen=" + examen.getId();
		System.out.println(query);
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			note = rs.getDouble(1);
			
		}
		return note;

	}
	public void setNote(double note,Utilisateur user,Examen examen)throws Exception{
		String query = "UPDATE utilisateuraexamen SET note="+ note +" WHERE id_utilisateur=" + user.getId()+ " AND id_examen =" + examen.getId()+"";
		System.out.println(query);
		conn.createStatement().executeUpdate(query);

	}
	
	public int getNombreQuestion(Examen examen)throws Exception{
		int nombreQuestion =0;
		String query = "SELECT COUNT(examenaquestion.id_question) FROM examenaquestion WHERE id_examen=" + examen.getId();
		System.out.println(query);
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			nombreQuestion = rs.getInt(1);	
		}
		return nombreQuestion;
	}
	
	public List getByUserId(int user)throws Exception{
		String query = "SELECT * from utilisateuraexamen WHERE id_utilisateur=" + user;
		ResultSet rs = conn.createStatement().executeQuery(query);
		List<UtilisateurAExamen> listeUtiliAExamen = new ArrayList();
		while(rs.next())
		{
			UtilisateurAExamen eq = new UtilisateurAExamen();
			eq.setIdUtilisateur(rs.getInt("id_utilisateur"));
			eq.setIdExamen(rs.getInt("id_examen"));
			eq.setNote(rs.getInt("note"));
			listeUtiliAExamen.add(eq);
		}
		return listeUtiliAExamen;
	}
}