package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Matiere;
import singleton.Singleton;

public class MatiereDao {

	Connection conn = Singleton.getInstance();

	public void add(Matiere m)throws Exception{

		String req="INSERT INTO matiere(nom) VALUE(\""+m.getNom()+"\")";
			System.out.println(req);
			conn.createStatement().executeUpdate(req);
	}
	public void suprimmer(Matiere m)throws Exception{
		String query = "DELETE from matiere WHERE ID =" + m.getId();
		System.out.println(query +  m.getId());
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
	}
	public void update(Matiere m)throws Exception{
		int id = m.getId();
		String query = "UPDATE matiere SET Nom = '"+m.getNom()+"' Where ID=" + id;
		System.out.println(query);
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);;
	}
	public Matiere getById(int id)throws Exception{
		String query = "SELECT * from matiere WHERE ID = " + id;
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			Matiere Matiere = new Matiere();
			Matiere.setId(rs.getInt("ID"));
			Matiere.setNom(rs.getString("Nom"));
			return Matiere;
		}
		return null;
	}
	
	public Matiere getLast()throws Exception{
		String query = "SELECT * from matiere ORDER BY ID DESC LIMIT 1" ;
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			Matiere Matiere = new Matiere();
			Matiere.setId(rs.getInt("ID"));
			Matiere.setNom(rs.getString("Nom"));
			return Matiere;
		}
		return null;
	}
	
	
	public List getAll()throws Exception{
	
		
		List<Matiere> myList = new ArrayList<Matiere>();
		String query;
		query = "SELECT * from matiere";
		System.out.println(query);
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			Matiere Matiere = new Matiere();
			Matiere.setId(rs.getInt("ID"));
			Matiere.setNom(rs.getString("nom"));
			myList.add(Matiere);
			
		}
		return myList;
	}
}
